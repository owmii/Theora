package xieao.theora.block.cauldron;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.OptionalCapabilityInstance;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.TransferType;
import xieao.theora.api.recipe.cauldron.ICauldronRecipe;
import xieao.theora.block.IInv;
import xieao.theora.block.ITiles;
import xieao.theora.block.Tile;
import xieao.theora.block.heat.BlockHeat;
import xieao.theora.core.handler.RecipeSorter;
import xieao.theora.core.lib.util.Ticker;
import xieao.theora.item.ItemHeat;

import javax.annotation.Nullable;

public class TileCauldron extends Tile.Tickable implements IInv {
    private final OptionalCapabilityInstance<IFluidHandler> fluidHolder;
    private final OptionalCapabilityInstance<Liquid.Handler> liquidHolder;
    protected Liquid.Handler liquidHandler = new Liquid.Handler();
    protected FluidTank fluidTank = new FluidTank(Fluid.BUCKET_VOLUME);

    private ItemStack heatStack = ItemStack.EMPTY;
    private Ticker heat = new Ticker(100);

    @Nullable
    public ICauldronRecipe recipe;
    public Ticker boiling = new Ticker(240);
    public Ticker startDelay = new Ticker(80);
    public final int maxRecipeTime = 240;

    public int[] liquidColors = new int[2];
    public int blend;

    public TileCauldron() {
        super(ITiles.CAULDRON, 12);
        this.fluidHolder = OptionalCapabilityInstance.of(() -> this.fluidTank);
        this.liquidHolder = OptionalCapabilityInstance.of(() -> this.liquidHandler);
        this.liquidHandler.addSlot(1000.0F, 100.0F, TransferType.SEND);
    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        this.fluidTank.readFromNBT(compound);
        this.liquidHandler.read(compound);
        this.heatStack = ItemStack.read(compound.getCompound("HeatStack"));
        this.heat.read(compound, "Heat");
        this.boiling.read(compound, "Boiling");
        this.startDelay.read(compound, "StartDelay");
        this.blend = this.boiling.getTicks();
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        this.fluidTank.writeToNBT(compound);
        this.liquidHandler.write(compound);
        compound.setTag("HeatStack", this.heatStack.write(new NBTTagCompound()));
        this.heat.read(compound, "Heat");
        this.boiling.write(compound, "Boiling");
        this.startDelay.write(compound, "StartDelay");
        return super.writeSync(compound);
    }

    @Override
    public void tick() {
        if (this.recipe != null) {
            Liquid liquid = this.recipe.getLiquid();
            Liquid.Slot slot = this.liquidHandler.getSlot(0);
            if (!this.world.isRemote) {
                if (slot.isEmpty()) {
                    if (heated()) {
                        if (this.startDelay.done()) {
                            if (this.boiling.done()) {
                                slot.setLiquid(liquid);
                                slot.setFull();
                                clear();
                                this.startDelay.reset();
                                this.boiling.reset();
                                markDirtyAndSync();
                            }
                        }
                    }
                }
            } else {
                this.liquidColors[0] = liquid.getDarkColor();
                this.liquidColors[1] = liquid.getBrightColor();
            }
        } else {
            this.liquidColors = new int[2];
        }
    }

    private boolean heated() {
        if (this.heatStack.getItem() instanceof ItemHeat) {
            ItemHeat itemHeat = (ItemHeat) this.heatStack.getItem();
            int maxAge = ((BlockHeat) itemHeat.getBlock()).getMaxAge();
            if (maxAge != -1) {
                int age = itemHeat.getAge(this.heatStack);
                if (age >= maxAge) {
                    this.heatStack = ItemStack.EMPTY;
                    markDirtyAndSync();
                }
                itemHeat.setAge(this.heatStack, age + 1);
            }
            if (hasEnoughWater()) {
                return this.heat.done();
            } else {
                this.heat.reset();
            }
        } else {
            this.heat.back();
        }
        return false;
    }

    protected boolean hasEnoughWater() {
        return this.fluidTank.getFluidAmount() >= Fluid.BUCKET_VOLUME;
    }

    @Override
    public void onInventoryChanged(int index) {
        this.recipe = RecipeSorter.getCauldronRecipe(this);
        this.boiling.reset();
        this.startDelay.reset();
        markDirtyAndSync();
    }

    public ItemStack getHeatStack() {
        return heatStack;
    }

    public void setHeatStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemHeat) {
            this.heatStack = stack;
            markDirtyAndSync();
        }
    }

    @Override
    public <T> OptionalCapabilityInstance<T> getCapability(Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return this.fluidHolder.cast();
        } else if (cap == Liquid.Cap.LIQUID_HANDLER) {
            return this.liquidHolder.cast();
        }
        return super.getCapability(cap, side);
    }
}
