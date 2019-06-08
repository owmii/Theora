package xieao.theora.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;

public class WorldData extends WorldSavedData {
    public WorldData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return compound;
    }

    @Nullable
    public static WorldData get(World world) {
//        WorldSavedDataStorage storage = world.ge();
//        if (storage == null)
//            return null;
//        WorldData data = storage.get(DimensionType.OVERWORLD, WorldData::new, "theora_data");
//        if (data == null) {
//            data = new WorldData("theora_data");
//            storage.set(DimensionType.OVERWORLD, "theora_data", data);
//        }
        return null;
    }
}
