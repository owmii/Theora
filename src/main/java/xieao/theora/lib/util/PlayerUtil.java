package xieao.theora.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.FakePlayer;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.HorData;
import xieao.theora.block.hor.TileHor;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerUtil {
    @Nullable
    public static TileHor getHor(EntityPlayer player) {
        final TileHor[] hor = {null};
        TheoraAPI.getPlayerData(player).ifPresent(playerData -> {
            HorData horData = playerData.hor;
            BlockPos pos = horData.getPos();
            DimensionType dimensionType = horData.getDimensionType();
            if (pos != null && dimensionType != null) {
                WorldServer worldServer = ServerUtil.getWorld(dimensionType);
                if (worldServer.isBlockLoaded(pos)) {
                    TileEntity tileEntity = worldServer.getTileEntity(pos);
                    if (tileEntity instanceof TileHor) {
                        hor[0] = (TileHor) tileEntity;
                    }
                }
            }
        });
        return hor[0];
    }

    public static boolean isFake(EntityPlayer player) {
        return player instanceof FakePlayer;
    }

    @Nullable
    public static EntityPlayerMP get(UUID uuid) {
        return ServerUtil.getServer().getPlayerList().getPlayerByUUID(uuid);
    }

    @Nullable
    public static EntityPlayerMP get(String name) {
        return ServerUtil.getServer().getPlayerList().getPlayerByUsername(name);
    }
}
