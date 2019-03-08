package xieao.theora.block.gate;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.LiquidHandler;
import xieao.theora.api.liquid.Transfer;
import xieao.theora.api.player.GateData;
import xieao.theora.block.TileBase;
import xieao.theora.core.ILiquids;
import xieao.theora.core.ITiles;
import xieao.theora.core.handler.ServerHandler;
import xieao.theora.lib.util.PlayerUtil;
import xieao.theora.network.packet.playerdata.SyncGateData;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileGate extends TileBase.Tickable {
    private final LiquidHandler liquidHandler = new LiquidHandler();
    private final LazyOptional<LiquidHandler> holder = LazyOptional.of(() -> this.liquidHandler);
    private GameProfile owner = new GameProfile(new UUID(0L, 0L), "null");
    private boolean gateBase;

    @Nullable
    private EntityPlayer player;

    public TileGate() {
        super(ITiles.GATE);
        this.liquidHandler.addSlot("slot.essence", ILiquids.ESSENCE, 1000.0F, 1000.0F, Transfer.ALL);
    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        this.liquidHandler.read(compound);
        this.gateBase = compound.getBoolean("GateBase");
        if (compound.contains("OwnerId", Constants.NBT.TAG_STRING)) {
            String ownerId = compound.getString("OwnerId");
            String ownerName = compound.getString("OwnerName");
            setOwner(new GameProfile(UUID.fromString(ownerId), ownerName));
        }
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        this.liquidHandler.write(compound);
        compound.putBoolean("GateBase", this.gateBase);
        if (!"null".equals(this.owner.getName())) {
            compound.putString("OwnerId", this.owner.getId().toString());
            compound.putString("OwnerName", this.owner.getName());
        }
        return super.writeSync(compound);
    }

    @Override
    public void tick() {
        if (!this.gateBase) return;
        LiquidHandler.Slot slot = this.liquidHandler.getSlot("slot.essence");
        slot.add(0.0008F);
        if (isServerWorld()) {
            setupOwner();
        }
        super.tick();
    }

    private void setupOwner() {
        if (this.player == null) {
            this.player = PlayerUtil.get(this.world, this.owner.getId());
        }
        if (this.player instanceof EntityPlayerMP) {
            TheoraAPI.getPlayerData(this.player).ifPresent(playerData -> {
                GateData gateData = playerData.gate;
                gateData.setLastCheck(ServerHandler.ticks);
                gateData.setTile(this);
                syncPlayer(gateData);
            });
        }
    }

    public void syncPlayer(GateData gateData) {
        LiquidHandler handler = gateData.getLiquidHandler();
        handler.read(this.liquidHandler.serialize());
        boolean guiOpen = gateData.playerGuiOpen;
        if (guiOpen && this.world.getGameTime() % 10 == 0) {
            Theora.NET.toClient(new SyncGateData(gateData.serialize()), (EntityPlayerMP) this.player);
        }
    }

    public LiquidHandler getLiquidHandler() {
        return liquidHandler;
    }

    public GameProfile getOwner() {
        return owner;
    }

    public void setOwner(GameProfile owner) {
        this.owner = owner;
    }

    public boolean isGateBase() {
        return gateBase;
    }

    public void setGateBase(boolean gateBase) {
        this.gateBase = gateBase;
    }

    @Nullable
    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(@Nullable EntityPlayer player) {
        this.player = player;
    }
}
