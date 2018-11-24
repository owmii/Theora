package xieao.theora.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import xieao.theora.common.lib.helper.NBTHelper;

import java.util.HashMap;

public class ItemSoulEgg extends ItemBase {

    public static final HashMap<Class<? extends Entity>, Integer> SOUL_EGG_REGISTRY = new HashMap<>();
    public static final String TAG_ENTITY_CLASS_NAME = "entityClassName";

    static {
        SOUL_EGG_REGISTRY.put(EntityDragon.class, 0xffffff);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (Class<? extends Entity> entityClass : SOUL_EGG_REGISTRY.keySet()) {
                ItemStack stack = new ItemStack(this);
                setEntityClassName(stack, entityClass.getCanonicalName());
                items.add(stack);
            }
        }
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


}
