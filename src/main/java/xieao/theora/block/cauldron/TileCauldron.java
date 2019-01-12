package xieao.theora.block.cauldron;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.OptionalCapabilityInstance;
import xieao.lib.block.IInv;
import xieao.lib.block.Tile;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.block.ITiles;

import javax.annotation.Nullable;

public class TileCauldron extends Tile.Tickable implements IInv {
    private final OptionalCapabilityInstance<Liquid.Handler> holder;
    protected Liquid.Handler liquidHandler = new Liquid.Handler();

    public TileCauldron() {
        super(ITiles.CAULDRON);
        this.holder = OptionalCapabilityInstance.of(() -> this.liquidHandler);
    }

    @Override
    public void tick() {

    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        this.liquidHandler.read(compound);
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        this.liquidHandler.write(compound);
        return super.writeSync(compound);
    }

    @Override
    public <T> OptionalCapabilityInstance<T> getCapability(Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == Liquid.Cap.LIQUID_HANDLER) {
            return this.holder.cast();
        }
        return super.getCapability(cap, side);
    }
}
