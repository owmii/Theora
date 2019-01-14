package xieao.theora.block.cauldron;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.OptionalCapabilityInstance;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xieao.lib.block.IInv;
import xieao.lib.block.Tile;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.TransferType;
import xieao.theora.block.ITiles;

import javax.annotation.Nullable;

public class TileCauldron extends Tile.Tickable implements IInv {
    private final OptionalCapabilityInstance<IFluidHandler> fluidHolder;
    private final OptionalCapabilityInstance<Liquid.Handler> liquidHolder;
    protected Liquid.Handler liquidHandler = new Liquid.Handler();
    protected FluidTank fluidTank = new FluidTank(Fluid.BUCKET_VOLUME);


    public TileCauldron() {
        super(ITiles.CAULDRON, 12);
        this.fluidHolder = OptionalCapabilityInstance.of(() -> this.fluidTank);
        this.liquidHolder = OptionalCapabilityInstance.of(() -> this.liquidHandler);
        this.liquidHandler.addSlot(1000.0F, 100.0F, TransferType.SEND);
    }

    @Override
    public void tick() {

    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        this.fluidTank.readFromNBT(compound);
        this.liquidHandler.read(compound);
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        this.fluidTank.writeToNBT(compound);
        this.liquidHandler.write(compound);
        return super.writeSync(compound);
    }

    @Override
    public <T> OptionalCapabilityInstance<T> getCapability(Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHolder.cast();
        } else if (cap == Liquid.Cap.LIQUID_HANDLER) {
            return this.liquidHolder.cast();
        }
        return super.getCapability(cap, side);
    }
}
