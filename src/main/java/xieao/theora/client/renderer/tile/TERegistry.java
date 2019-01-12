package xieao.theora.client.renderer.tile;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import xieao.theora.block.cauldron.TileCauldron;

@OnlyIn(Dist.CLIENT)
public class TERegistry {

    public static void registerAll() {
        bind(TileCauldron.class, new CauldronRenderer());
    }

    public static <T extends TileEntity> void bind(Class<T> clazz, TileEntityRenderer<? super T> renderer) {
        ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
    }
}
