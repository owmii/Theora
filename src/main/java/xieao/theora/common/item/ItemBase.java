package xieao.theora.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.client.renderer.item.ItemRenderer;

public class ItemBase extends Item implements IGenericItem {

    public ItemBase() {
        setTileEntityItemStackRenderer(
                new ItemRenderer()
        );
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
}
