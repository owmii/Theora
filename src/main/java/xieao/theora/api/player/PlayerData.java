package xieao.theora.api.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import xieao.theora.api.fill.Fill;
import xieao.theora.api.fill.FillHandler;
import xieao.theora.api.fill.Transfer;

public final class PlayerData {
    public static final Fill STAMINA = Fill.register("stamina", 0, 0);
    private final FillHandler fillHandler = new FillHandler();

    public PlayerData() {
        this.fillHandler.addSlot("stamina", STAMINA, 1000.0F, 1000.0F, Transfer.ALL);
    }

    public CompoundNBT write(CompoundNBT compound) {
        this.fillHandler.write(compound);
        return compound;
    }

    public void read(CompoundNBT compound) {
        this.fillHandler.read(compound);
    }

    public CompoundNBT serialize() {
        return write(new CompoundNBT());
    }

    public FillHandler.Slot getStamina() {
        return this.fillHandler.getSlot("stamina");
    }

    public static class Cap {
        @CapabilityInject(PlayerData.class)
        @SuppressWarnings("ConstantConditions")
        public static Capability<PlayerData> DATA = null;
    }
}
