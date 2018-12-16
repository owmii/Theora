package xieao.theora.common.block.binding;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.binding.IBindingRecipe;
import xieao.theora.client.particle.ParticleEngine;
import xieao.theora.client.particle.ParticleGeneric;
import xieao.theora.client.particle.ParticleTexture;
import xieao.theora.common.block.TileLiquidContainer;
import xieao.theora.common.lib.recipe.RecipeHandler;
import xieao.theora.common.liquid.TheoraLiquids;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileBindingCenter extends TileLiquidContainer implements ITickable {

    public static final int[][] RING_OFFSETS = {{0, 3}, {3, 0}, {0, -3}, {-3, 0}, {2, 2}, {-2, -2}, {2, -2}, {-2, 2}};

    public final int maxBuildTicks = 40;
    public int buildTicks;

    public Ability ability = Ability.EMPTY;
    public boolean ready;

    private final int maxTime = 640;
    private int binding;
    public boolean startBinding;

    public BlockPos[] activeRings = new BlockPos[0];

    public TileBindingCenter() {
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(TheoraLiquids.GLIOPHIN, true, 10000.0F, 0.0F, 100.0F, LiquidSlot.TransferType.RECEIVE)
        );
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.ability = Ability.readNBT(nbt);
        this.binding = nbt.getInteger("binding");
        this.startBinding = nbt.getBoolean("startBinding");
        this.ready = nbt.getBoolean("ready");
        this.buildTicks = nbt.getInteger("buildTicks");
        List<BlockPos> list = new ArrayList<>();
        NBTTagList tagList = nbt.getTagList("activeRings", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            list.add(NBTUtil.getPosFromTag(compound.getCompoundTag("activePos")));
        }
        this.activeRings = list.toArray(new BlockPos[0]);
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        Ability.writeNBT(this.ability, nbt);
        nbt.setInteger("binding", this.binding);
        nbt.setBoolean("startBinding", this.startBinding);
        nbt.setBoolean("ready", this.ready);
        nbt.setInteger("buildTicks", this.buildTicks);
        NBTTagList tagList = new NBTTagList();
        for (BlockPos pos : this.activeRings) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("activePos", NBTUtil.createPosTag(pos));
            tagList.appendTag(compound);
        }
        nbt.setTag("activeRings", tagList);
    }

    @Override
    public void update() {
        if (isServerWorld()) {
            if (this.buildTicks > 0) {
                this.buildTicks--;
                if (this.buildTicks <= 0) {
                    markDirtyAndSync();
                }
            } else {
                LiquidSlot liquidSlot = this.liquidContainer.getLiquidSlot(0);
                if (this.ability.isEmpty()) {
                    IBindingRecipe recipe = getCurrentRecipe();
                    if (recipe != null && !recipe.getResultAbility().isEmpty()) {
                        if (this.startBinding) {
                            List<BlockPos> list = new ArrayList<>();
                            for (int[] offset : RING_OFFSETS) {
                                BlockPos ringPos = getPos().add(offset[0], 0, offset[1]);
                                TileEntity tileEntity = getWorld().getTileEntity(ringPos);
                                if (tileEntity instanceof TileBindingRing) {
                                    TileBindingRing bindingRing = (TileBindingRing) tileEntity;
                                    if (!bindingRing.getStackInSlot(0).isEmpty()) {
                                        bindingRing.setInventorySlotContents(0, ItemStack.EMPTY);
                                        bindingRing.markDirtyAndSync();
                                        list.add(ringPos);
                                    }
                                }
                            }
                            this.activeRings = list.toArray(new BlockPos[0]);
                            liquidSlot.setStored(liquidSlot.getStored() - recipe.getLiquidAmount());
                            this.ability = recipe.getResultAbility();
                            this.startBinding = false;
                            markDirtyAndSync();
                        }
                    }
                } else if (!this.ready && this.binding++ >= this.maxTime) {
                    this.ready = true;
                    this.binding = 0;
                    this.activeRings = new BlockPos[0];
                    markDirtyAndSync();
                }
                if (this.binding == 1) {
                    markDirtyAndSync();
                }
            }
        } else {
            if (world.getTotalWorldTime() % 30 == 0)
                ParticleEngine.INSTANCE.addEffect(new ParticleGeneric(ParticleTexture.GLOW_SMALL, this.world, getPosVec(), 12)
                        .scale(1, 3, 0.8F).setAlpha(1.0F, 0).setColor(0xfff7d3, 0xa89e70).blendFunc().setGravity(-.03F));
        }
    }

    @Nullable
    public IBindingRecipe getCurrentRecipe() {
        LiquidSlot liquidSlot = this.liquidContainer.getLiquidSlot(0);
        return RecipeHandler.findBindingStoneRecipe(getRecipeStacks(), liquidSlot.getStored(), getWorld(), getPos());
    }

    public List<ItemStack> getRecipeStacks() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int[] offset : RING_OFFSETS) {
            BlockPos ringPos = getPos().add(offset[0], 0, offset[1]);
            TileEntity tileEntity = getWorld().getTileEntity(ringPos);
            if (tileEntity instanceof TileBindingRing) {
                TileBindingRing bindingRing = (TileBindingRing) tileEntity;
                ItemStack stack = bindingRing.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }
        }
        return stacks;
    }

    public boolean hasAbility() {
        return !this.ability.isEmpty();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
