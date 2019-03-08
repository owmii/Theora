package xieao.theora.api.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import xieao.theora.api.liquid.LiquidHandler;
import xieao.theora.core.handler.ServerHandler;

import javax.annotation.Nullable;

public class GateData {
    private final LiquidHandler liquidHandler = new LiquidHandler();
    public long lastCheck;
    public boolean playerGuiOpen;

    @Nullable
    private TileEntity tile;

    public NBTTagCompound write(NBTTagCompound compound) {
        compound.putLong("LastCheck", this.lastCheck);
        this.liquidHandler.write(compound);
        compound.putBoolean("PlayerGuiOpen", this.playerGuiOpen);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.lastCheck = compound.getLong("LastCheck");
        this.liquidHandler.read(compound);
        this.playerGuiOpen = compound.getBoolean("PlayerGuiOpen");
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

    public LiquidHandler getLiquidHandler() {
        return liquidHandler;
    }

    public void setPlayerGuiOpen(boolean playerGuiOpen) {
        this.playerGuiOpen = playerGuiOpen;
    }

    public boolean loaded() {
        return ServerHandler.ticks - this.lastCheck == 0;
    }
}
