package xieao.theora.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class WorldData extends WorldSavedData {
    public static final String NAME = "theora_data";

    public WorldData() {
        super(NAME);
    }

    @Override
    public void read(CompoundNBT nbt) {
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return compound;
    }

    public static WorldData get(World world) {
        DimensionSavedDataManager dataManager = ((ServerWorld) world).getSavedData();
        WorldData data = dataManager.get(WorldData::new, NAME);
        if (data == null) {
            data = new WorldData();
            data.markDirty();
            dataManager.set(data);
        }
        return data;
    }
}
