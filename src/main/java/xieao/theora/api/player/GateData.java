package xieao.theora.api.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xieao.theora.api.liquid.LiquidHandler;

import javax.annotation.Nullable;

public class GateData {
    private final LiquidHandler liquidHandler = new LiquidHandler();
    public long lastCheck;
    public boolean loaded;
    public boolean playerGuiOpen;

    @Nullable
    private TileEntity tile;

    public NBTTagCompound write(NBTTagCompound compound) {
        compound.putLong("LastCheck", this.lastCheck);
        this.liquidHandler.write(compound);
        compound.putBoolean("Loaded", this.loaded);
        compound.putBoolean("PlayerGuiOpen", this.playerGuiOpen);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.lastCheck = compound.getLong("LastCheck");
        this.liquidHandler.read(compound);
        this.loaded = compound.getBoolean("Loaded");
        this.playerGuiOpen = compound.getBoolean("PlayerGuiOpen");
    }

    public NBTTagCompound serialize() {
        return write(new NBTTagCompound());
    }

    public void setLastCheck(long lastCheck) {
        this.lastCheck = lastCheck;
    }

    @Nullable
    public TileEntity getTileEntity(World world) {
        if (!loaded(world)) {
            setTileEntity(null);
        }
        return tile;
    }

    public void setTileEntity(@Nullable TileEntity tile) {
        this.tile = tile;
    }

    public LiquidHandler getLiquidHandler() {
        return liquidHandler;
    }

    public void setPlayerGuiOpen(boolean playerGuiOpen) {
        this.playerGuiOpen = playerGuiOpen;
    }

    public boolean loaded(World world) {
        this.loaded = world.getGameTime() - this.lastCheck == 0;
        return loaded;
    }
}
