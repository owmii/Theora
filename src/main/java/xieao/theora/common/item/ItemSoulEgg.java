package xieao.theora.common.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.lib.item.ItemBase;
import xieao.theora.client.renderer.item.IColoredItem;
import xieao.theora.common.entity.EntitySoul;
import xieao.theora.common.lib.helper.NBTHelper;

import javax.annotation.Nullable;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class ItemSoulEgg extends ItemBase implements IColoredItem {

    public static final HashMap<Class<? extends Entity>, Integer> SOULS = new HashMap<>();
    public static final String TAG_ENTITY_CLASS_NAME = "entityClassName";

    static {
        // Nutrals
        SOULS.put(EntityBat.class, 0x888236);

        // Monsters
        SOULS.put(EntityEvoker.class, 0x888236);
        SOULS.put(EntityZombie.class, 0x487833);
        SOULS.put(EntitySkeleton.class, 0xcecdbf);
        SOULS.put(EntityWitherSkeleton.class, 0x454340);
        SOULS.put(EntityEnderman.class, 0x9436bf);
        SOULS.put(EntityBlaze.class, 0xcc950f);
        SOULS.put(EntityWitch.class, 0x9b1745);
        SOULS.put(EntityGuardian.class, 0x5a9c7a);
        SOULS.put(EntityElderGuardian.class, 0xafaa8f);

        // Bosses
        SOULS.put(EntityDragon.class, 0xce08b7);
        SOULS.put(EntityWither.class, 0x828282);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            new Color(0xAFAA8F);
            items.add(new ItemStack(this));
            for (Class<? extends Entity> entityClass : SOULS.keySet()) {
                ItemStack stack = new ItemStack(this);
                setEntityClassName(stack, entityClass.getCanonicalName());
                items.add(stack);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (getEntity(stack) != null) {
            tooltip.add(getEntity(stack).getName() + " Soul");
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (!hasEntityClassName(stack)) {
            List<EntitySoul> souls = worldIn.getEntitiesWithinAABB(EntitySoul.class, new AxisAlignedBB(entityLiving.getPosition()).grow(16));
            if (!souls.isEmpty()) {
                EntitySoul soul = souls.get(0);
                setEntityClassName(stack, soul.getSoulOwnerClassName());
                soul.setDead();
            }
        }
        return stack;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (!hasEntityClassName(stack)) {
            List<EntitySoul> souls = player.world.getEntitiesWithinAABB(EntitySoul.class, new AxisAlignedBB(player.getPosition()).grow(16));
            if (!souls.isEmpty()) {
                EntitySoul soul = souls.get(0);
                soul.motionX = (player.posX - soul.posX) * 0.05D;
                soul.motionY = ((player.posY + 0.5D) - soul.posY) * 1.8D;
                soul.motionZ = (player.posZ - soul.posZ) * 0.05D;
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 50;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

    public boolean hasEntityClassName(ItemStack stack) {
        return NBTHelper.hasKey(stack, TAG_ENTITY_CLASS_NAME, Constants.NBT.TAG_STRING);
    }

    public String getEntityClassName(ItemStack stack) {
        return NBTHelper.getString(stack, TAG_ENTITY_CLASS_NAME);
    }

    public void setEntityClassName(ItemStack stack, String entityClassName) {
        NBTHelper.setString(stack, TAG_ENTITY_CLASS_NAME, entityClassName);
    }

    @Nullable
    public Class<?> getEntityClass(ItemStack stack) {
        try {
            String className = getEntityClassName(stack);
            if (!className.isEmpty()) {
                return Class.forName(className);
            }
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }

    @Nullable
    public Entity getEntity(ItemStack stack) {
        try {
            Class<?> entityClass = getEntityClass(stack);
            if (entityClass != null) {
                return (Entity) entityClass.getConstructor(World.class).newInstance(DimensionManager.getWorld(0));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        return null;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IItemColor getItemColor() {
        return (stack, tintIndex) -> {
            Class<?> entityClass = getEntityClass(stack);
            if (entityClass != null && SOULS.containsKey(entityClass)) {
                int color = SOULS.get(entityClass);
                return tintIndex == 1 ? color : 0xffffff;
            } else {
                return tintIndex == 1 ? 0 : 0xffffff;
            }
        };
    }
}


