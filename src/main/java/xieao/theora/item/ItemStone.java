package xieao.theora.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.block.hor.TileHor;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.core.lib.util.PlayerUtil;

public class ItemStone extends ItemBase implements IItemColorHolder {
    public ItemStone(Properties properties) {
        super(properties);
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote) {
            EntityPlayer player = context.getPlayer();
            if (player != null && !PlayerUtil.isFake(player)) {
                TileHor hor = PlayerUtil.getHor(player);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor() {
        return (stack, i) -> 0xffffff;
    }
}
