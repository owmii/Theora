package xieao.theora.client.renderer.tile;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.block.Tile;


@OnlyIn(Dist.CLIENT)
public abstract class TERenderer<T extends Tile> extends TileEntityRenderer<T> {

    @Override
    public boolean isGlobalRenderer(T te) {
        return te.getRenderBoundingBox() == Tile.INFINITE_EXTENT_AABB || super.isGlobalRenderer(te);
    }
}
