package xieao.theora.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.passive.EntityChicken;
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
import xieao.theora.common.entity.EntitySoul;
import xieao.theora.common.lib.helper.NBTHelper;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class ItemSoulEgg extends ItemBase {

    public static final HashMap<Class<? extends Entity>, Integer> SOUL_EGGS = new HashMap<>();
    public static final String TAG_ENTITY_CLASS_NAME = "entityClassName";

    static {
        SOUL_EGGS.put(EntityChicken.class, 0xffffff);
        SOUL_EGGS.put(EntityDragon.class, 0xffffff);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            items.add(new ItemStack(this));
            for (Class<? extends Entity> entityClass : SOUL_EGGS.keySet()) {
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
                setEntityClassName(stack, souls.get(0).getSoulOwnerClassName());
                souls.get(0).setDead();
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
                soul.spawnExplosionParticle();
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


}
