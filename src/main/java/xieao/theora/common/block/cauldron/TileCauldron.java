package xieao.theora.common.block.cauldron;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.TileInvBase;

import javax.annotation.Nullable;

public class TileCauldron extends TileInvBase implements ITickable {

    private final LiquidContainer liquidContainer;

    private final FluidTank fluidTank = new FluidTank(Fluid.BUCKET_VOLUME) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return FluidRegistry.WATER.equals(fluid.getFluid());
        }

        @Override
        public boolean canDrain() {
            return TileCauldron.this.stacks.size() <= 0;
        }
    };

    private final int waterMaxHeat = 100;
    private int waterHeating;

    private final int boilingTime = 480;
    private int boiling;
    private boolean started;

    public int[] nextLiquidColors = new int[2];
    public float blend;

    public TileCauldron() {
        this.liquidContainer = new LiquidContainer();
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(Liquid.EMPTY, false, 1000.0F, 0.0F, 1000.0F, LiquidSlot.TransferType.SEND)
        );
    }

    @Override
    public void update() {

    }

    @Override
    public int getSizeInventory() {
        return 12;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER
                || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER ? (T) this.liquidContainer
                : capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T) this.fluidTank
                : super.getCapability(capability, facing);
    }
}
