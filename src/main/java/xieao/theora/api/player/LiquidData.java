package xieao.theora.api.player;

import net.minecraft.nbt.NBTTagCompound;
import xieao.theora.api.liquid.LiquidHandler;

public class LiquidData {
    private final LiquidHandler handler = new LiquidHandler();

    public NBTTagCompound write(NBTTagCompound compound) {
        this.handler.write(compound);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.handler.read(compound);
    }

    public NBTTagCompound serialize() {
        return write(new NBTTagCompound());
    }
}
