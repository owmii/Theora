package xieao.theora.core;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.block.IBlockBase;
import xieao.theora.block.horfire.HorFireBlock;
import xieao.theora.item.IItemBase;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IBlocks {
    public static final List<BlockItem> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block HOR_FIRE;

    static {
        HOR_FIRE = register("hor_fire", new HorFireBlock(Block.Properties.create(Material.FIRE).doesNotBlockMovement().lightValue(15).sound(SoundType.CLOTH)));
    }

    static <T extends Block & IBlockBase> T register(String name, T block) {
        BlockItem itemBlock = block.getItemBlock(new Item.Properties().group(IItemBase.MAIN));
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
