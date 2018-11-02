package xieao.theora.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.client.renderer.item.ItemRenderer;

import java.util.Objects;

public class ItemBase extends Item implements IGenericItem {

    public ItemBase() {
        setTileEntityItemStackRenderer(
                new ItemRenderer()
        );
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderItem() {
        ResourceLocation rl = getRegistryName();
        Objects.requireNonNull(rl);
        ModelResourceLocation mrl = new ModelResourceLocation(rl, "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, mrl);
    }
}
