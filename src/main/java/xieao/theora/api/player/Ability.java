package xieao.theora.api.player;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import xieao.theora.api.registry.RegistryEntry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ability extends RegistryEntry<Ability> {
    public static final Map<ResourceLocation, Ability> REGISTRY = new HashMap<>();
    public static final Ability EMPTY = register("theora:empty", new Ability(), 0);
    private int maxLevel;

    public static Ability register(String name, Ability ability, int maxLevel) {
        ability.setRegistryName(name);
        ability.maxLevel = maxLevel;
        REGISTRY.put(ability.getRegistryName(), ability);
        return ability;
    }

    public static Ability get(String name) {
        Ability ability = REGISTRY.get(new ResourceLocation(name));
        return ability == null ? EMPTY : ability;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public void tick(EntityPlayer player, NBTTagCompound abilityNBT) {
    }

    public void onAdded(EntityPlayer player, NBTTagCompound abilityNBT) {
    }

    public void onRemoved(EntityPlayer player) {
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getIcon() {
        return new ResourceLocation(getNamespace(), "textures/abilities/" + getPath() + ".png");
    }

    @OnlyIn(Dist.CLIENT)
    public GuiScreen getGui(Data data) {
        return new GuiScreenDemo();
    }

    public static class Data {
        private final Map<Ability, NBTTagCompound> abilityMap = new HashMap<>();

        public boolean add(Ability ability, EntityPlayer player) {
            if (has(ability)) return false;
            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("Enabled", true);
            compound.setInt("Level", 0);
            compound.setTag("Data", new NBTTagCompound());
            this.abilityMap.put(ability, compound);
            ability.onAdded(player, compound.getCompound("Data"));
            return true;
        }

        public boolean remove(Ability ability, EntityPlayer player) {
            if (!has(ability)) return false;
            this.abilityMap.remove(ability);
            ability.onRemoved(player);
            return true;
        }

        public boolean has(Ability ability) {
            return this.abilityMap.containsKey(ability);
        }

        public NBTTagCompound getNBT(Ability ability) {
            return this.abilityMap.get(ability);
        }

        public NBTTagCompound getAbilityNBT(Ability ability) {
            return getNBT(ability).getCompound("Data");
        }

        public boolean enabled(Ability ability) {
            return getNBT(ability).getBoolean("Enabled");
        }

        public void onOrOff(Ability ability) {
            NBTTagCompound compound = getNBT(ability);
            compound.setBoolean("Enabled", !compound.getBoolean("Enabled"));
        }

        public int level(Ability ability) {
            return getNBT(ability).getInt("Level");
        }

        public void levelUp(Ability ability) {
            getNBT(ability).setInt("Level", Math.min(ability.maxLevel, level(ability) + 1));
        }

        public void levelDown(Ability ability) {
            getNBT(ability).setInt("Level", Math.max(0, level(ability) - 1));
        }

        public NBTTagCompound write(NBTTagCompound compound) {
            NBTTagList list = new NBTTagList();
            this.abilityMap.forEach((ability, compound1) -> {
                NBTTagCompound compound2 = new NBTTagCompound();
                compound2.setString("AbilityId", ability.getRegistryString());
                compound2.setTag("AbilityNBT", compound1);
                list.add(compound2);
            });
            compound.setTag("AbilityList", list);
            return compound;
        }

        public void read(NBTTagCompound compound) {
            NBTTagList list = compound.getList("AbilityList", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                NBTTagCompound compound1 = list.getCompound(i);
                this.abilityMap.put(get(compound1.getString("AbilityId")), compound1.getCompound("AbilityNBT"));
            }
        }

        public Map<Ability, NBTTagCompound> getAbilityMap() {
            return Collections.unmodifiableMap(this.abilityMap);
        }
    }
}
