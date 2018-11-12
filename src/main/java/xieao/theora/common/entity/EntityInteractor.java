package xieao.theora.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.common.lib.TheoraSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EntityInteractor extends Entity {

    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityInteractor.class, DataSerializers.VARINT);
    private IBlockState oldState;
    private IBlockState newState;
    private int interactTime;

    @Nullable
    private UUID playerId;

    public EntityInteractor(World worldIn) {
        super(worldIn);
        setSize(0.9F, 0.9F);
        this.oldState = Blocks.AIR.getDefaultState();
        this.newState = Blocks.AIR.getDefaultState();
    }

    public static boolean tryInteract(World world, BlockPos pos, IBlockState oldState, boolean exactOldState, IBlockState newState, int interactTime, int color, @Nullable UUID playerId) {
        AxisAlignedBB alignedBB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 0.2D).offset(pos);
        List<Entity> entities = world.getEntitiesWithinAABB(EntityInteractor.class, alignedBB);
        if (entities.size() > 0) {
            return false;
        }
        if (!world.isRemote) {
            EntityInteractor interactor = new EntityInteractor(world);
            interactor.setPlayerId(playerId);
            IBlockState state = world.getBlockState(pos);
            if (exactOldState) {
                interactor.setOldState(oldState);
            } else {
                interactor.setOldState(state);
            }
            interactor.setNewState(newState);
            interactor.setInteractTime((interactTime));
            interactor.setColor(color);
            interactor.setPosition(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(interactor);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), TheoraSounds.LIQUID_DRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return true;
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(COLOR, 0xffffff);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        setColor(compound.getInteger("color"));
        setInteractTime(compound.getInteger("interactTime"));
        setPlayerId(compound.getUniqueId("playerId"));
        if (compound.hasKey("oldStateRegName", Constants.NBT.TAG_STRING)) {
            String regName = compound.getString("oldStateRegName");
            int meta = compound.getInteger("oldStateMeta");
            ResourceLocation regNamel = new ResourceLocation(regName);
            Block block = ForgeRegistries.BLOCKS.getValue(regNamel);
            if (block != null) {
                setOldState(block.getStateFromMeta(meta));
            }
        }
        if (compound.hasKey("newStateRegName", Constants.NBT.TAG_STRING)) {
            String regName = compound.getString("newStateRegName");
            int meta = compound.getInteger("newStateMeta");
            ResourceLocation regNamel = new ResourceLocation(regName);
            Block block = ForgeRegistries.BLOCKS.getValue(regNamel);
            if (block != null) {
                setNewState(block.getStateFromMeta(meta));
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("color", getColor());
        compound.setInteger("interactTime", getInteractTime());
        if (getPlayerId() != null) {
            compound.setUniqueId("playerId", getPlayerId());
        }
        if (!getOldState().getBlock().equals(Blocks.AIR)) {
            Block block = getOldState().getBlock();
            int meta = block.getMetaFromState(getOldState());
            ResourceLocation regName = block.getRegistryName();
            Objects.requireNonNull(regName);
            compound.setString("oldStateRegName", regName.toString());
            compound.setInteger("oldStateMeta", meta);
        }
        if (!getNewState().getBlock().equals(Blocks.AIR)) {
            Block block = getNewState().getBlock();
            int meta = block.getMetaFromState(getNewState());
            ResourceLocation regName = block.getRegistryName();
            Objects.requireNonNull(regName);
            compound.setString("newStateRegName", regName.toString());
            compound.setInteger("newStateMeta", meta);
        }
    }

    @Override
    public void onUpdate() {
        if (!this.world.isRemote) {
            if (checkStates()) {
                BlockPos pos = getPosition();
                IBlockState state = this.world.getBlockState(pos);
                if (state.equals(getOldState())) {
                    if (this.interactTime-- <= 0) {
                        this.world.setBlockState(pos, getNewState(), 2);
                        this.world.playEvent(2001, pos, Block.getStateId(getNewState()));
                        this.world.playSound(null, this.posX, this.posY, this.posZ, TheoraSounds.BLOCK_TRANSFORMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        setDead();
                    }
                } else {
                    setDead();
                }
            } else {
                setDead();
            }
        } else {
            doSpawnParticles();
        }
    }

    private boolean checkStates() {
        return !getOldState().getBlock().equals(Blocks.AIR)
                && !getNewState().getBlock().equals(Blocks.AIR);
    }

    @SideOnly(Side.CLIENT)
    private void doSpawnParticles() {
        IBlockState state = this.world.getBlockState(getPosition());
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

    @Override
    public int getBrightnessForRender() {
        return 12213545;
    }

    @Override
    public float getBrightness() {
        return super.getBrightness();
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    public void setColor(int color) {
        this.dataManager.set(COLOR, color);
    }

    public IBlockState getOldState() {
        return oldState;
    }

    public void setOldState(IBlockState oldState) {
        this.oldState = oldState;
    }

    public IBlockState getNewState() {
        return newState;
    }

    public void setNewState(IBlockState newState) {
        this.newState = newState;
    }

    public int getInteractTime() {
        return interactTime;
    }

    public void setInteractTime(int interactTime) {
        this.interactTime = interactTime;
    }

    @Nullable
    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(@Nullable UUID playerId) {
        this.playerId = playerId;
    }
}
