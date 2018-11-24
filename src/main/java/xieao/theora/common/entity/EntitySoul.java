package xieao.theora.common.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xieao.theora.common.item.ItemSoulEgg;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EntitySoul extends EntityLiving {

    private String soulOwnerClassName = "";

    @Nullable
    private BlockPos spawnPosition;

    public EntitySoul(World worldIn) {
        super(worldIn);
        setSize(0.5F, 0.5F);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.soulOwnerClassName = compound.getString("soulOwnerClassName");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("soulOwnerClassName", this.soulOwnerClassName);
        return super.writeToNBT(compound);
    }

    @SubscribeEvent
    public static void spawnSoul(LivingDeathEvent event) {
        EntityLivingBase target = event.getEntityLiving();
        if (ItemSoulEgg.SOUL_EGGS.containsKey(target.getClass())) {
            DamageSource damageSource = event.getSource();
            if (damageSource.getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) damageSource.getTrueSource();
                if (!player.world.isRemote) {
                    ItemStack stack = player.getHeldItemOffhand(); //TODO expermenting stack
                    if (stack.getItem() instanceof ItemSoulEgg) {
                        ItemSoulEgg soulEgg = (ItemSoulEgg) stack.getItem();
                        if (!soulEgg.hasEntityClassName(stack)) {
                            EntitySoul soul = new EntitySoul(player.world);
                            soul.setSoulOwnerClassName(target.getClass().getCanonicalName());
                            soul.setPosition(target.posX, target.posY, target.posZ);
                            player.world.spawnEntity(soul);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //  this.motionY *= 0.6000000238418579D;
    }

    @Override
    protected void updateAITasks() {
        if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
            this.spawnPosition = null;
        }
        if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((double) ((int) this.posX), (double) ((int) this.posY), (double) ((int) this.posZ)) < 4.0D) {
            this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
        }
        double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
        double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
        double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
        this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.02D;
        this.motionY = 0;
        this.motionY += (Math.signum(d1) * 0.6D - this.motionY) * 0.02D;
        this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.02D;
        float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Nullable
    @Override
    public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
        return null;
    }

    @Nullable
    @Override
    public Entity changeDimension(int dimensionIn) {
        return null;
    }

    public String getSoulOwnerClassName() {
        return soulOwnerClassName;
    }

    public void setSoulOwnerClassName(String soulOwnerClassName) {
        this.soulOwnerClassName = soulOwnerClassName;
    }
}
