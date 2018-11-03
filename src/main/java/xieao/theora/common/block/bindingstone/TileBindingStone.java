package xieao.theora.common.block.bindingstone;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.common.block.TileBase;

import javax.annotation.Nullable;

public class TileBindingStone extends TileBase implements ITickable {

    private final LiquidContainer liquidContainer;
    private Ability ability = Ability.EMPTY;

    private final int maxTime = 640;
    private int binding;
    private boolean startBinding;

    public TileBindingStone() {
        this.liquidContainer = new LiquidContainer();
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(Liquid.EMPTY, true, 10000.0F, 0.0F, 250.0F, LiquidSlot.TransferType.RECEIVE)
        );
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.liquidContainer.readNBT(nbt);
        this.ability = Ability.readAbility(nbt);
        this.binding = nbt.getInteger("binding");
        this.startBinding = nbt.getBoolean("startBinding");
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        this.liquidContainer.writeNBT(nbt);
        Ability.writeAbility(this.ability, nbt);
        nbt.setInteger("binding", this.binding);
        nbt.setBoolean("startBinding", this.startBinding);
    }

    @Override
    public void update() {
        if (isServerWorld()) {

        }
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
