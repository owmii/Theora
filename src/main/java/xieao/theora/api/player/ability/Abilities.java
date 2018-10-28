package xieao.theora.api.player.ability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class Abilities implements INBTSerializable<NBTTagCompound> {

    private final Map<Ability, NBTTagCompound> abilityMap = new HashMap<>();

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList tagList = new NBTTagList();
        for (Map.Entry<Ability, NBTTagCompound> entry : this.abilityMap.entrySet()) {
            NBTTagCompound nbt1 = new NBTTagCompound();
            nbt1.setString("registryName", entry.getKey().getRegistryString());
            nbt1.setTag("abilityNbt", entry.getValue());
            tagList.appendTag(nbt1);
        }
        nbt.setTag("tagList", tagList);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getTagList("tagList", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound nbt1 = tagList.getCompoundTagAt(i);
            String regestryName = nbt1.getString("registryName");
            NBTTagCompound abilityNbt = nbt1.getCompoundTag("abilityNbt");
            Ability ability = Ability.getAbility(regestryName);
            if (!ability.isEmpty()) {
                this.abilityMap.put(ability, abilityNbt);
            }
        }
    }

    public Map<Ability, NBTTagCompound> getAbilityMap() {
        return abilityMap;
    }
}
