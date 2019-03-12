package xieao.theora.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.storage.WorldSavedDataStorage;

import javax.annotation.Nullable;

public class WorldData extends WorldSavedData {
    public WorldData(String name) {
        super(name);
    }

    @Override
    public void read(NBTTagCompound nbt) {
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        return compound;
    }

    @Nullable
    public static WorldData get(World world) {
        WorldSavedDataStorage storage = world.getSavedDataStorage();
        if (storage == null)
            return null;
        WorldData data = storage.get(DimensionType.OVERWORLD, WorldData::new, "theora_data");
        if (data == null) {
            data = new WorldData("theora_data");
            storage.set(DimensionType.OVERWORLD, "theora_data", data);
        }
        return data;
    }
}
