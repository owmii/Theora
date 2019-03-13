package xieao.theora.core;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.block.hor.TileHor;
import xieao.theora.block.hor.TileHorPart;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ITiles {
    public static final List<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();
    public static final TileEntityType<?> HOR = register("hor", TileHor::new);
    public static final TileEntityType<?> HOR_PART = register("hor_part", TileHorPart::new);

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    static <T extends TileEntity> TileEntityType<T> register(String id, Supplier<? extends T> factoryIn) {
        TileEntityType<T> type = (TileEntityType<T>) TileEntityType.Builder.create(factoryIn).build(null);
        type.setRegistryName(id);
        TILE_ENTITY_TYPES.add(type);
        return type;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
        TILE_ENTITY_TYPES.forEach(tileType -> event.getRegistry().register(tileType));
    }
}
