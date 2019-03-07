package xieao.theora.api.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import xieao.theora.api.liquid.LiquidHandler;

import javax.annotation.Nullable;

public class GateData {
    public long lastCheck;
    @Nullable
    private TileEntity tile;
    private LiquidHandler liquidHandler = new LiquidHandler();

    public NBTTagCompound write(NBTTagCompound compound) {
        compound.putLong("LastCheck", this.lastCheck);
        this.liquidHandler.write(compound);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.lastCheck = compound.getLong("LastCheck");
        this.liquidHandler.read(compound);
    }

    public NBTTagCompound serialize() {
        return write(new NBTTagCompound());
    }

    public void setLastCheck(long lastCheck) {
        this.lastCheck = lastCheck;
    }

    @Nullable
    public TileEntity getTile() {
        if (!loaded()) {
            setTile(null);
        }
        return tile;
    }

    public void setTile(@Nullable TileEntity tile) {
        this.tile = tile;
    }

    public boolean loaded() {
        long current = System.currentTimeMillis();
        long last = this.lastCheck;
        return current - last == 0;
    }
}
