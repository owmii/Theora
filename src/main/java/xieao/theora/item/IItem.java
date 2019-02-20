package xieao.theora.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.fml.network.NetworkHooks;
import xieao.theora.api.Consts;
import xieao.theora.client.renderer.item.TEItemRenderer;
import xieao.theora.core.IBlocks;
import xieao.theora.core.lib.util.PlayerUtil;
import xieao.theora.world.IInteractObj;

public interface IItem extends IForgeItem {
    ItemGroup MAIN = new ItemGroup(Consts.MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(IBlocks.CAULDRON);
        }
    };

    default boolean isCreativeItem(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().getBoolean("theora:IsCreative");
    }

    default void setCreativeItem(ItemStack stack, boolean creative) {
        stack.getOrCreateTag().setBoolean("theora:IsCreative", creative);
    }

    @OnlyIn(Dist.CLIENT)
    default boolean renderByItem(ItemStack stack) {
        return false;
    }

    class Base extends Item implements IItem {
        public Base(Properties properties) {
            super(properties.setTEISR(() -> TEItemRenderer::new));
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
            if (this instanceof IInteractObj) {
                IInteractObj io = (IInteractObj) this;
                if (!PlayerUtil.isFake(player) && player instanceof EntityPlayerMP) {
                    NetworkHooks.openGui((EntityPlayerMP) player, io, packetBuffer -> {
                        packetBuffer.writeString("item.gui");
                        packetBuffer.writeInt(hand.ordinal());
                    });
                }
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
            return super.onItemRightClick(world, player, hand);
        }
    }

    class Block extends ItemBlock implements IItem {
        public Block(net.minecraft.block.Block block, Properties properties) {
            super(block, properties.setTEISR(() -> TEItemRenderer::new));
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
            if (this instanceof IInteractObj) {
                IInteractObj io = (IInteractObj) this;
                if (!PlayerUtil.isFake(player) && player instanceof EntityPlayerMP) {
                    NetworkHooks.openGui((EntityPlayerMP) player, io, packetBuffer -> {
                        packetBuffer.writeString("item.gui");
                        packetBuffer.writeInt(hand.ordinal());
                    });
                }
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
            return super.onItemRightClick(world, player, hand);
        }
    }
}
