package xieao.theora.network;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final GuiHandler INSTANCE = new GuiHandler();
    public static final int ID_TILE_ENTITY = 0;
    public static final int ID_ITEM = 1;
    public static final int ID_PLAYER_BINDER = 100;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final BlockPos pos = new BlockPos(x, y, z);
        if (ID == ID_TILE_ENTITY) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof IGuiTile) {
                IGuiTile guiTile = (IGuiTile) tileEntity;
                return guiTile.getContainer(player);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final BlockPos pos = new BlockPos(x, y, z);
        if (ID == ID_TILE_ENTITY) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof IGuiTile) {
                IGuiTile guiTile = (IGuiTile) tileEntity;
                return guiTile.getGui(player);
            }
        }
        return null;
    }

    public interface IGuiTile {

        @SideOnly(Side.CLIENT)
        GuiContainer getGui(EntityPlayer player);

        Container getContainer(EntityPlayer player);
    }
}
