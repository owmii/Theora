package xieao.theora.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EnchantmentSoulander extends Enchantment {

    protected EnchantmentSoulander(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void blackList(AnvilUpdateEvent event) {
        ItemStack right = event.getRight();
        ItemStack left = event.getLeft();
        if (right.getItem() instanceof ItemEnchantedBook || !right.isItemEqual(left)) {
            if (EnchantmentHelper.getEnchantmentLevel(TheoraEnchantments.SOULANDER, right) > 0) {
                event.setCanceled(true);
            }
        }
    }
}
