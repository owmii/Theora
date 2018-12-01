package xieao.theora.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xieao.theora.client.gui.book.GuiBook;

public class ItemBook extends ItemBase {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) GuiBook.instance.open();
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
