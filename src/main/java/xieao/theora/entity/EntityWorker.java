package xieao.theora.entity;

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
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;
import xieao.theora.block.gate.TileGate;
import xieao.theora.core.IBlocks;
import xieao.theora.core.IEntities;
import xieao.theora.lib.util.Ticker;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityWorker extends Entity {
    private static final DataParameter<Integer> JOB = EntityDataManager.createKey(EntityWorker.class, DataSerializers.VARINT);
    private Ticker working;

    @Nullable
    protected Pair<UUID, String> owner;

    @Nullable
    protected EntityPlayer player;

    public EntityWorker(Job job, EntityPlayer player, World world) {
        this(world);
        setJob(job);
        this.owner = Pair.of(player.getUniqueID(), player.getName().getString());
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
            setOwner(UUID.fromString(ownerId), ownerName);
        }
    }

    @Override
    protected void writeAdditional(NBTTagCompound compound) {
        compound.putInt("Job", getJob().ordinal());
        if (this.owner != null) {
            compound.putString("OwnerId", this.owner.getLeft().toString());
            compound.putString("OwnerName", this.owner.getRight());
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
                case BUILD_GATE:
                    if (this.player == null) remove();
                    BlockPos pos = getPosition();
                    BlockPos up = pos.up();
                    if (this.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN ||
                            this.world.getBlockState(up).getBlock() != Blocks.OBSIDIAN ||
                            this.owner == null) {
                        remove();
                    } else {
                        if (this.working.done()) {
                            this.world.setBlockState(pos, IBlocks.GATE.getDefaultState(), 2);
                            this.world.setBlockState(up, IBlocks.GATE.getDefaultState(), 2);
                            TileEntity tileEntity = this.world.getTileEntity(pos);
                            if (tileEntity instanceof TileGate) {
                                TileGate gate = (TileGate) tileEntity;
                                gate.setGateBase(true);
                                gate.setOwner(this.owner.getLeft(), this.owner.getRight());
                                gate.markDirtyAndSync();

                                LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(this.player);
                                holder.orElse(new PlayerData()).setGatePos(getPosition());
                            }
                            remove();
                        }
                    }
                    break;
            }
        }
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
    public Pair<UUID, String> getOwner() {
        return owner;
    }

    public void setOwner(UUID id, String name) {
        this.owner = Pair.of(id, name);
    }

    public enum Job {
        NO_JOB(0),
        BUILD_GATE(80);
        public final int jobTime;

        Job(int jobTime) {
            this.jobTime = jobTime;
        }
    }
}
