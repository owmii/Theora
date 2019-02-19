package xieao.theora.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.FMLPlayMessages;
import xieao.theora.block.base.Tile;
import xieao.theora.world.IInteractObj;

import javax.annotation.Nullable;

public class GuiFactory {
    @Nullable
    public static GuiScreen get(FMLPlayMessages.OpenContainer openContainer) {
        PacketBuffer packetBuffer = openContainer.getAdditionalData();
        BlockPos pos = packetBuffer.readBlockPos();
        if (!pos.equals(BlockPos.ORIGIN)) {
            Minecraft mc = Minecraft.getInstance();
            TileEntity tileEntity = mc.world.getTileEntity(pos);
            if (tileEntity instanceof Tile && tileEntity instanceof IInteractObj) {
                IInteractObj object = (IInteractObj) tileEntity;
                String guiId = object.getGuiID();
                if (guiId.equals(openContainer.getId().toString())) {
                    return object.getGui();
                }
            }
        }
        return null;
    }
}
