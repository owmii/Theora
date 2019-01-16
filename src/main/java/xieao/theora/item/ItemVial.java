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
import net.minecraftforge.common.capabilities.OptionalCapabilityInstance;
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
        Liquid.Handler.Item handlerItem = new Liquid.Handler.Item(stack);
        Liquid.Slot slotItem = handlerItem.getSlot(0);
        EntityPlayer player = context.getPlayer();
        if (tryInteract(handlerItem, context)) {
            return EnumActionResult.SUCCESS;
        }
        TileEntity tileEntity = context.getWorld().getTileEntity(context.getPos());
        if (tileEntity != null) {
            OptionalCapabilityInstance<Liquid.Handler> holder = TheoraAPI.getLiquidHandler(tileEntity, context.getFace());
            holder.map(handler -> {
                for (Liquid.Slot slot : handler.getSlots()) {
                    if (!slotItem.isEmpty() && slot.canReceive(slotItem.getLiquid())) {
                        slotItem.to(slot, true, player != null && !player.isCreative());
                        handlerItem.setSlot(0, slotItem);
                        return EnumActionResult.SUCCESS;
                    }
                    if (!slotItem.isFull() && slot.canSend(slotItem.getLiquid())) {
                        slot.to(slotItem, true, true);
                        handlerItem.setSlot(0, slotItem);
                        return EnumActionResult.SUCCESS;
                    }
                }
                return this;
            });
        }
        return super.onItemUseFirst(stack, context);
    }

    public static boolean tryInteract(Liquid.Handler.Item handlerItem, ItemUseContext context) {
        EntityPlayer player = context.getPlayer();
        Liquid.Slot slotItem = handlerItem.getSlot(0);
        IInteractRecipe recipe = RecipeSorter.getInteractRecipe(slotItem.getLiquid(), slotItem.getStored(), context.getWorld(), context.getPos());
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
                slotItem.use(player != null && !player.isCreative() ? 0.0F : recipe.getAmount());
                handlerItem.setSlot(0, slotItem);
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
                OptionalCapabilityInstance<Liquid.Handler.Item> holder = TheoraAPI.getLiquidHandlerItem(stack);
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
