package xieao.theora.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.data.PlayerData;

public class ItemAcidVial extends ItemBase {

    public ItemAcidVial() {
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!(player instanceof FakePlayer)) {
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
//                if (!data.hasAcidVial()) {
//                    data.setHasAcidVial(true);
//                    data.setVialChanged(true);
//                    player.attackEntityFrom(DamageSource.GENERIC, 2.0F);
//                    player.setHeldItem(hand, ItemStack.EMPTY);
//                    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
//                } else {
//                    data.setHasAcidVial(false);
//                    data.setVialChanged(true);
//                    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
//                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }
}
