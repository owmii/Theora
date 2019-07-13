package xieao.theora.core;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.item.HorItem;
import xieao.theora.item.IItemBase;
import xieao.theora.item.VialItem;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IItems {
    public static final List<Item> ITEMS = new ArrayList<>(IBlocks.BLOCK_ITEMS);
    public static final Item HOR_CRYSTAL;
    public static final Item VIAL;

    static {
        HOR_CRYSTAL = register("hor_crystal", new HorItem(new Item.Properties()));
        VIAL = register("vial", new VialItem(new Item.Properties()));
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
