package xieao.theora.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
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
import xieao.lib.client.renderer.item.IItemColorHolder;
import xieao.lib.item.IItemBase;
import xieao.theora.api.fill.Fill;
import xieao.theora.api.fill.FillHandler;
import xieao.theora.api.fill.Transfer;

import javax.annotation.Nullable;
import java.util.List;

public class VialItem extends Item implements IItemColorHolder, IItemBase {
    public VialItem(Properties properties) {
        super(properties);
        addPropertyOverride(new ResourceLocation("fill"), (stack, world, entity) -> {
            FillHandler.Item handler = new FillHandler.Item(stack);
            FillHandler.Slot slot = handler.getSlot("stored");
            return slot.getStored() / slot.getCapacity();
        });
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.SUCCESS;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            Fill.REGISTRY.values().forEach(fill -> {
                ItemStack stack = new ItemStack(this);
                FillHandler.Item handler = new FillHandler.Item(stack);
                FillHandler.Slot slot = handler.getSlot("stored");
                if (!fill.isEmpty()) {
                    slot.setFill(fill);
                    slot.setFull();
                    handler.setSlot("stored", slot);
                }
                items.add(stack);
            });
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        FillHandler.Item handler = new FillHandler.Item(stack);
        FillHandler.Slot slot = handler.getSlot("stored");
        tooltip.add(new StringTextComponent("" + slot.getFill().getDisplayName() + " " + slot.getStored()));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (stack.getTag() == null) {
            FillHandler.Item handler = new FillHandler.Item(stack);
            handler.addSlot("stored", Fill.EMPTY, 200.0F, 200.0F, Transfer.ALL);
            return handler;
        }
        return new FillHandler.Item(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor() {
        return (stack, i) -> {
            FillHandler.Item handler = new FillHandler.Item(stack);
            Fill fill = handler.getSlot("stored").getFill();
            return i == 1 || fill.isEmpty() ? 0xFFFFFF : fill.getColor0();
        };
    }
}
