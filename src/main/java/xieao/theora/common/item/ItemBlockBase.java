package xieao.theora.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.client.renderer.item.ItemRenderer;
import xieao.theora.common.lib.book.BookEntry;

import javax.annotation.Nullable;

public class ItemBlockBase extends ItemBlock implements IGenericItem, IBookItemBlock {

    @Nullable
    private Pair<BookEntry, Integer> bookPage;

    public ItemBlockBase(Block block) {
        super(block);
        setTileEntityItemStackRenderer(
                new ItemRenderer()
        );
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (getSubTypeValues().length > 0) {
            int meta = stack.getMetadata();
            return super.getUnlocalizedName() + "." + getSubTypeValues()[meta]
                    .toString().toLowerCase().replace("_", ".");
        } else {
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderItem() {
        if (getSubTypeValues().length > 0) {
            for (Enum<?> enumType : getSubTypeValues()) {
                ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName() + "_" + enumType.name().toLowerCase(), "inventory");
                ModelLoader.setCustomModelResourceLocation(this, enumType.ordinal(), mrl);
            }
        } else {
            ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(this, 0, mrl);
        }
    }

    @Override
    public Enum<?>[] getSubTypeValues() {
        return new Enum[0];
    }

    @Override
    @Nullable
    public Pair<BookEntry, Integer> getBookPage() {
        return bookPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemBlockBase & IBookItemBlock> T setBookPage(BookEntry entry, int index) {
        this.bookPage = new ImmutablePair<>(entry, index);
        return (T) this;
    }
}
