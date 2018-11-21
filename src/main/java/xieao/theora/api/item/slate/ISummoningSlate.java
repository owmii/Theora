package xieao.theora.api.item.slate;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

import java.util.Set;

public interface ISummoningSlate extends ISlate {

    Set<Biome.SpawnListEntry> getSpawnListEntries(ItemStack stack);

}
