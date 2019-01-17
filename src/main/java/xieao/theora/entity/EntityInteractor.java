package xieao.theora.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.core.IEntities;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EntityInteractor extends Entity {
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityInteractor.class, DataSerializers.VARINT);
    private IBlockState state;
    @Nullable
    private IBlockState resultState;
    private ItemStack resultStack = ItemStack.EMPTY;
    @Nullable
    private UUID playerId;
    private int coolDown;

    private EntityInteractor(World world, IBlockState state, @Nullable UUID playerId) {
        super(IEntities.INTERACTOR, world);
        this.state = state;
        this.playerId = playerId;
        setSize(0.9F, 0.9F);
    }

    public EntityInteractor(World worldIn) {
        this(worldIn, Blocks.AIR.getDefaultState(), null);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(COLOR, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (this.resultState == null && this.resultStack.isEmpty()) remove();
            IBlockState state = this.world.getBlockState(getPosition());
            if (state.equals(this.state)) {
                if (this.coolDown-- <= 0) {
                    if (this.resultState != null) {
                        this.world.setBlockState(getPosition(), this.resultState, 2);
                    } else if (!this.resultStack.isEmpty()) {
                        BlockPos pos = getPosition();
                        EntityItem item = new EntityItem(this.world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, this.resultStack);
                        this.world.spawnEntity(item);
                    }
                    remove();
                }
            } else {
                remove();
            }
        }
    }

    @Override
    protected void readAdditional(NBTTagCompound compound) {
        this.state = NBTUtil.readBlockState(compound.getCompound("state"));
        if (compound.hasKey("resultState")) {
            this.resultState = NBTUtil.readBlockState(compound.getCompound("resultState"));
        }
        if (compound.hasKey("resultStack")) {
            this.resultStack = ItemStack.read(compound.getCompound("resultStack"));
        }
        if (compound.hasKey("playerId")) {
            this.playerId = compound.getUniqueId("playerId");
        }
        setColor(compound.getInt("color"));
        this.coolDown = compound.getInt("coolDown");
    }

    @Override
    protected void writeAdditional(NBTTagCompound compound) {
        compound.setTag("state", NBTUtil.writeBlockState(this.state));
        if (this.resultState != null) {
            compound.setTag("resultState", NBTUtil.writeBlockState(this.resultState));
        }
        if (!this.resultStack.isEmpty()) {
            compound.setTag("resultStack", this.resultStack.write(new NBTTagCompound()));
        }
        if (this.playerId != null) {
            compound.setUniqueId("playerId", this.playerId);
        }
        compound.setInt("color", getColor());
        compound.setInt("coolDown", this.coolDown);
    }

    public static boolean tryInteract(World world, BlockPos pos, IBlockState resultState, int coolDown, int color, @Nullable UUID playerId) {
        return tryInteract(world, pos, world.getBlockState(pos), resultState, ItemStack.EMPTY, coolDown, color, playerId);
    }

    public static boolean tryInteract(World world, BlockPos pos, ItemStack resultStack, int coolDown, int color, @Nullable UUID playerId) {
        return tryInteract(world, pos, world.getBlockState(pos), null, resultStack, coolDown, color, playerId);
    }

    public static boolean tryInteract(World world, BlockPos pos, IBlockState state, IBlockState resultState, int coolDown, int color, @Nullable UUID playerId) {
        return tryInteract(world, pos, state, resultState, ItemStack.EMPTY, coolDown, color, playerId);
    }

    public static boolean tryInteract(World world, BlockPos pos, IBlockState state, ItemStack resultStack, int coolDown, int color, @Nullable UUID playerId) {
        return tryInteract(world, pos, state, null, resultStack, coolDown, color, playerId);
    }

    private static boolean tryInteract(World world, BlockPos pos, IBlockState state, @Nullable IBlockState resultState, ItemStack resultStack, int coolDown, int color, @Nullable UUID playerId) {
        if (!world.isRemote) {
            if (checkInteractorAt(world, pos)) {
                IBlockState state1 = world.getBlockState(pos);
                EntityInteractor interactor = new EntityInteractor(world, state, playerId);
                interactor.resultState = resultState;
                interactor.resultStack = resultStack;
                interactor.coolDown = coolDown;
                interactor.setColor(color);
                interactor.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(interactor);
            }
        }
        return false;
    }

    private static boolean checkInteractorAt(World world, BlockPos pos) {
        AxisAlignedBB alignedBB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 0.2D).offset(pos);
        List<EntityInteractor> entities = world.getEntitiesWithinAABB(EntityInteractor.class, alignedBB);
        return entities.size() <= 0;
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    private void setColor(int color) {
        this.dataManager.set(COLOR, color);
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
}
