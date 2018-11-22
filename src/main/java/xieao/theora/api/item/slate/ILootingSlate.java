package xieao.theora.api.item.slate;

import net.minecraft.item.ItemStack;

public interface ILootingSlate extends ISlate {

    int getFortuneLevel(ItemStack stack);
}
