package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.ItemHandlerHelper;
import xieao.theora.Theora;
import xieao.theora.api.trade.pigzombie.PigZombieTrade;
import xieao.theora.common.item.ItemPigCoin;

import javax.annotation.Nullable;

public class PacketPigZombieTradBuy implements IPacket<PacketPigZombieTradBuy> {

    private ResourceLocation trade;

    public PacketPigZombieTradBuy(ResourceLocation trade) {
        this.trade = trade;
    }

    public PacketPigZombieTradBuy() {
        this(Theora.location("empty"));
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        this.trade = new ResourceLocation(buf.readString());
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeString(this.trade.toString());
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketPigZombieTradBuy message, MessageContext ctx, World world, EntityPlayer player) {
        ((WorldServer) world).addScheduledTask(() -> {
            PigZombieTrade trade = PigZombieTrade.getPigZombieTrade(message.trade.toString());
            int price = trade.price;
            int playerBalance = ItemPigCoin.getTotalPlayerCoins(player);
            if (price <= playerBalance) {
                ItemPigCoin.shrinkPlayerCoins(player, price);
                ItemHandlerHelper.giveItemToPlayer(player, trade.itemToSell.copy());
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }
}
