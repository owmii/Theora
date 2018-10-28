package xieao.theora.api.player.data;

import net.minecraft.nbt.NBTTagCompound;
import xieao.theora.api.player.ability.Abilities;

public final class PlayerData implements IPlayerData {

    private final Abilities abilities = new Abilities();

    @Override
    public NBTTagCompound serializeNBT(NBTTagCompound nbt) {
        nbt.setTag("abilities", this.abilities.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.abilities.deserializeNBT(nbt.getCompoundTag("abilities"));
    }

    public Abilities getAbilities() {
        return abilities;
    }
}
