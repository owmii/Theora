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
import xieao.theora.item.IItem;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IBlocks {
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block CAULDRON;
    public static final Block HEAT;
    public static final Block MUSH_GLIOPHORUS;
    public static final Block MUSH_AMANITA_MUSCARIA;
    public static final Block MUSH_WHITE_BEECH;
    public static final Block MUSH_WITCH_HATE;

    static {
        CAULDRON = register("cauldron", new BlockCauldron());
        HEAT = register("heat", new BlockHeat(24000));
        MUSH_GLIOPHORUS = register("mush_gliophorus", new BlockPlant(Items.CACTUS_GREEN, 3).setShearable());
        MUSH_AMANITA_MUSCARIA = register("mush_amanita_muscaria", new BlockPlant(Items.APPLE, 3).setShearable());
        MUSH_WHITE_BEECH = register("mush_white_beech", new BlockPlant(Items.BONE_MEAL, 3).setShearable());
        MUSH_WITCH_HATE = register("mush_witch_hate", new BlockPlant(Items.BRICK, 3).setShearable());
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