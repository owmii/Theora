package xieao.theora.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xieao.theora.common.lib.TheoraSounds;
import xieao.theora.common.lib.helper.NBTHelper;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
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
            worldIn.playSound(null, player.getPosition(), TheoraSounds.COINS_COLLECT, SoundCategory.AMBIENT, 1.0F, 1.0F);
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

    @SubscribeEvent
    public static void pickUpCoin(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.world.isRemote) {
            ItemStack stack = event.getItem().getItem();
            if (stack.getItem() == TheoraItems.PIG_COIN) {
                ItemStack bag = getPigCoinBag(player);
                if (!bag.isEmpty()) {
                    int coins = NBTHelper.getInteger(bag, ItemPigCoinBag.TAG_PIG_COINS);
                    NBTHelper.setInteger(bag, TAG_PIG_COINS, coins + stack.getCount());
                    event.getItem().getItem().shrink(stack.getCount());
                    event.setCanceled(true);

                    //TODO coin pickup sound
                }
            }
        }
    }

    private static ItemStack getPigCoinBag(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack.getItem() == TheoraItems.PIG_COIN_BAG) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
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
