package xieao.theora.core.lib.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class Registry {
    public static void register(Block block) {
        ForgeRegistries.BLOCKS.register(block);
    }

    public static void register(Item item) {
        ForgeRegistries.ITEMS.register(item);
    }

    public static void register(ResourceLocation location, TileEntityType<?> tileEntityType) {
        tileEntityType.setRegistryName(location);
        ForgeRegistries.TILE_ENTITIES.register(tileEntityType);
    }

    public static void register(ResourceLocation location, EntityType<?> entityType) {
        entityType.setRegistryName(location);
        ForgeRegistries.ENTITIES.register(entityType);
    }

    public static void register(SoundEvent soundEvent) {
        ForgeRegistries.SOUND_EVENTS.register(soundEvent);
    }

    @Nullable
    public static Block getBlock(ResourceLocation key) {
        return ForgeRegistries.BLOCKS.getValue(key);
    }

    @Nullable
    public static Item getItem(ResourceLocation key) {
        return ForgeRegistries.ITEMS.getValue(key);
    }

    @Nullable
    public static TileEntityType<?> getTile(ResourceLocation key) {
        return ForgeRegistries.TILE_ENTITIES.getValue(key);
    }
}
