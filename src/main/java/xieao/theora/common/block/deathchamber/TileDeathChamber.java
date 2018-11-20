package xieao.theora.common.block.deathchamber;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import xieao.theora.api.item.ISummoningSlate;
import xieao.theora.common.block.TileInvBase;
import xieao.theora.common.item.TheoraItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TileDeathChamber extends TileInvBase implements ITickable {

    public static final int[][] LAYER = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    public static final int[][] LEGS_LAYER = new int[][]{{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};

    private static final GameProfile DEATH_CHAMBER = new GameProfile(UUID.fromString("8f3dc5b7-eab1-4768-9b73-f1ca057a82eb"), "Death Chamber");

    @Nullable
    protected FakePlayer killer;

    private boolean buildStatus;

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
    }

    @Override
    public void update() {
        if (isServerWorld()) {
            if (!this.buildStatus) {
                if (isBlocksInPlace()) {
                    tryBuild();
                }
            } else if (getWorld().getTotalWorldTime() % 70 == 0) {
                if (this.killer == null) {
                    this.killer = FakePlayerFactory.get(DimensionManager.getWorld(0), DEATH_CHAMBER);
                } else {
                    EntityLiving entityLiving = getRandomEntity(0);
                    if (entityLiving != null) {
                        FakePlayer killer = this.killer;
                        ItemStack weapon = new ItemStack(Items.DIAMOND_SWORD);
                        weapon.addEnchantment(Enchantments.LOOTING, 3);
                        killer.setHeldItem(EnumHand.MAIN_HAND, weapon);
                        entityLiving.setInvisible(true);
                        entityLiving.setPosition(getX() + 0.5D, getY() + 1.1D, getZ() + 0.5D);
                        entityLiving.attackEntityFrom(DamageSource.causePlayerDamage(killer), Float.MAX_VALUE);
                        entityLiving.setDead();
                    }
                }
            }
        }
    }

    public boolean isBlocksInPlace() {
        boolean flag = true;
        for (int i = 0; i < 4; i++) {
            for (int[] offset : LAYER) {
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
            for (int[] offset : LAYER) {
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
            for (int[] offset : LAYER) {
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

    @Nullable
    private EntityLiving getRandomEntity(int slot) {
        ItemStack stack = new ItemStack(TheoraItems.SUMMONING_SLATE);
        if (stack.getItem() instanceof ISummoningSlate) {
            ISummoningSlate slate = (ISummoningSlate) stack.getItem();
            List<Biome.SpawnListEntry> spawnListEntries = new ArrayList<>(slate.getSpawnListEntries(stack));
            Biome.SpawnListEntry spawnListEntry = WeightedRandom.getRandomItem(getWorld().rand, spawnListEntries);
            EntityLiving entity = null;
            try {
                entity = spawnListEntry.newInstance(getWorld());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return entity;
        }
        return null;
    }

    @Override
    public int getSizeInventory() {
        return 12;
    }
}
