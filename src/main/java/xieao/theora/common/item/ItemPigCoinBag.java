package xieao.theora.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xieao.theora.common.helper.NBTHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPigCoinBag extends ItemBase {

    public static final String TAG_PIG_COINS = "pig.coins.stored";

    public ItemPigCoinBag() {
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack bag = player.getHeldItem(hand);
        NBTHelper.checkAndSetNbt(bag);
        boolean flag = false;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            int size = stack.getCount();
            if (stack.getItem() == TheoraItems.PIG_COIN) {
                int coins = NBTHelper.getInteger(bag, TAG_PIG_COINS);
                NBTHelper.setInteger(bag, TAG_PIG_COINS, coins + size);
                stack.shrink(size);
                flag = true;
            }
        }
        if (flag) {
            //TODO coins sound}
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Coins: " + NBTHelper.getInteger(stack, TAG_PIG_COINS));
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return TheoraItems.PIG_COIN.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
