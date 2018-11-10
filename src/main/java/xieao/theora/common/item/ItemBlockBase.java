package xieao.theora.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.client.renderer.item.ItemRenderer;

public class ItemBlockBase extends ItemBlock implements IGenericItem {

    public ItemBlockBase(Block block) {
        super(block);
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
