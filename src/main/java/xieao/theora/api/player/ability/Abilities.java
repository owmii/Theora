package xieao.theora.api.player.ability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class Abilities implements INBTSerializable<NBTTagCompound> {

    public static final String TAG_STATUS = "status";
    public static final String TAG_LEVEL = "level";

    private final HashMap<Ability, NBTTagCompound> abilityMap = new HashMap<>();

    public boolean hasAbility(Ability ability) {
        return this.abilityMap.containsKey(ability);
    }

    public boolean acquire(Ability ability) {
        if (!hasAbility(ability)) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean(TAG_STATUS, true);
            nbt.setInteger(TAG_LEVEL, 0);
            this.abilityMap.put(ability, nbt);
            return true;
        }
        return false;
    }

    public int getAbilityLevel(Ability ability) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            return nbt.getInteger(TAG_LEVEL);
        }
        return 0;
    }


    public boolean setAbilityLevel(Ability ability, int level) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            int curLevel = nbt.getInteger(TAG_LEVEL);
            if (curLevel != level) {
                nbt.setInteger(TAG_LEVEL, level);
                return true;
            }
        }
        return false;
    }

    public boolean isActive(Ability ability) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            return nbt.getBoolean(TAG_LEVEL);
        }
        return false;
    }

    public boolean setAbilityStatus(Ability ability, boolean status) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            boolean curStatus = nbt.getBoolean(TAG_LEVEL);
            if (curStatus != status) {
                nbt.setBoolean(TAG_STATUS, status);
                return true;
            }
        }
        return false;
    }

    public NBTTagCompound getAbilityNbt(Ability ability) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        return nbt == null ? new NBTTagCompound() : nbt;
    }

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

    public HashMap<Ability, NBTTagCompound> getAbilityMap() {
        return abilityMap;
    }
}
