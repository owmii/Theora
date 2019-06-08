package xieao.theora.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;

public class WorldData extends WorldSavedData {
    public WorldData(String name) {
        super(name);
    }

    public WorldData() {
        super("Theora_Data");
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
//        WorldData data = Objects.requireNonNull(ServerUtil.getWorld(0)).func_217481_x().func_215753_b(WorldData::new, "Theora_Data");
//        MapData mapData = world.func_217406_a("");
//        if (mapData == null)
//            return null;
//        WorldData data = mapData.get(DimensionType.OVERWORLD, WorldData::new, "theora_data");
//        if (data == null) {
//            data = new WorldData("theora_data");
//            storage.set(DimensionType.OVERWORLD, "theora_data", data);
//        }
        return null;
    }
}
