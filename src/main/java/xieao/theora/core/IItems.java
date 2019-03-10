package xieao.theora.core;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.item.IItemBase;
import xieao.theora.item.ItemPowder;
import xieao.theora.item.ItemStone;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IItems {
    public static final List<Item> ITEMS = new ArrayList<>(IBlocks.ITEM_BLOCKS);
    public static final Item POWDER;
    public static final Item STONE;

    static {
        POWDER = register("powder", new ItemPowder(new Item.Properties().group(IItemBase.MAIN).maxStackSize(1)));
        STONE = register("stone", new ItemStone(new Item.Properties().group(IItemBase.MAIN).maxStackSize(1)));
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
