package xieao.theora.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.OptionalCapabilityInstance;
import xieao.lib.item.IItem;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.TransferType;

import javax.annotation.Nullable;

public class ItemVial extends IItem.Base {
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
        TileEntity tileEntity = context.getWorld().getTileEntity(context.getPos());
        if (tileEntity != null) {
            OptionalCapabilityInstance<Liquid.Handler> holder = TheoraAPI.getLiquidHandler(tileEntity, context.getFace());
            holder.map(handler -> {
                for (Liquid.Slot slot : handler.getSlots()) {
                    if (!slotItem.isEmpty() && slot.canReceive(slotItem.getLiquid())) {
                        EntityPlayer player = context.getPlayer();
                        boolean flag = player != null && !player.isCreative();
                        slotItem.to(slot, true, flag);
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

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            Liquid.REGISTRY.values().forEach(liquid -> {
                ItemStack stack = new ItemStack(this);
                Liquid.Handler.Item handler = new Liquid.Handler.Item(stack);
                Liquid.Slot slot = handler.getSlot(0);
                slot.setLiquid(liquid);
                if (!liquid.isEmpty()) {
                    slot.setFull();
                }
                handler.setSlot(0, slot);
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
}
