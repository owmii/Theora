package xieao.theora.api.player.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class PlayerDataCapability {

    @CapabilityInject(IPlayerData.class)
    public static Capability<IPlayerData> PLAYER_DATA = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerData.class, new Capability.IStorage<IPlayerData>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbt) {

            }
        }, PlayerData::new);
    }

}
