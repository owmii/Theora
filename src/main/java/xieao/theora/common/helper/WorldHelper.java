package xieao.theora.common.helper;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class WorldHelper {

    public static boolean hasBiomeTypes(Biome biome, Type... types) {
        for (Type biomeType : types) {
            if (BiomeDictionary.hasType(biome, biomeType)) {
                return true;
            }
        }
        return false;
    }
}
