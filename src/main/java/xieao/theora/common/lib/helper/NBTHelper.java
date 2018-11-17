package xieao.theora.common.lib.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.UUID;

public class NBTHelper {

    // ItemStack NBT

    public static boolean hasNBT(ItemStack stack) {
        return stack.hasTagCompound();
    }

    public static boolean hasKey(ItemStack stack, String value) {
        return getNBT(stack).hasKey(value, Constants.NBT.TAG_STRING);
    }

    public static NBTTagCompound getNBT(ItemStack stack) {
        return hasNBT(stack) ? stack.getTagCompound() : new NBTTagCompound();
    }

    public static void checkAndSetNbt(ItemStack stack) {
        if (!hasNBT(stack)) stack.setTagCompound(new NBTTagCompound());
    }

    public static boolean hasUniqueId(ItemStack stack, String value) {
        return getNBT(stack).hasUniqueId(value);
    }

    public static void setTag(ItemStack stack, String key, NBTBase value) {
        checkAndSetNbt(stack);
        getNBT(stack).setTag(key, value);
    }

    public static void setByte(ItemStack stack, String key, byte value) {
        checkAndSetNbt(stack);
        getNBT(stack).setByte(key, value);
    }

    public static void setShort(ItemStack stack, String key, short value) {
        checkAndSetNbt(stack);
        getNBT(stack).setShort(key, value);
    }

    public static void setInteger(ItemStack stack, String key, int value) {
        checkAndSetNbt(stack);
        getNBT(stack).setInteger(key, value);
    }

    public static void setLong(ItemStack stack, String key, long value) {
        checkAndSetNbt(stack);
        getNBT(stack).setLong(key, value);
    }

    public static void setUniqueId(ItemStack stack, String key, UUID value) {
        checkAndSetNbt(stack);
        getNBT(stack).setUniqueId(key, value);
    }

    public static void setFloat(ItemStack stack, String key, float value) {
        checkAndSetNbt(stack);
        getNBT(stack).setFloat(key, value);
    }

    public static void setDouble(ItemStack stack, String key, double value) {
        checkAndSetNbt(stack);
        getNBT(stack).setDouble(key, value);
    }

    public static void setString(ItemStack stack, String key, String value) {
        checkAndSetNbt(stack);
        getNBT(stack).setString(key, value);
    }

    public static void setByteArray(ItemStack stack, String key, byte[] value) {
        checkAndSetNbt(stack);
        getNBT(stack).setByteArray(key, value);
    }

    public static void setIntArray(ItemStack stack, String key, int[] value) {
        checkAndSetNbt(stack);
        getNBT(stack).setIntArray(key, value);
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        checkAndSetNbt(stack);
        getNBT(stack).setBoolean(key, value);
    }

    public static NBTBase getTag(ItemStack stack, String key) {
        return getNBT(stack).getTag(key);
    }

    public static NBTTagCompound getCompoundTag(ItemStack stack, String key) {
        return getNBT(stack).getCompoundTag(key);
    }

    public static byte getByte(ItemStack stack, String key) {
        return getNBT(stack).getByte(key);
    }

    public static short getShort(ItemStack stack, String key) {
        return getNBT(stack).getShort(key);
    }

    public static int getInteger(ItemStack stack, String key) {
        return getNBT(stack).getInteger(key);
    }

    public static long getLong(ItemStack stack, String key) {
        return getNBT(stack).getLong(key);
    }

    @Nullable
    public static UUID getUniqueId(ItemStack stack, String key) {
        return getNBT(stack).getUniqueId(key);
    }

    public static float getFloat(ItemStack stack, String key) {
        return getNBT(stack).getFloat(key);
    }

    public static double getDouble(ItemStack stack, String key) {
        return getNBT(stack).getDouble(key);
    }

    public static String getString(ItemStack stack, String key) {
        return getNBT(stack).getString(key);
    }

    public static byte[] getByteArray(ItemStack stack, String key) {
        return getNBT(stack).getByteArray(key);
    }

    public static int[] getIntArray(ItemStack stack, String key) {
        return getNBT(stack).getIntArray(key);
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        return getNBT(stack).getBoolean(key);
    }

}
