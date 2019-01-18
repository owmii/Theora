package xieao.theora.core.lib.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class RegistryUtil {
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
