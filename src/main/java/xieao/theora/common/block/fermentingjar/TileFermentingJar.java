package xieao.theora.common.block.fermentingjar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.TileInvBase;

import javax.annotation.Nullable;

public class TileFermentingJar extends TileInvBase implements ITickable {

    private final LiquidContainer liquidContainer;

    private final int maxFermentingTime = 1200;
    private int fermenting;

    public TileFermentingJar() {
        this.liquidContainer = new LiquidContainer();
        this.liquidContainer.addLiquidSlot(new LiquidSlot(Liquid.EMPTY, false, 400.0F, 0.0F, 20.0F, LiquidSlot.TransferType.ALL));
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.liquidContainer.readNBT(nbt);
        this.fermenting = nbt.getInteger("fermenting");
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        this.liquidContainer.writeNBT(nbt);
        nbt.setInteger("fermenting", this.fermenting);
    }

    @Override
    public void update() {
        if (isServerWorld()) {

        }
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER
                || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER ? (T) this.liquidContainer
                : super.getCapability(capability, facing);
    }
}
