package xieao.theora.network.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.theora.Theora;
import xieao.theora.api.trade.pigzombie.PigZombieTrade;

import javax.annotation.Nullable;
import java.util.UUID;

public class PacketPigZombieTradBuy implements IPacket<PacketPigZombieTradBuy> {

    private UUID pigZombieId;
    private ResourceLocation trade;

    public PacketPigZombieTradBuy(UUID pigZombieId, ResourceLocation trade) {
        this.pigZombieId = pigZombieId;
        this.trade = trade;
    }

    public PacketPigZombieTradBuy() {
        this(UUID.randomUUID(), Theora.location("empty"));
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        this.pigZombieId = UUID.fromString(buf.readString());
        this.trade = new ResourceLocation(buf.readString());
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeString(this.pigZombieId.toString());
        buf.writeString(this.trade.toString());
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketPigZombieTradBuy message, MessageContext ctx, World world, EntityPlayer player) {
        ((WorldServer) world).addScheduledTask(() -> {
            Entity entity = ((WorldServer) world).getEntityFromUuid(message.pigZombieId);
            if (entity instanceof EntityPigZombie) {
                EntityPigZombie pigZombie = (EntityPigZombie) entity;
                PigZombieTrade trade = PigZombieTrade.getPigZombieTrade(message.trade.toString());
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }
}
