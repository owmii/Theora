package xieao.theora.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

public class PlayerUtil {
    public static boolean isFake(EntityPlayer player) {
        return player instanceof FakePlayer;
    }
}
