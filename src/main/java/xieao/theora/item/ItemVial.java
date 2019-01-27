package xieao.theora.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.TransferType;
import xieao.theora.api.recipe.interact.IInteractRecipe;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.core.handler.RecipeSorter;
import xieao.theora.entity.EntityInteractor;

import javax.annotation.Nullable;

public class ItemVial extends IItem.Base implements IItemColorHolder {
    public ItemVial(Builder builder) {
        super(builder);
        addPropertyOverride(new ResourceLocation("fill"), (stack, world, entity) -> {
            Liquid.Handler.Item handler = new Liquid.Handler.Item(stack);
            Liquid.Slot slot = handler.getSlot(0);
            return slot.getStored() / slot.getCapacity();
        });
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, ItemUseContext context) {
        Liquid.Handler.Item handlerVial = new Liquid.Handler.Item(stack);
        Liquid.Slot slotVial = handlerVial.getSlot(0);
        EntityPlayer player = context.getPlayer();
        if (tryInteract(slotVial, context)) {
            handlerVial.setSlot(0, slotVial);
            return EnumActionResult.SUCCESS;
        }
        TileEntity tileEntity = context.getWorld().getTileEntity(context.getPos());
        if (tileEntity != null) {
            LazyOptional<Liquid.Handler> holder = TheoraAPI.getLiquidHandler(tileEntity, context.getFace());
            holder.map(handler -> {
                for (Liquid.Slot slot : handler.getSlots()) {
                    if (!slotVial.isEmpty() && slot.canReceive(slotVial.getLiquid())) {
                        slotVial.to(slot, true, player != null && !player.isCreative());
                        handlerVial.setSlot(0, slotVial);
                        return EnumActionResult.SUCCESS;
                    }
                    if (!slotVial.isFull() && slot.canSend(slotVial.getLiquid())) {
                        slot.to(slotVial, true, true);
                        handlerVial.setSlot(0, slotVial);
                        return EnumActionResult.SUCCESS;
                    }
                }
                return this;
            });
        }
        return super.onItemUseFirst(stack, context);
    }

    public static boolean tryInteract(Liquid.Slot slot, ItemUseContext context) {
        EntityPlayer player = context.getPlayer();
        IInteractRecipe recipe = RecipeSorter.getInteractRecipe(slot.getLiquid(), slot.getStored(), context.getWorld(), context.getPos());
        if (recipe != null) {
            Object result = recipe.getResult();
            boolean flag1 = false;
            if (result instanceof IBlockState) {
                flag1 = EntityInteractor.tryInteract(context.getWorld(), context.getPos(),
                        (IBlockState) result, (int) recipe.getAmount() * 5, recipe.getLiquid().getDarkColor(),
                        player != null ? player.getUniqueID() : null);
            } else if (result instanceof ItemStack) {
                flag1 = EntityInteractor.tryInteract(context.getWorld(), context.getPos(),
                        (ItemStack) result, (int) recipe.getAmount() * 5, recipe.getLiquid().getDarkColor(),
                        player != null ? player.getUniqueID() : null);
            }
            if (flag1) {
                slot.use(player != null && !player.isCreative() ? 0.0F : recipe.getAmount());
                return true;
            }
        }
        return false;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            Liquid.REGISTRY.values().forEach(liquid -> {
                ItemStack stack = new ItemStack(this);
                LazyOptional<Liquid.Handler.Item> holder = TheoraAPI.getLiquidHandlerItem(stack);
                holder.map(handler -> {
                    Liquid.Slot slot = handler.getSlot(0);
                    slot.setLiquid(liquid);
                    if (!liquid.isEmpty()) {
                        slot.setFull();
                    }
                    handler.setSlot(0, slot);
                    return this;
                });
                items.add(stack);
            });
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        Liquid.Handler.Item handler = new Liquid.Handler.Item(stack);
        handler.addSlot(200.0F, 200.0F, TransferType.ALL);
        return handler;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor() {
        return (stack, i) -> {
            Liquid.Handler.Item handler = new Liquid.Handler.Item(stack);
            Liquid liquid = handler.getSlot(0).getLiquid();
            return i == 1 || liquid.isEmpty() ? 0xFFFFFF : liquid.getDarkColor();
        };
    }
}
