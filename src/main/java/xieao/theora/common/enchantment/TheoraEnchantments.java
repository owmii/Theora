package xieao.theora.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TheoraEnchantments {

    public static final Enchantment SOULANDER = new EnchantmentSoulander(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});

    public static void register() {
        register("soulander", SOULANDER);
    }

    public static void register(String name, Enchantment enchantment) {
        enchantment.setRegistryName(name);
        enchantment.setName(enchantment.getRegistryName().toString().replace(":", "."));
        ForgeRegistries.ENCHANTMENTS.register(enchantment);
    }
}
