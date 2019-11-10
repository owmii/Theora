package zeroneye.theora.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import zeroneye.lib.util.math.V3d;

import javax.annotation.Nullable;
import java.util.UUID;

public class HorEntity extends Entity {
    private static final DataParameter<String> PLAYER_ID = EntityDataManager.createKey(WitherEntity.class, DataSerializers.STRING);
    private static final DataParameter<BlockPos> LAND = EntityDataManager.createKey(WitherEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Integer> TURN = EntityDataManager.createKey(WitherEntity.class, DataSerializers.VARINT);
    @Nullable
    private PlayerEntity player;

    public HorEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public HorEntity(World worldIn) {
        super(IEntities.HOR, worldIn);
        noClip = true;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(PLAYER_ID, UUID.randomUUID().toString());
        this.dataManager.register(LAND, BlockPos.ZERO);
        this.dataManager.register(TURN, 0);
    }

    @Override
    public void tick() {
        super.tick();
//        this.player = Player.get(UUID.fromString(this.dataManager.get(PLAYER_ID)));
//        if (this.player != null) {
//            double dist = this.player.getDistance(this);
//            if (dist > 10.0D) {
//                remove();
//            } else {
//                if (this.ticksExisted > 3 * getTurn()) {
//                    setMotion(
//                            (this.player.posX - this.posX) * 0.35D * (1 + (this.ticksExisted - 2) / 10.0F),
//                            ((this.player.posY + 1.2D) - this.posY) * 0.35D * (1 + (this.ticksExisted - 2) / 10.0F),
//                            (this.player.posZ - this.posZ) * 0.35D * (1 + (this.ticksExisted - 2) / 10.0F)
//                    );
//                    move(MoverType.SELF, getMotion());
//                }
//                if (dist < 1.21D) {
//                    if (this.world.isRemote) {
//                        for (int i = 0; i < 40; i++) {
//                            Effects.create(Effect.GLOW_SMALL, world, V3d.from(getPositionVec()).random(.15D)
//                            ).maxAge(10).color(0x02b500).to(V3d.from(getPositionVec()).random(.72D))
//                                    .alpha(0.3F, 1).blend().spawn();
//                        }
//                    }
//                    remove();
//                }
//            }
//        } else remove();
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(PLAYER_ID, compound.getString("PlayerId"));
        this.dataManager.set(LAND, NBTUtil.readBlockPos(compound.getCompound("Land")));
        this.dataManager.set(TURN, compound.getInt("Turn"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putString("PlayerId", this.dataManager.get(PLAYER_ID));
        compound.put("Land", NBTUtil.writeBlockPos(this.dataManager.get(LAND)));
        compound.putInt("Turn", this.dataManager.get(TURN));
    }

    @Nullable
    public PlayerEntity getPlayer() {
        return player;
    }

    public HorEntity setPlayer(PlayerEntity player) {
        this.dataManager.set(PLAYER_ID, player.getUniqueID().toString());
        return this;
    }

    public BlockPos getLandPos() {
        return this.dataManager.get(LAND);
    }

    public void setLandPos(V3d landPos) {
        this.dataManager.set(LAND, landPos.toPos());
    }

    public int getTurn() {
        return this.dataManager.get(TURN);
    }

    public void setTurn(int turn) {
        this.dataManager.set(TURN, turn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
