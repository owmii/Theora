package xieao.theora.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.client.renderer.item.ItemRenderer;
import xieao.theora.common.lib.book.Entry;

import javax.annotation.Nullable;

public class ItemBase extends Item implements IGenericItem, IBookItem {

    @Nullable
    private Pair<Entry, Integer> bookPage;

    public ItemBase() {
        setTileEntityItemStackRenderer(
                new ItemRenderer()
        );
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (getSubTypes().length > 0) {
            int meta = stack.getMetadata();
            return super.getUnlocalizedName() + "." + getSubTypes()[meta]
                    .toString().toLowerCase().replace("_", ".");
        } else {
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            boolean flag = false;
            for (Enum<?> type : getSubTypes()) {
                items.add(new ItemStack(this, 1, type.ordinal()));
                flag = true;
            }
            if (!flag) {
                items.add(new ItemStack(this));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderItem() {
        if (getSubTypes().length > 0) {
            for (Enum<?> enumType : getSubTypes()) {
                ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName() + "_" + enumType.name().toLowerCase(), "inventory");
                ModelLoader.setCustomModelResourceLocation(this, enumType.ordinal(), mrl);
            }
        } else {
            ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(this, 0, mrl);
        }
    }


    @Override
    public Enum<?>[] getSubTypes() {
        return new Enum[0];
    }

    @Override
    @Nullable
    public Pair<Entry, Integer> getBookPage() {
        return bookPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemBase & IBookItem> T setBookPage(Entry entry, int index) {
        this.bookPage = new ImmutablePair<>(entry, index);
        return (T) this;
    }
}
