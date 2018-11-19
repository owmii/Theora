package xieao.theora.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraftforge.common.DimensionManager;
import xieao.theora.api.item.ISummoningSlate;

import java.util.ArrayList;
import java.util.List;

public class ItemSummoningSlate extends ItemBase implements ISummoningSlate {

    public static final List<Biome.SpawnListEntry> SPAWN_LIST_NETHER = new ArrayList<>();
    public static final List<Biome.SpawnListEntry> SPAWN_LIST_NETHER_FORTRESS = new ArrayList<>();
    public static final List<Biome.SpawnListEntry> SPAWN_LIST_END = new ArrayList<>();
    public static final List<Biome.SpawnListEntry> SPAWN_LIST_OCEAN_MONUMENT = new ArrayList<>();

    public static void postInit() {
        World world = DimensionManager.getWorld(0); //OverWorld
        world.getTotalWorldTime();

        world = DimensionManager.getWorld(-1); //Nether World
        ChunkGeneratorHell chunkGeneratorHell = new ChunkGeneratorHell(world, world.getWorldInfo().isMapFeaturesEnabled(), world.getSeed());

        world = DimensionManager.getWorld(1); //End World
        world.getTotalWorldTime();
    }

    @Override
    public List<Biome.SpawnListEntry> getSpawnListEntries(ItemStack stack) {
        return Type.values()[stack.getMetadata()].spawnListEntries;
    }

    @Override
    public Enum<?>[] getSubTypeValues() {
        return Type.values();
    }

    public enum Type {
        NETHER(SPAWN_LIST_NETHER),
        NETHER_FORTRESS(SPAWN_LIST_NETHER_FORTRESS),
        END(SPAWN_LIST_END),
        OCEAN_MONUMENT(SPAWN_LIST_OCEAN_MONUMENT);

        public final List<Biome.SpawnListEntry> spawnListEntries;

        Type(List<Biome.SpawnListEntry> spawnListEntries) {
            this.spawnListEntries = spawnListEntries;
        }
    }
}
