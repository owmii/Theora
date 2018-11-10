package xieao.theora.api.player.data;

import net.minecraft.nbt.NBTTagCompound;
import xieao.theora.api.player.ability.Abilities;

public final class PlayerData implements IPlayerData {

    private final Abilities abilities = new Abilities();

    public boolean allowFlying;

    @Override
    public NBTTagCompound serializeNBT(NBTTagCompound nbt) {
        nbt.setTag("abilities", this.abilities.serializeNBT());
        nbt.setBoolean("allowFlying", this.allowFlying);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.abilities.deserializeNBT(nbt.getCompoundTag("abilities"));
        this.allowFlying = nbt.getBoolean("allowFlying");
    }

    public Abilities getAbilities() {
        return abilities;
    }
}
