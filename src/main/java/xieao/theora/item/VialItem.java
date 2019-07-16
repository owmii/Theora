package xieao.theora.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidHandler;
import xieao.theora.api.liquid.Transfer;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.core.ILiquids;

import javax.annotation.Nullable;
import java.util.List;

public class VialItem extends ItemBase implements IItemColorHolder {
    public VialItem(Properties properties) {
        super(properties);
        addPropertyOverride(new ResourceLocation("fill"), (stack, world, entity) -> {
            LiquidHandler.Item handler = new LiquidHandler.Item(stack);
            LiquidHandler.Slot slot = handler.getSlot("stored");
            return slot.getStored() / slot.getCapacity();
        });
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        LiquidHandler.Item handler = new LiquidHandler.Item(stack);
        LiquidHandler.Slot slot = handler.getSlot("stored");
//        stack.getOrCreateTag().putBoolean("fg",true);
//        stack.getOrCreateTag().putBoolean("fgfgf",true);
//        stack.getOrCreateTag().putBoolean("fgg",true);
//        stack.getOrCreateTag().putBoolean("ggg",true);
        //  if(worldIn.getGameTime()%20==0)
        // System.out.println("" + stack.getTag()+"" + slot.getLiquid().getDisplayName()+" "+slot.getStored());
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        LiquidHandler.Item handlerVial = new LiquidHandler.Item(stack);
        LiquidHandler.Slot slotVial = handlerVial.getSlot("stored");
        PlayerEntity player = context.getPlayer();
        slotVial.setLiquid(ILiquids.GLIOPHIN);
        slotVial.setStored(100.0F);
        handlerVial.setSlot("stored", slotVial);
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            Liquid.REGISTRY.values().forEach(liquid -> {
                ItemStack stack = new ItemStack(this);
                LiquidHandler.Item handler = new LiquidHandler.Item(stack);
                LiquidHandler.Slot slot = handler.getSlot("stored");
                if (!liquid.isEmpty()) {
                    slot.setLiquid(liquid);
                    slot.setFull();
                    handler.setSlot("stored", slot);
                }
                items.add(stack);
            });
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        LiquidHandler.Item handler = new LiquidHandler.Item(stack);
        LiquidHandler.Slot slot = handler.getSlot("stored");
        tooltip.add(new StringTextComponent("" + slot.getLiquid().getDisplayName() + " " + slot.getStored()));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (stack.getTag() == null) {
            LiquidHandler.Item handler = new LiquidHandler.Item(stack);
            handler.addSlot("stored", Liquid.EMPTY, 200.0F, 200.0F, Transfer.ALL);
            return handler;
        }
        return new LiquidHandler.Item(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor() {
        return (stack, i) -> {
            LiquidHandler.Item handler = new LiquidHandler.Item(stack);
            Liquid liquid = handler.getSlot("stored").getLiquid();
            return i == 1 || liquid.isEmpty() ? 0xFFFFFF : liquid.getColor0();
        };
    }
}
