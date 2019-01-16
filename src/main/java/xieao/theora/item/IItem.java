package xieao.theora.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;
import xieao.theora.client.renderer.item.TEItemRenderer;

public interface IItem extends IForgeItem {
    default boolean isCreativeItem(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().getBoolean("onelib.isCreative");
    }

    default void setCreativeItem(ItemStack stack, boolean creative) {
        stack.getOrCreateTag().setBoolean("onelib.isCreative", creative);
    }

    @OnlyIn(Dist.CLIENT)
    default void initRenderer() {

    }

    @OnlyIn(Dist.CLIENT)
    default boolean renderByItem(ItemStack stack) {
        return false;
    }

    class Base extends Item implements IItem {
        public Base(Builder builder) {
            super(builder.setTEISR(() -> TEItemRenderer::new));
        }
    }

    class Block extends ItemBlock implements IItem {
        public Block(net.minecraft.block.Block block, Builder builder) {
            super(block, builder.setTEISR(() -> TEItemRenderer::new));
        }
    }
}
