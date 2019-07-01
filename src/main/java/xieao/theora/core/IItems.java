package xieao.theora.core;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.item.HorItem;
import xieao.theora.item.IItemBase;
import xieao.theora.item.ItemBase;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IItems {
    public static final List<Item> ITEMS = new ArrayList<>(IBlocks.BLOCK_ITEMS);
    public static final Item HOR_CRYSTAL;
    public static final Item HOR_CRYSTAL_BIT;
    public static final Item HOR_WORM;
    public static final Item GOBEN_BONE;

    static {
        HOR_CRYSTAL = register("hor_crystal", new HorItem(new Item.Properties()));
        HOR_CRYSTAL_BIT = register("hor_crystal_bit", new HorItem(new Item.Properties()));
        HOR_WORM = register("hor_worm", new HorItem(new Item.Properties()));
        GOBEN_BONE = register("goben_bone", new ItemBase(new Item.Properties()));
    }

    static <T extends Item & IItemBase> T register(String name, T item) {
        item.setRegistryName(name);
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event) {
        ITEMS.forEach(item -> event.getRegistry().register(item));
    }
}
