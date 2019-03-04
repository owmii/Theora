package xieao.theora.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;
import xieao.theora.api.Consts;
import xieao.theora.core.IItems;

public interface IItemBase extends IForgeItem {
    ItemGroup MAIN = new ItemGroup(Consts.MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(IItems.POWDER);
        }
    };
}
