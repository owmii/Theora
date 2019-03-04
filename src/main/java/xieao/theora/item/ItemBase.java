package xieao.theora.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import xieao.theora.lib.util.PlayerUtil;
import xieao.theora.world.IInteractObj;

public class ItemBase extends Item implements IItemBase {
    public ItemBase(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (this instanceof IInteractObj) {
            IInteractObj io = (IInteractObj) this;
            if (player instanceof EntityPlayerMP && !PlayerUtil.isFake(player)) {
                NetworkHooks.openGui((EntityPlayerMP) player, io, packetBuffer -> {
                    packetBuffer.writeString("item.gui");
                    packetBuffer.writeEnumValue(hand);
                });
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(world, player, hand);
    }
}
