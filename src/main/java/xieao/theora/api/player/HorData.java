package xieao.theora.api.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import xieao.theora.api.liquid.LiquidHandler;

import javax.annotation.Nullable;

public class HorData {
    private final LiquidHandler liquidHandler = new LiquidHandler();
    public static long current;
    private long lastCheck;
    private boolean loaded;
    public boolean playerGuiOpen;

    @Nullable
    private TileEntity tile;

    public NBTTagCompound write(NBTTagCompound compound) {
        this.liquidHandler.write(compound);
        compound.putBoolean("Loaded", this.loaded);
        compound.putBoolean("PlayerGuiOpen", this.playerGuiOpen);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.liquidHandler.read(compound);
        this.loaded = compound.getBoolean("Loaded");
        this.playerGuiOpen = compound.getBoolean("PlayerGuiOpen");
    }

    public NBTTagCompound serialize() {
        return write(new NBTTagCompound());
    }

    @Nullable
    public TileEntity getTileEntity(boolean isClient) {
        if (!loaded(isClient)) {
            setTileEntity(null);
            this.liquidHandler.read(new NBTTagCompound());
        }
        return tile;
    }

    public void setTileEntity(@Nullable TileEntity tile) {
        this.tile = tile;
        if (tile != null) {
            this.lastCheck = current;
        }
    }

    public LiquidHandler getLiquidHandler() {
        return liquidHandler;
    }

    public void setPlayerGuiOpen(boolean playerGuiOpen) {
        this.playerGuiOpen = playerGuiOpen;
    }

    public boolean loaded(boolean isClient) {
        if (!isClient) {
            this.loaded = current - this.lastCheck == 0;
        }
        return loaded;
    }
}
