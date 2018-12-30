package xieao.theora.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.lib.item.ItemBase;
import xieao.theora.api.recipe.liquidinteract.ILiquidInteractRecipe;
import xieao.theora.common.entity.EntityInteractor;
import xieao.theora.common.lib.recipe.RecipeHandler;
import xieao.theora.common.liquid.TheoraLiquids;

public class ItemGliophin extends ItemBase {

    public ItemGliophin() {
        setMaxStackSize(16);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack held = player.getHeldItem(hand);
        ILiquidInteractRecipe recipe = RecipeHandler.findLiquidInteractRecipe(TheoraLiquids.GLIOPHIN, 40, world, pos);
        if (recipe != null) {
            if (EntityInteractor.tryInteract(world, pos, recipe.getInState(), recipe.exactState(), recipe.getOutState()
                    , (int) recipe.getLiquidAmount() * 20, TheoraLiquids.GLIOPHIN.getDarkColor(), player.getUniqueID())) {
                if (!player.isCreative()) {
                    held.shrink(1);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
}
