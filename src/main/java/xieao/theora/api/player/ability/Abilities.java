package xieao.theora.api.player.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;
import java.util.TreeMap;

public class Abilities implements INBTSerializable<NBTTagCompound> {

    public static final String TAG_STATUS = "abilityStatus";
    public static final String TAG_LEVEL = "abilityLevel";
    public static final String TAG_SUB_NBT = "abilitySubNbt";

    private final TreeMap<Ability, NBTTagCompound> abilityMap = new TreeMap<>((a0, a1) -> a0 != null && a1 != null ? a0.getRegistryString().compareTo(a1.getRegistryString()) : 1);

    private boolean sync;

    public boolean hasAbility(Ability ability) {
        return this.abilityMap.containsKey(ability);
    }

    public boolean acquire(EntityPlayer player, Ability ability) {
        if (!ability.isEmpty() && !hasAbility(ability)) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean(TAG_STATUS, true);
            nbt.setInteger(TAG_LEVEL, 0);
            nbt.setTag(TAG_SUB_NBT, new NBTTagCompound());
            this.abilityMap.put(ability, nbt);
            ability.onAcquired(player, player.world, getAbilityLevel(ability), getAbilityNbt(ability).getCompoundTag(TAG_SUB_NBT));
            return true;
        }
        return false;
    }

    public boolean lose(EntityPlayer player, Ability ability) {
        if (hasAbility(ability)) {
            this.abilityMap.remove(ability);
            ability.onRemoved(player, player.world, getAbilityLevel(ability));
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

    public boolean setAbilityLevel(EntityPlayer player, Ability ability, int level) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            int curLevel = nbt.getInteger(TAG_LEVEL);
            if (level < ability.getMaxLevel() && curLevel != level) {
                nbt.setInteger(TAG_LEVEL, level);
                if (curLevel < level) {
                    ability.onLevelUp(player, player.world, level, getAbilityNbt(ability).getCompoundTag(TAG_SUB_NBT));
                } else {
                    ability.onLevelDown(player, player.world, level, getAbilityNbt(ability).getCompoundTag(TAG_SUB_NBT));
                }
                return true;
            }
        }
        return false;
    }

    public boolean isActive(Ability ability) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            return nbt.getBoolean(TAG_STATUS);
        }
        return false;
    }

    public boolean setAbilityStatus(Ability ability, boolean status) {
        NBTTagCompound nbt = this.abilityMap.get(ability);
        if (nbt != null) {
            boolean curStatus = nbt.getBoolean(TAG_STATUS);
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
            String registryName = nbt1.getString("registryName");
            NBTTagCompound abilityNbt = nbt1.getCompoundTag("abilityNbt");
            Ability ability = Ability.getAbility(registryName);
            if (!ability.isEmpty()) {
                this.abilityMap.put(ability, abilityNbt);
            }
        }
    }

    public boolean isEmpty() {
        return getAbilityMap().isEmpty();
    }

    public TreeMap<Ability, NBTTagCompound> getAbilityMap() {
        return abilityMap;
    }
}
