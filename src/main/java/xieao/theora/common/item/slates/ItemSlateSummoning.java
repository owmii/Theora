package xieao.theora.common.item.slates;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraft.world.gen.structure.StructureOceanMonument;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import xieao.lib.item.ItemBase;
import xieao.lib.util.NBTUtil;
import xieao.theora.api.item.slate.ISummoningSlate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ItemSlateSummoning extends ItemBase implements ISummoningSlate {

    public static final String TAG_BIOME_ID = "biomeId";

    public static final Set<Biome.SpawnListEntry> SPAWN_LIST_NETHER = new HashSet<>();
    public static final Set<Biome.SpawnListEntry> SPAWN_LIST_NETHER_FORTRESS = new HashSet<>();
    public static final Set<Biome.SpawnListEntry> SPAWN_LIST_END = new HashSet<>();
    public static final Set<Biome.SpawnListEntry> SPAWN_LIST_OCEAN_MONUMENT = new HashSet<>();

    public void initSpawnLists() {
        StructureOceanMonument oceanMonument = (StructureOceanMonument) TerrainGen.getModdedMapGen(new StructureOceanMonument(), InitMapGenEvent.EventType.OCEAN_MONUMENT);
        SPAWN_LIST_OCEAN_MONUMENT.addAll(oceanMonument.getMonsters());
        MapGenNetherBridge netherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(new MapGenNetherBridge(), InitMapGenEvent.EventType.NETHER_BRIDGE);
        SPAWN_LIST_NETHER.addAll(Biomes.HELL.getSpawnableList(EnumCreatureType.MONSTER));
        SPAWN_LIST_NETHER_FORTRESS.addAll(netherBridge.getSpawnList());
        SPAWN_LIST_END.addAll(Biomes.SKY.getSpawnableList(EnumCreatureType.MONSTER));
        cleanFromBosses(
                SPAWN_LIST_NETHER,
                SPAWN_LIST_NETHER_FORTRESS,
                SPAWN_LIST_END,
                SPAWN_LIST_OCEAN_MONUMENT
        );
    }

    @SafeVarargs
    private final void cleanFromBosses(Set<Biome.SpawnListEntry>... lists) {
        for (Set<Biome.SpawnListEntry> listEntries : lists) {
            listEntries.removeIf(spawnListEntry -> {
                boolean flag = false;
                try {
                    flag = !spawnListEntry.newInstance(DimensionManager.getWorld(0)).isNonBoss();
                } catch (Exception ignored) {
                }
                return flag;
            });
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (Type.values()[stack.getMetadata()] == Type.BIOME) {
            if (!NBTUtil.hasKey(stack, TAG_BIOME_ID, Constants.NBT.TAG_INT)) {
                int biomeId = Biome.getIdForBiome(worldIn.getBiome(playerIn.getPosition()));
                NBTUtil.setInteger(stack, TAG_BIOME_ID, biomeId);
            } else {
                playerIn.sendStatusMessage(new TextComponentString("theora.chat.slate.has.biome"), true);
            }
        }
        //TODO success and failed sounds
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public Set<Biome.SpawnListEntry> getSpawnListEntries(ItemStack stack) {
        if (Type.values()[stack.getMetadata()] == Type.BIOME) {
            if (NBTUtil.hasKey(stack, TAG_BIOME_ID, Constants.NBT.TAG_INT)) {
                int biomeId = NBTUtil.getInteger(stack, TAG_BIOME_ID);
                Biome biome = Biome.getBiome(biomeId);
                if (biome != null) {
                    return new HashSet<>(biome.getSpawnableList(EnumCreatureType.MONSTER));
                }
            }
        } else {
            if (SPAWN_LIST_NETHER.isEmpty()) {
                initSpawnLists();
            }
        }
        return Type.values()[stack.getMetadata()].spawnListEntries;
    }

    @Override
    public Enum<?>[] getSubTypes() {
        return Type.values();
    }

    @Override
    public float getLiquidCost(ItemStack stack) {
        return 0;
    }

    public enum Type {
        BIOME(Collections.emptySet()),
        NETHER(SPAWN_LIST_NETHER),
        NETHER_FORTRESS(SPAWN_LIST_NETHER_FORTRESS),
        END(SPAWN_LIST_END),
        OCEAN_MONUMENT(SPAWN_LIST_OCEAN_MONUMENT),
        VIBRANT(Collections.emptySet());

        public final Set<Biome.SpawnListEntry> spawnListEntries;

        Type(Set<Biome.SpawnListEntry> spawnListEntries) {
            this.spawnListEntries = spawnListEntries;
        }
    }
}
