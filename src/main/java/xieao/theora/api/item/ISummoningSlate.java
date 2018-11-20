package xieao.theora.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

import java.util.Set;

public interface ISummoningSlate {

    Set<Biome.SpawnListEntry> getSpawnListEntries(ItemStack stack);

}
