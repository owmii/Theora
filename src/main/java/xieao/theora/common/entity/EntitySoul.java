package xieao.theora.common.entity;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xieao.theora.common.item.ItemSoulEgg;

@Mod.EventBusSubscriber
public class EntitySoul extends EntityFlying {

    private String soulOwnerClassName = "";

    public EntitySoul(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.getItem() instanceof ItemSoulEgg) {
            ItemSoulEgg soulEgg = (ItemSoulEgg) stack.getItem();
            if (!soulEgg.hasEntityClassName(stack)) {
                soulEgg.setEntityClassName(stack, this.soulOwnerClassName);
                setDead();
                //TODO particles & sound
            }
        }
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
        if (ItemSoulEgg.SOUL_EGG_REGISTRY.containsKey(target.getClass())) {
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

    public String getSoulOwnerClassName() {
        return soulOwnerClassName;
    }

    public void setSoulOwnerClassName(String soulOwnerClassName) {
        this.soulOwnerClassName = soulOwnerClassName;
    }
}
