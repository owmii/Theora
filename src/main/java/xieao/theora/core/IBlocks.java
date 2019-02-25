package xieao.theora.core;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.block.BlockPlant;
import xieao.theora.block.base.IBlock;
import xieao.theora.block.cauldron.BlockCauldron;
import xieao.theora.block.heat.BlockHeat;
import xieao.theora.block.vessel.BlockVessel;
import xieao.theora.item.IItem;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IBlocks {
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block CAULDRON;
    public static final Block VESSEL;
    public static final Block HEAT;
    public static final Block MUSH_GLIOPHORUS;
    public static final Block MUSH_WITCH_HAT;

    static {
        CAULDRON = register("cauldron", new BlockCauldron());
        VESSEL = register("vessel", new BlockVessel());
        HEAT = register("heat", new BlockHeat(24000));
        MUSH_GLIOPHORUS = register("mush_gliophorus", new BlockPlant(Items.CACTUS_GREEN, 1, 2).setShearable());
        MUSH_WITCH_HAT = register("mush_witch_hat", new BlockPlant(Items.BRICK, 1, 2).setShearable());
    }

    static <T extends Block & IBlock> T register(String name, T block) {
        ItemBlock itemBlock = block.getItemBlock(new Item.Properties().group(IItem.MAIN));
        itemBlock.setRegistryName(name);
        block.setRegistryName(name);
        ITEM_BLOCKS.add(itemBlock);
        BLOCKS.add(block);
        return block;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Block> event) {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
    }
}