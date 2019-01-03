package xieao.theora.common.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xieao.lib.util.WorldUtil;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.block.misc.BlockShroom;
import xieao.theora.common.config.ConfigWorldGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class WorldGenShrooms {

    private static final List<MushroomEntry> MUSHROOM_ENTRIES = new ArrayList<>();

    static {
        if (ConfigWorldGen.enabled) {
            for (BlockShroom.Type type : BlockShroom.Type.values()) {
                MUSHROOM_ENTRIES.add(new MushroomEntry(TheoraBlocks.SHROOM.getDefaultState()
                        .withProperty(BlockShroom.TYPE, type), type.weight));
            }
        }
    }

    @SubscribeEvent
    public static void decorate(DecorateBiomeEvent.Decorate event) {
        if (ConfigWorldGen.enabled) {
            if (event.getType() == DecorateBiomeEvent.Decorate.EventType.SHROOM) {
                World world = event.getWorld();
                Random rand = event.getRand();
                if (rand.nextInt(8) == 0) {
                    ChunkPos chunkPos = event.getChunkPos();
                    MushroomEntry randomItem = WeightedRandom.getRandomItem(rand, MUSHROOM_ENTRIES);
                    for (int i = 0; i < 7; i++) {
                        int x = (chunkPos.x << 4) + rand.nextInt(16) + 8;
                        int z = (chunkPos.z << 4) + rand.nextInt(16) + 8;
                        BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
                        Biome biome = world.getBiome(pos);
                        if (!WorldUtil.hasBiomeTypes(biome, HOT, DRY, BEACH, DEAD, END, OCEAN, NETHER)) {
                            IBlockState state = randomItem.state;
                            if (world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos) && state.getBlock().canPlaceBlockAt(world, pos)) {
                                world.setBlockState(pos, state, 2);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class MushroomEntry extends WeightedRandom.Item {

        public final IBlockState state;

        public MushroomEntry(IBlockState state, int weight) {
            super(weight);
            this.state = state;
        }
    }
}
