package xieao.theora.api.player.ability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class Abilities implements INBTSerializable<NBTTagCompound> {

    private final HashMap<Ability, NBTTagCompound> abilityMap = new HashMap<>();

    public static final String TAG_STATUS = "abilityStatus";
    public static final String TAG_LEVEL = "abilityLevel";
    public static final String TAG_SUB_NBT = "abilitySubNbt";

    private boolean sync;

    public boolean hasAbility(Ability ability) {
        return this.abilityMap.containsKey(ability);
    }

    public boolean acquire(Ability ability) {
        if (!ability.isEmpty() && !hasAbility(ability)) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean(TAG_STATUS, true);
            nbt.setInteger(TAG_LEVEL, 0);
            nbt.setTag(TAG_SUB_NBT, new NBTTagCompound());
            this.abilityMap.put(ability, nbt);
            return true;
        }
        return false;
    }

    public boolean lose(Ability ability) {
        if (hasAbility(ability)) {
            this.abilityMap.remove(ability);
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

    public NBTTagCompound getSubNbt(Ability ability) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            return nbt.getCompoundTag(TAG_SUB_NBT);
        }
        return new NBTTagCompound();
    }

    public boolean setAbilitySubNbt(Ability ability, NBTTagCompound subNbt) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            NBTTagCompound curNbt = nbt.getCompoundTag(TAG_SUB_NBT);
            if (curNbt != subNbt) {
                nbt.setTag(TAG_SUB_NBT, subNbt);
                return true;
            }
        }
        return false;
    }

    public NBTTagCompound getAbilityNbt(Ability ability) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        return nbt == null ? new NBTTagCompound() : nbt;
    }

    public boolean doSync() {
        return sync;
    }

    public void sync(boolean sync) {
        this.sync = sync;
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
