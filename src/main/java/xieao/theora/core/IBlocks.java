package xieao.theora.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.block.IBlockBase;
import xieao.theora.block.gate.BlockGate;
import xieao.theora.block.gate.BlockGatePart;
import xieao.theora.item.IItemBase;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IBlocks {
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block GATE;
    public static final Block GATE_PART;

    static {
        GATE = register("gate", new BlockGate(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, Float.MAX_VALUE)));
        GATE_PART = register("gate_part", new BlockGatePart(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, Float.MAX_VALUE)));
    }

    static <T extends Block & IBlockBase> T register(String name, T block) {
        ItemBlock itemBlock = block.getItemBlock(new Item.Properties().group(IItemBase.MAIN));
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
