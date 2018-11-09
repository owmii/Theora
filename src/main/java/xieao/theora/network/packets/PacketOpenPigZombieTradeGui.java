package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.theora.client.gui.trade.GuiPigZombieTrade;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PacketOpenPigZombieTradeGui implements IPacket<PacketOpenPigZombieTradeGui> {

    private UUID pigZombieId;
    private List<ResourceLocation> trades;

    public PacketOpenPigZombieTradeGui(UUID pigZombieId, List<ResourceLocation> trades) {
        this.pigZombieId = pigZombieId;
        this.trades = trades;
    }

    public PacketOpenPigZombieTradeGui() {
        this(UUID.randomUUID(), Collections.emptyList());
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        this.pigZombieId = UUID.fromString(buf.readString());
        int count = buf.readInt();
        this.trades = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            this.trades.add(new ResourceLocation(buf.readString()));
        }
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeString(this.pigZombieId.toString());
        buf.writeInt(this.trades.size());
        for (ResourceLocation trade : this.trades) {
            buf.writeString(trade.toString());
        }
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketOpenPigZombieTradeGui message, MessageContext ctx, World world, EntityPlayer player) {
        minecraft().addScheduledTask(() -> minecraft().displayGuiScreen(new GuiPigZombieTrade(message.pigZombieId, message.trades)));
        return null;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }
}
