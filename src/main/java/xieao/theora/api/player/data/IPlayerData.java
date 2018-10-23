package xieao.theora.api.player.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerData extends INBTSerializable<NBTTagCompound> {

    @Override
    default NBTTagCompound serializeNBT() {
        return serializeNBT(new NBTTagCompound());
    }

    NBTTagCompound serializeNBT(NBTTagCompound nbt);
}
