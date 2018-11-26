package xieao.theora.common.block.deathchamber;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.api.item.slate.*;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.TileInvBase;
import xieao.theora.common.lib.multiblock.IMultiBlock;
import xieao.theora.common.lib.multiblock.IMultiBlockBuilder;
import xieao.theora.common.liquid.TheoraLiquids;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TileDeathChamber extends TileInvBase implements ITickable, IMultiBlockBuilder<TileDeathChamber> {

    private static final GameProfile DEATH_CHAMBER = new GameProfile(UUID.fromString("8f3dc5b7-eab1-4768-9b73-f1ca057a82eb"), "Death Chamber");
    private static final IMultiBlock MULTI_BLOCK = new MBDeathChamber();

    private final LiquidContainer liquidContainer;

    @Nullable
    protected EntityPlayer killer;


    public boolean built;

    public TileDeathChamber() {
        this.liquidContainer = new LiquidContainer();
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(TheoraLiquids.LEQUEN, true, 10000.0F, 0.0F, 400.0F, LiquidSlot.TransferType.RECEIVE)
        );
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.liquidContainer.readNBT(nbt);
        this.built = nbt.getBoolean("built");
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        this.liquidContainer.writeNBT(nbt);
        nbt.setBoolean("built", this.built);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update() {
        if (isServerWorld()) {
            if (!this.built) {
                tryBuild(this);
            } else if (!isEmpty() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                float liquidCost = 0.0F;
                boolean dropEquipment = false;
                int lootingLevel = 0, delayTicks = 120, xpMultiPlier = 0;
                for (int i = 1; i < getSizeInventory() - 1; i++) {
                    ItemStack stack = getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        if (stack.getItem() instanceof ISlate) {
                            ISlate slate = (ISlate) stack.getItem();
                            liquidCost += slate.getLiquidCost(stack);
                            if (slate instanceof ILootingSlate) {
                                lootingLevel = ((ILootingSlate) slate).getFortuneLevel(stack);
                            } else if (slate instanceof IEfficiencySlate) {
                                delayTicks = ((IEfficiencySlate) slate).getDelayTicks(stack);
                            } else if (slate instanceof IEquipmentDropSlate) {
                                dropEquipment = true;
                            } else if (slate instanceof IXPSlate) {
                                xpMultiPlier = ((IXPSlate) slate).getXPMultiplier(stack);
                            }
                        }
                    }
                }
                if (getWorld().getTotalWorldTime() % (delayTicks < 10 ? 10 : delayTicks) == 0) {
                    LiquidSlot liquidSlot = this.liquidContainer.getLiquidSlot(0);
                    if (this.killer == null) {
                        this.killer = FakePlayerFactory.get(DimensionManager.getWorld(0), DEATH_CHAMBER);
                    } else {
                        ItemStack stack = getStackInSlot(0);
                        if (stack.getItem() instanceof ISummoningSlate) {
                            ISummoningSlate slate = (ISummoningSlate) stack.getItem();
                            liquidCost += slate.getLiquidCost(stack);
                            List<Biome.SpawnListEntry> spawnListEntries = new ArrayList<>(slate.getSpawnListEntries(stack));
                            Biome.SpawnListEntry spawnListEntry = WeightedRandom.getRandomItem(getWorld().rand, spawnListEntries);
                            EntityLiving entityLiving = null;
                            try {
                                entityLiving = spawnListEntry.newInstance(getWorld());
                                if (entityLiving != null && liquidCost <= liquidSlot.getStored()) {
                                    EntityPlayer killer = this.killer;
                                    ItemStack weapon = new ItemStack(Items.DIAMOND_SWORD);
                                    weapon.addEnchantment(Enchantments.LOOTING, lootingLevel);
                                    killer.setHeldItem(EnumHand.MAIN_HAND, weapon);
                                    entityLiving.setInvisible(true);
                                    entityLiving.setPosition(getX() + 0.5D, getY() + 1.1D, getZ() + 0.5D);
                                    entityLiving.onInitialSpawn(getWorld().getDifficultyForLocation(getPos()), null);
                                    if (entityLiving.isRiding()) {
                                        Entity ridingEntity = entityLiving.getRidingEntity();
                                        if (ridingEntity instanceof EntityLiving) {
                                            EntityLiving entityLiving1 = (EntityLiving) ridingEntity;
                                            entityLiving1.setInvisible(true);
                                            entityLiving1.setPosition(getX() + 0.5D, getY() + 1.1D, getZ() + 0.5D);
                                            entityLiving1.onInitialSpawn(getWorld().getDifficultyForLocation(getPos()), null);
                                            entityLiving1.attackEntityFrom(DamageSource.causePlayerDamage(killer), Float.MAX_VALUE);
                                            entityLiving1.setDead();
                                        } else {
                                            ridingEntity.setDead();
                                        }
                                    }
                                    if (!dropEquipment) {
                                        for (EntityEquipmentSlot equipmentSlot : EntityEquipmentSlot.values()) {
                                            entityLiving.setItemStackToSlot(equipmentSlot, ItemStack.EMPTY);
                                        }
                                    }
                                    entityLiving.spawnExplosionParticle();
                                    entityLiving.attackEntityFrom(DamageSource.causePlayerDamage(killer), Float.MAX_VALUE);
                                    entityLiving.setDead();
                                    int xpDrop = entityLiving.experienceValue + this.rand.nextInt(3);
                                    xpDrop = ForgeEventFactory.getExperienceDrop(entityLiving, killer, xpDrop) * xpMultiPlier;
                                    while (xpDrop > 0) {
                                        int i = EntityXPOrb.getXPSplit(xpDrop);
                                        xpDrop -= i;
                                        getWorld().spawnEntity(new EntityXPOrb(getWorld(), getX() + 0.5D, getY() - 1.1D, getZ() + 0.5D, i));
                                    }
                                    liquidSlot.setStored(liquidSlot.getStored() - liquidCost);
                                    syncNBTData();
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public IMultiBlock getMultiBlock() {
        return MULTI_BLOCK;
    }

    @Override
    public boolean built() {
        return built;
    }

    @Override
    public void setBuilt(boolean built) {
        this.built = built;
    }

    @Override
    public int getSizeInventory() {
        return 5;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack.getItem() instanceof ISummoningSlate && index != 0) {
            return false;
        }
        for (ItemStack stack1 : this.stacks) {
            if (stack.getItem() == stack1.getItem()) {
                return false;
            }
        }
        return getStackInSlot(index).isEmpty() && stack.getItem() instanceof ISlate;
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
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return this.built ? INFINITE_EXTENT_AABB : super.getRenderBoundingBox();
    }
}
