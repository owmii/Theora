package xieao.theora.common.block.binding;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.binding.IBindingRecipe;
import xieao.theora.client.particle.ParticleEngine;
import xieao.theora.client.particle.ParticleGlow;
import xieao.theora.common.block.TileBase;
import xieao.theora.common.liquid.TheoraLiquids;
import xieao.theora.common.recipe.RecipeHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileBindingCenter extends TileBase implements ITickable {

    public static final int[][] RING_OFFSETS = {{0, 3}, {3, 0}, {0, -3}, {-3, 0}, {2, 2}, {-2, -2}, {2, -2}, {-2, 2}};

    public final int maxBuildTicks = 40;
    public int buildTicks;

    private final LiquidContainer liquidContainer;
    public Ability ability = Ability.EMPTY;
    public boolean ready;

    private final int maxTime = 640;
    private int binding;
    public boolean startBinding;

    public BlockPos[] activeRings = new BlockPos[0];

    public TileBindingCenter() {
        this.liquidContainer = new LiquidContainer();
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(TheoraLiquids.GLIOPHIN, true, 10000.0F, 0.0F, 100.0F, LiquidSlot.TransferType.RECEIVE)
        );
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.liquidContainer.readNBT(nbt);
        this.ability = Ability.readAbility(nbt);
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
        this.liquidContainer.writeNBT(nbt);
        Ability.writeAbility(this.ability, nbt);
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
                    syncNBTData();
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
                                        bindingRing.syncNBTData();
                                        list.add(ringPos);
                                    }
                                }
                            }
                            this.activeRings = list.toArray(new BlockPos[0]);
                            liquidSlot.setStored(liquidSlot.getStored() - recipe.getLiquidAmount());
                            this.ability = recipe.getResultAbility();
                            this.startBinding = false;
                            syncNBTData();
                        }
                    }
                } else if (!this.ready && this.binding++ >= this.maxTime) {
                    this.ready = true;
                    this.binding = 0;
                    this.activeRings = new BlockPos[0];
                    syncNBTData();
                }
                if (this.binding == 1) {
                    syncNBTData();
                }
            }
        } else {
//            for (int[] offset : RING_OFFSETS) { //TODO remake particles
//                for (int i = 0; i < 20; i++) {
//                    if (this.rand.nextInt(8) == 0) {
//                        ParticleEngine.INSTANCE.addEffect(
//                                new ParticleGlow(getWorld(), getPosVec().addVector(
//                                        offset[0] + 0.125 + (this.rand.nextDouble() * 0.750), 0.05, offset[1] + 0.125 + (this.rand.nextDouble() * 0.750)), getPosVec().addVector(
//                                        this.rand.nextDouble(), 0.55D, this.rand.nextDouble()), 1, 40, 0.7F, 0xffffff, 0.6F)
//                        );
//                    }
//                }
//            }
            if (this.ready && !this.ability.isEmpty()) {
                for (int i = 0; i < 7; i++) {
                    ParticleEngine.INSTANCE.addEffect(new ParticleGlow(getWorld(), getPosVec().addVector(this.rand.nextDouble(), .2 + this.rand.nextDouble() * 0.9D, this.rand.nextDouble())
                            , getPosVec().addVector(this.rand.nextDouble(), .2 + this.rand.nextDouble() * 0.9D, this.rand.nextDouble()), .03, 10, 0.7F, 0xffffff, 0.6F));
                }
            }
//            if (!this.ready && this.binding > 0) {
//                for (int i = 0; i < 7; i++) {
//                    // Vec3d activeVec = new Vec3d(activePos).addVector(0.125 + (this.rand.nextDouble() * 0.750), 0.05, 0.125 + (this.rand.nextDouble() * 0.750));
//                    ParticleEngine.INSTANCE.addEffect(new ParticleGlow(getWorld(), getPosVec().addVector(this.rand.nextDouble(), .2 + this.rand.nextDouble() * 0.9D, this.rand.nextDouble())
//                            , getPosVec().addVector(this.rand.nextDouble(), .2 + this.rand.nextDouble() * 0.9D, this.rand.nextDouble()), .03, 10, 0.7F, 0xffffff, 0.6F));
//                }
//            }
            if (this.buildTicks > 0) {
                for (BlockPos activePos : this.activeRings) {
                    for (int i = 0; i < 7; i++) {
                        if (this.rand.nextInt(8) == 0) {
                            double d0 = this.rand.nextDouble();
                            Vec3d activeVec = new Vec3d(activePos).addVector(0.125 + (d0 * 0.750), 0.05, 0.125 + (d0 * 0.750));
                            ParticleEngine.INSTANCE.addEffect(new ParticleGlow(getWorld(), activeVec, getPosVec().addVector(d0, 0.55D, d0), 1, 40, 0.7F, 0xffffff, 0.6F));
                        }
                    }
                }
                this.buildTicks--;
                if (this.buildTicks <= 5) {
                    for (int i = 0; i < 20; i++) {
                        if (this.rand.nextInt(5) == 0) {
                            ParticleEngine.INSTANCE.addEffect(
                                    new ParticleGlow(getWorld(), getPosVec().addVector(
                                            this.rand.nextDouble(), 0.05, this.rand.nextDouble()), getPosVec().addVector(
                                            this.rand.nextDouble(), 2.55D, this.rand.nextDouble()), 1, 40, 0.7F, 0xffffff, 0.6F)
                            );
                        }
                    }
                }
            }
        }
    }

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
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER
                || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER ? (T) this.liquidContainer
                : super.getCapability(capability, facing);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
