package xieao.theora.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import xieao.theora.block.hor.TileHor;
import xieao.theora.block.hor.TileHorPart;
import xieao.theora.core.IBlocks;
import xieao.theora.core.IEntities;
import xieao.theora.item.ItemPowder;
import xieao.theora.lib.util.Ticker;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityWorker extends Entity {
    private static final DataParameter<Integer> JOB = EntityDataManager.createKey(EntityWorker.class, DataSerializers.VARINT);
    private Ticker working;

    @Nullable
    protected GameProfile owner;

    @Nullable
    protected EntityPlayer player;

    public EntityWorker(Job job, EntityPlayer player, World world) {
        this(world);
        setJob(job);
        this.owner = player.getGameProfile();
        this.player = player;
        this.working = new Ticker(job.jobTime);
    }

    public EntityWorker(World world) {
        super(IEntities.WORKER, world);
        setSize(0.0001F, 0.0001F);
        this.working = new Ticker(0);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(JOB, 0);
    }

    @Override
    protected void readAdditional(NBTTagCompound compound) {
        setJob(Job.values()[compound.getInt("Job")]);
        if (compound.contains("OwnerId", Constants.NBT.TAG_STRING)) {
            String ownerId = compound.getString("OwnerId");
            String ownerName = compound.getString("OwnerName");
            setOwner(new GameProfile(UUID.fromString(ownerId), ownerName));
        }
    }

    @Override
    protected void writeAdditional(NBTTagCompound compound) {
        compound.putInt("Job", getJob().ordinal());
        if (this.owner != null) {
            compound.putString("OwnerId", this.owner.getId().toString());
            compound.putString("OwnerName", this.owner.getName());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            switch (getJob()) {
                case NO_JOB:
                    remove();
                    break;
                case BUILD_HOR:
                    if (this.player == null) remove();
                    BlockPos pos = getPosition();
                    BlockPos up = pos.up();
                    if (this.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN) {
                        remove();
                    } else {
                        int count = 0;
                        for (int[] off : ItemPowder.OFFSETS_0) {
                            for (int j = 0; j >= -3; j--) {
                                BlockPos pos2 = pos.add(off[0], j, off[1]);
                                if (isObsidian(world, pos2)) {
                                    count++;
                                }
                            }
                        }
                        if (count == 16) {
                            if (this.working.done()) {
                                for (int[] off : ItemPowder.OFFSETS_0) {
                                    for (int j = 0; j >= -3; j--) {
                                        BlockPos pos2 = pos.add(off[0], j, off[1]);
                                        if (!pos.equals(pos2)) {
                                            this.world.setBlockState(pos2, IBlocks.HOR_PART.getDefaultState(), 2);
                                            TileEntity tileEntity = this.world.getTileEntity(pos2);
                                            if (tileEntity instanceof TileHorPart) {
                                                TileHorPart horPart = (TileHorPart) tileEntity;
                                                horPart.setHorPos(pos);
                                            }
                                        }
                                    }
                                }
                                this.world.setBlockState(pos, IBlocks.HOR.getDefaultState(), 2);
                                TileEntity tileEntity = this.world.getTileEntity(pos);
                                if (tileEntity instanceof TileHor) {
                                    TileHor hor = (TileHor) tileEntity;
                                    hor.setOwner(this.owner);
                                    hor.markDirtyAndSync();
                                }
                                remove();
                            }
                        } else remove();
                    }
                    break;
            }
        }
    }

    private boolean isObsidian(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN;
    }

    public Job getJob() {
        return Job.values()[this.dataManager.get(JOB)];
    }

    public void setJob(Job job) {
        this.dataManager.set(JOB, job.ordinal());
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    public boolean handleWaterMovement() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void move(MoverType type, double x, double y, double z) {
    }

    @Override
    public void moveRelative(float strafe, float up, float forward, float friction) {
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Nullable
    public GameProfile getOwner() {
        return owner;
    }

    public void setOwner(GameProfile owner) {
        this.owner = owner;
    }

    public enum Job {
        NO_JOB(0),
        BUILD_HOR(80);
        public final int jobTime;

        Job(int jobTime) {
            this.jobTime = jobTime;
        }
    }
}
