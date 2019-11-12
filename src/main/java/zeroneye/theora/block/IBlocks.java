package zeroneye.theora.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zeroneye.lib.block.IBlockBase;
import zeroneye.theora.block.cauldron.CauldronBlock;
import zeroneye.theora.item.ItemGroups;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IBlocks {
    public static final List<BlockItem> BLOCK_ITEMS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block HOR_LOG;
    public static final Block GLIOPHIN;
    public static final Block CAULDRON;

    static {
        HOR_LOG = register("hor_log", new HorLogBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
        GLIOPHIN = register("gliophin", new LiquidBlock(Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F)));
        CAULDRON = register("cauldron", new CauldronBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F)));
    }

    static <T extends Block & IBlockBase> T register(String name, T block) {
        BlockItem itemBlock = block.getBlockItem(new Item.Properties(), ItemGroups.MAIN);
        itemBlock.setRegistryName(name);
        block.setRegistryName(name);
        BLOCK_ITEMS.add(itemBlock);
        BLOCKS.add(block);
        return block;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Block> event) {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
    }
}
