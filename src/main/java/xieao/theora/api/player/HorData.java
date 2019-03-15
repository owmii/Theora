package xieao.theora.api.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.Constants;
import xieao.theora.api.liquid.LiquidHandler;

import javax.annotation.Nullable;

public class HorData {
    private final LiquidHandler liquidHandler = new LiquidHandler();
    @Nullable
    private BlockPos pos;
    @Nullable
    private DimensionType dimensionType;
    private boolean loaded;
    public boolean guiOpen;

    public NBTTagCompound write(NBTTagCompound compound) {
        this.liquidHandler.write(compound);
        if (this.pos != null) {
            compound.put("Pos", NBTUtil.writeBlockPos(getPos()));
        }
        if (this.dimensionType != null) {
            compound.putInt("DimensionType", this.dimensionType.getId());
        }
        compound.putBoolean("Loaded", this.loaded);
        compound.putBoolean("GuiOpen", this.guiOpen);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.liquidHandler.read(compound);
        if (compound.contains("Pos", Constants.NBT.TAG_COMPOUND)) {
            setPos(NBTUtil.readBlockPos(compound.getCompound("Pos")));
        }
        if (compound.contains("DimensionType", Constants.NBT.TAG_INT)) {
            setDimensionType(DimensionType.getById(compound.getInt("DimensionType")));
        }
        this.loaded = compound.getBoolean("Loaded");
        this.guiOpen = compound.getBoolean("GuiOpen");
    }

    public NBTTagCompound serialize() {
        return write(new NBTTagCompound());
    }

    public LiquidHandler getLiquidHandler() {
        return liquidHandler;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isGuiOpen() {
        return guiOpen;
    }

    public void setGuiOpen(boolean guiOpen) {
        this.guiOpen = guiOpen;
    }
}
