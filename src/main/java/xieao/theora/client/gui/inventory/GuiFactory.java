package xieao.theora.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.FMLPlayMessages;
import xieao.theora.block.TileBase;
import xieao.theora.world.IInteractObj;

import javax.annotation.Nullable;

public class GuiFactory {
    @Nullable
    public static GuiScreen get(FMLPlayMessages.OpenContainer openContainer) {
        EntityPlayer player = Minecraft.getInstance().player;
        PacketBuffer packetBuffer = openContainer.getAdditionalData();
        String str = packetBuffer.readString(32767);
        if (str.equals("tile.gui")) {
            BlockPos pos = packetBuffer.readBlockPos();
            if (!pos.equals(BlockPos.ORIGIN)) {
                Minecraft mc = Minecraft.getInstance();
                TileEntity tileEntity = mc.world.getTileEntity(pos);
                if (tileEntity instanceof TileBase && tileEntity instanceof IInteractObj) {
                    IInteractObj obj = (IInteractObj) tileEntity;
                    String guiId = obj.getGuiID();
                    if (guiId.equals(openContainer.getId().toString())) {
                        return obj.getGui(player, EnumHand.MAIN_HAND);
                    }
                }
            }
        } else if (str.equals("item.gui")) {
            EnumHand hand = packetBuffer.readEnumValue(EnumHand.class);
            ItemStack held = player.getHeldItem(hand);
            if (held.getItem() instanceof IInteractObj) {
                IInteractObj obj = (IInteractObj) held.getItem();
                return obj.getGui(player, hand);
            }
        }
        return null;
    }
}
