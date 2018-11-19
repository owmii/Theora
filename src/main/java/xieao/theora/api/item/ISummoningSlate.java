package xieao.theora.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

import java.util.List;

public interface ISummoningSlate {

    List<Biome.SpawnListEntry> getSpawnListEntries(ItemStack stack);

}
