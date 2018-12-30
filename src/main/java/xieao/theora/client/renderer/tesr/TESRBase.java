package xieao.theora.client.renderer.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import xieao.lib.block.TileBase;
import xieao.theora.client.renderer.model.ModelCube;

public class TESRBase<T extends TileBase> extends TileEntitySpecialRenderer<T> {

    protected static final ModelCube MODEL_CUBE = new ModelCube();

    public final Minecraft mc = Minecraft.getMinecraft();

    @SuppressWarnings("unchecked")
    public T te = (T) new TileBase();

    @SuppressWarnings("unchecked")
    @Override
    public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        this.te = te;
    }

    @Override
    public boolean isGlobalRenderer(T te) {
        return te.getRenderBoundingBox().equals(TileEntity.INFINITE_EXTENT_AABB);
    }

}
