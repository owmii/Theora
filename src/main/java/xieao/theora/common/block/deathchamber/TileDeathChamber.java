package xieao.theora.common.block.deathchamber;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.api.item.slate.*;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.TileInvBase;
import xieao.theora.common.liquid.TheoraLiquids;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class TileDeathChamber extends TileInvBase implements ITickable {

    public static final int[][] CHAMBER_LAYER = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    public static final int[][] LEGS_LAYER = new int[][]{{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};

    private static final GameProfile DEATH_CHAMBER = new GameProfile(UUID.fromString("8f3dc5b7-eab1-4768-9b73-f1ca057a82eb"), "Death Chamber");

    @Nullable
    protected EntityPlayer killer;

    private final LiquidContainer liquidContainer;

    public boolean buildStatus;

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
        this.buildStatus = nbt.getBoolean("buildStatus");
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        this.liquidContainer.writeNBT(nbt);
        nbt.setBoolean("buildStatus", this.buildStatus);
    }

    @Override
    public void update() {
        if (isServerWorld()) {
            if (!this.buildStatus) {
                if (isBlocksInPlace()) {
                    tryBuild();
                    syncNBTData();
                }
            } else if (!isEmpty() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                float liquidCost = 0.0F;
                int lootingLevel = 0, delayTicks = 120, xpMultiPlier = 0;
                for (int i = 1; i < getSizeInventory() - 1; i++) {
                    ItemStack stack = getStackInSlot(i);
                    if (stack.getItem() instanceof ILootingSlate) {
                        ILootingSlate slate = (ILootingSlate) stack.getItem();
                        lootingLevel = slate.getFortuneLevel(stack);
                        liquidCost += slate.getLiquidCost(stack);
                    } else if (stack.getItem() instanceof IEfficiencySlate) {
                        IEfficiencySlate slate = (IEfficiencySlate) stack.getItem();
                        delayTicks = slate.getDelayTicks(stack);
                        liquidCost += slate.getLiquidCost(stack);
                    } else if (stack.getItem() instanceof IXPSlate) {
                        IXPSlate slate = (IXPSlate) stack.getItem();
                        xpMultiPlier = slate.getXPMultiplier(stack);
                        liquidCost += slate.getLiquidCost(stack);
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
                                if (entityLiving.isRiding() || entityLiving.isBeingRidden()) {
                                    return;
                                }
                                if (entityLiving != null && liquidCost >= liquidSlot.getStored()) {
                                    EntityPlayer killer = this.killer;
                                    ItemStack weapon = new ItemStack(Items.DIAMOND_SWORD);
                                    weapon.addEnchantment(Enchantments.LOOTING, lootingLevel);
                                    killer.setHeldItem(EnumHand.MAIN_HAND, weapon);
                                    Iterator<ItemStack> itr = entityLiving.getEquipmentAndArmor().iterator();
                                    while (itr.hasNext()) {
                                        itr.remove();
                                    }
                                    entityLiving.setInvisible(true);
                                    entityLiving.setPosition(getX() + 0.5D, getY() + 1.1D, getZ() + 0.5D);
                                    entityLiving.attackEntityFrom(DamageSource.causePlayerDamage(killer), Float.MAX_VALUE);
                                    entityLiving.setDead();
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

    public boolean isBlocksInPlace() {
        boolean flag = true;
        for (int i = 0; i < 4; i++) {
            for (int[] offset : CHAMBER_LAYER) {
                BlockPos pos = getPos().add(offset[0], i, offset[1]);
                TileEntity tileEntity = getTileEntity(pos);
                if (!isWallConnectable(pos)) {
                    flag = false;
                }
            }
        }
        if (flag) {
            for (int i = 0; i > -3; i--) {
                for (int[] offset : LEGS_LAYER) {
                    BlockPos pos = getPos().add(offset[0], i, offset[1]);
                    if (!isWallConnectable(pos)) {
                        flag = false;
                    }
                }
            }
        }
        return flag && isWallConnectable(getPos().add(0, 3, 0));
    }

    protected boolean isWallConnectable(BlockPos pos) {
        TileEntity tileEntity = getTileEntity(pos);
        if (tileEntity instanceof TileDeathChamberWall) {
            TileDeathChamberWall wall = (TileDeathChamberWall) tileEntity;
            BlockPos pos1 = wall.dcPos;
            if (pos1 != BlockPos.ORIGIN) {
                TileEntity tileEntity1 = getTileEntity(pos1);
                if (tileEntity1 instanceof TileDeathChamberWall) {
                    TileDeathChamber chamber = (TileDeathChamber) tileEntity1;
                    return chamber.buildStatus;
                }
                return true;
            } else {
                return true;
            }
        }
        return false;
    }

    public void tryBuild() {
        for (int i = 0; i < 4; i++) {
            for (int[] offset : CHAMBER_LAYER) {
                BlockPos pos = getPos().add(offset[0], i, offset[1]);
                setWallChamberPos(pos, getPos());
            }
        }
        for (int i = 0; i > -3; i--) {
            for (int[] offset : LEGS_LAYER) {
                BlockPos pos = getPos().add(offset[0], i, offset[1]);
                setWallChamberPos(pos, getPos());
            }
        }
        setWallChamberPos(getPos().add(0, 3, 0), getPos());
        this.buildStatus = true;
    }

    public void dimolish() {
        for (int i = 0; i < 4; i++) {
            for (int[] offset : CHAMBER_LAYER) {
                BlockPos pos = getPos().add(offset[0], i, offset[1]);
                setWallChamberPos(pos, BlockPos.ORIGIN);
            }
        }
        for (int i = 0; i > -3; i--) {
            for (int[] offset : LEGS_LAYER) {
                BlockPos pos = getPos().add(offset[0], i, offset[1]);
                setWallChamberPos(pos, BlockPos.ORIGIN);
            }
        }
        setWallChamberPos(getPos().add(0, 3, 0), BlockPos.ORIGIN);
        this.buildStatus = false;
    }

    private void setWallChamberPos(BlockPos wallPos, BlockPos chamberPos) {
        TileEntity tileEntity = getTileEntity(wallPos);
        if (tileEntity instanceof TileDeathChamberWall) {
            ((TileDeathChamberWall) tileEntity).setDeathChamberPos(chamberPos);
        }
    }

    @Override
    public int getSizeInventory() {
        return 12;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        for (ItemStack stack1 : this.stacks) {
            if (stack.getItem() == stack1.getItem()) {
                return false;
            }
        }
        return stack.getItem() instanceof ISlate;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return this.buildStatus ? INFINITE_EXTENT_AABB : super.getRenderBoundingBox();
    }
}
