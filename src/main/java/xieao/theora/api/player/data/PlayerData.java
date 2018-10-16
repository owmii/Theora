package xieao.theora.api.player.data;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerData implements IPlayerData {

    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }
}
