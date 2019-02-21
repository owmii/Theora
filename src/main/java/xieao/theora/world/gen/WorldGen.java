package xieao.theora.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.CompositeFeature;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraftforge.common.BiomeDictionary;
import xieao.theora.core.config.Config;
import xieao.theora.world.gen.feature.PlantConfig;
import xieao.theora.world.gen.feature.PlantFeature;

public class WorldGen {
    public static void register() {
        if (!Config.GENERAL.worldGen.get()) return;
        if (Config.GENERAL.worldGenPlants.get()) {
            BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST).forEach(biome -> {
                if (BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY).contains(biome)) return;
                final CompositeFeature<?, ?> feature = Biome.createCompositeFeature(PlantFeature.INSTANCE,
                        new PlantConfig(), Biome.SURFACE_PLUS_32, new FrequencyConfig(1));
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
            });
        }
    }
}
