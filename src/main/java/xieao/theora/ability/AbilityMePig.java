package xieao.theora.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import xieao.theora.api.player.Ability;

public class AbilityMePig extends Ability {
    @Override
    public void tick(EntityPlayer player, Data data) {
        super.tick(player, data);
    }

    @Override
    public void onAdded(EntityPlayer player, NBTTagCompound abilityNBT) {
        super.onAdded(player, abilityNBT);
    }

    @Override
    public void onRemoved(EntityPlayer player) {
        super.onRemoved(player);
    }
}
