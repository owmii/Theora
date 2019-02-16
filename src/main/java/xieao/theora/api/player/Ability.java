package xieao.theora.api.player;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import xieao.theora.api.Consts;
import xieao.theora.api.registry.RegistryEntry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ability extends RegistryEntry<Ability> {
    private static final Logger LOGGER = LogManager.getLogger(Consts.MOD_ID);
    public static final Marker REG = MarkerManager.getMarker("AbilityRegistry");
    public static final Map<ResourceLocation, Ability> REGISTRY = new HashMap<>();
    public static final Ability EMPTY = register("theora:empty", new Ability(), 1);
    private int maxLevel;

    public static Ability register(String name, Ability ability, int maxLevel) {
        ability.setRegistryName(name);
        if (maxLevel < 1) {
            LOGGER.fatal(REG, "Found ability {} with max level of {} changing it to 1", ability.getRegistryString(), maxLevel);
            maxLevel = 1;
        }
        ability.maxLevel = maxLevel;
        REGISTRY.put(ability.getRegistryName(), ability);
        return ability;
    }

    public static Ability get(String name) {
        Ability ability = REGISTRY.get(new ResourceLocation(name));
        return ability == null ? EMPTY : ability;
    }

    public static boolean exist(Ability ability) {
        return !get(ability.getRegistryString()).isEmpty();
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public void tick(EntityPlayer player, Data data) {
    }

    public void onAdded(EntityPlayer player, NBTTagCompound nbt) {
    }

    public void onRemoved(EntityPlayer player, NBTTagCompound nbt) {
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getIcon() {
        return new ResourceLocation(getNamespace(), "textures/abilities/" + getPath() + ".png");
    }

    @OnlyIn(Dist.CLIENT)
    public GuiScreen getGui() {
        return new AbilityGui(this);
    }

    public static class Data {
        private final Map<Ability, NBTTagCompound> abilityMap = new HashMap<>();
        public boolean sync;

        public boolean add(Ability ability, EntityPlayer player) {
            if (!exist(ability) || has(ability)) return false;
            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("Enabled", true);
            compound.setInt("Level", 0);
            compound.setTag("Data", new NBTTagCompound());
            this.abilityMap.put(ability, compound);
            ability.onAdded(player, compound.getCompound("Data"));
            sync(true);
            return true;
        }

        public boolean remove(Ability ability, EntityPlayer player) {
            if (!has(ability)) return false;
            this.abilityMap.remove(ability);
            ability.onRemoved(player, storable(ability));
            sync(true);
            return true;
        }

        public boolean has(Ability ability) {
            return this.abilityMap.containsKey(ability);
        }

        private NBTTagCompound getMainNBT(Ability ability) {
            return this.abilityMap.get(ability);
        }

        public NBTTagCompound storable(Ability ability) {
            return getMainNBT(ability).getCompound("Data");
        }

        public boolean enabled(Ability ability) {
            return getMainNBT(ability).getBoolean("Enabled");
        }

        public void onOrOff(Ability ability) {
            NBTTagCompound compound = getMainNBT(ability);
            compound.setBoolean("Enabled", !compound.getBoolean("Enabled"));
        }

        public int level(Ability ability) {
            return getMainNBT(ability).getInt("Level");
        }

        public void levelUp(Ability ability) {
            getMainNBT(ability).setInt("Level", Math.min(ability.maxLevel, level(ability) + 1));
            sync(true);
        }

        public void levelDown(Ability ability) {
            getMainNBT(ability).setInt("Level", Math.max(0, level(ability) - 1));
            sync(true);
        }

        public NBTTagCompound write(NBTTagCompound compound) {
            NBTTagList list = new NBTTagList();
            this.abilityMap.forEach((ability, compound1) -> {
                if (exist(ability)) {
                    NBTTagCompound compound2 = new NBTTagCompound();
                    compound2.setString("AbilityId", ability.getRegistryString());
                    compound2.setTag("AbilityNBT", compound1);
                    list.add(compound2);
                }
            });
            compound.setTag("AbilityList", list);
            return compound;
        }

        public void read(NBTTagCompound compound) {
            NBTTagList list = compound.getList("AbilityList", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                NBTTagCompound compound1 = list.getCompound(i);
                Ability ability = get(compound1.getString("AbilityId"));
                if (exist(ability)) {
                    this.abilityMap.put(ability, compound1.getCompound("AbilityNBT"));
                }
            }
        }

        public void sync(boolean sync) {
            this.sync = sync;
        }

        public Map<Ability, NBTTagCompound> getAbilityMap() {
            return Collections.unmodifiableMap(this.abilityMap);
        }
    }
}
