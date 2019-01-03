package xieao.theora.common.block.cauldron;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.api.recipe.cauldron.ICauldronRecipe;
import xieao.theora.common.block.TileInvLiquidContainer;
import xieao.theora.common.item.recipe.RecipeHandler;

import javax.annotation.Nullable;

public class TileCauldron extends TileInvLiquidContainer implements ITickable {

    public final FluidTank fluidTank = new FluidTank(Fluid.BUCKET_VOLUME) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return FluidRegistry.WATER.equals(fluid.getFluid());
        }

        @Override
        public boolean canDrain() {
            return TileCauldron.this.boiling <= 0;
        }
    };

    public boolean heated;

    private final int waterMaxHeat = 100;
    public int waterHeating;

    public final int boilingTime = 240;
    public int boiling;
    public boolean started;

    public int[] nextLiquidColors = new int[2];
    public float blend;
    public float fill;

    public TileCauldron() {
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(Liquid.EMPTY, false, 1000.0F, 0.0F, 1000.0F, LiquidSlot.TransferType.SEND)
        );
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.fluidTank.readFromNBT(nbt);
        this.waterHeating = nbt.getInteger("waterHeating");
        this.boiling = nbt.getInteger("boiling");
        this.blend = (float) this.boiling;
        LiquidSlot liquidSlot = this.liquidContainer.getLiquidSlot(0);
        this.fill = liquidSlot.getStored();
        this.heated = nbt.getBoolean("heated");
        this.started = nbt.getBoolean("started");
        this.nextLiquidColors = nbt.getIntArray("nextLiquidColors");
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        this.fluidTank.writeToNBT(nbt);
        nbt.setInteger("waterHeating", this.waterHeating);
        nbt.setInteger("boiling", this.boiling);
        nbt.setBoolean("heated", this.heated);
        nbt.setBoolean("started", this.started);
        nbt.setIntArray("nextLiquidColors", this.nextLiquidColors);
    }

    @Override
    public void update() {
        if (isServerWorld()) {
            if (hasWater()) {
                if (this.heated) {
                    if (this.waterHeating < this.waterMaxHeat) {
                        this.waterHeating++;
                    } else {
                        LiquidSlot liquidSlot = getLiquidSlot(0);
                        if (liquidSlot.isEmpty()) {
                            ICauldronRecipe recipe = RecipeHandler.findCauldronRecipe(this, getWorld(), getPos());
                            if (recipe != null && this.started) {
                                Liquid liquid = recipe.getLiquid();
                                int dColor = liquid.getDarkColor();
                                int bColor = liquid.getBrightColor();
                                if (this.nextLiquidColors[0] != dColor && this.nextLiquidColors[1] != bColor) {
                                    this.nextLiquidColors[0] = dColor;
                                    this.nextLiquidColors[1] = bColor;
                                    markDirtyAndSync();
                                }
                                if (this.boiling++ >= this.boilingTime) {
                                    for (int i = 0; i < getSizeInventory(); i++) {
                                        ItemStack stack = getStackInSlot(i);
                                        stack.shrink(1);
                                    }
                                    liquidSlot.setLiquid(liquid);
                                    liquidSlot.setFull();
                                    this.fluidTank.drainInternal(Fluid.BUCKET_VOLUME, true);
                                    this.boiling = 0;
                                    this.blend = 0;
                                    this.started = false;
                                    markDirtyAndSync();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            LiquidSlot liquidSlot = this.liquidContainer.getLiquidSlot(0);
            float stored = liquidSlot.getStored();
            this.tickCount++;
            if (this.started && hasWater()) {
                this.blend++;
            }
            for (int i = 0; this.fill != stored && i < 30; i++) {
                if (this.fill < stored) {
                    this.fill++;
                    if (this.fill > stored) {
                        this.fill = stored;
                    }
                } else if (this.fill > stored) {
                    this.fill--;
                    if (this.fill < 0) {
                        this.fill = 0;
                    }
                }
            }
        }
    }

    @Override
    public boolean collectItems(EntityItem entityItem, int amount, int... excludedSlots) {
        return hasWater() && super.collectItems(entityItem, amount, excludedSlots);
    }

    @Override
    public boolean takeStack(EntityPlayer player, EnumFacing facing) {
        return !this.started && super.takeStack(player, facing);
    }

    public boolean hasWater() {
        return this.fluidTank.getFluidAmount() == Fluid.BUCKET_VOLUME;
    }

    @Override
    public int getSizeInventory() {
        return 12;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T) this.fluidTank
                : super.getCapability(capability, facing);
    }
}
