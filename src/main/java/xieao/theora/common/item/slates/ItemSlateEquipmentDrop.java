package xieao.theora.common.item.slates;

import net.minecraft.item.ItemStack;
import xieao.theora.api.item.slate.IEquipmentDropSlate;
import xieao.theora.common.item.ItemBase;

public class ItemSlateEquipmentDrop extends ItemBase implements IEquipmentDropSlate {

    @Override
    public float getLiquidCost(ItemStack stack) {
        return 8.0F;
    }
}
