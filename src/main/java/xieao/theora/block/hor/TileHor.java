package xieao.theora.block.hor;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.LiquidHandler;
import xieao.theora.api.player.HorData;
import xieao.theora.block.IInvBase;
import xieao.theora.block.TileBase;
import xieao.theora.client.gui.inventory.GuiHor;
import xieao.theora.core.ILiquids;
import xieao.theora.core.ITiles;
import xieao.theora.inventory.ContainerGate;
import xieao.theora.lib.util.PlayerUtil;
import xieao.theora.network.packet.playerdata.SyncHorData;
import xieao.theora.world.IInteractObj;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileHor extends TileBase.Tickable implements IInvBase, IInteractObj {
    private final LiquidHandler handler = new LiquidHandler();
    private GameProfile owner = new GameProfile(new UUID(0L, 0L), "null");

    @Nullable
    private EntityPlayer player;
    public int openTab;

    public TileHor() {
        super(ITiles.HOR);
        this.handler.add("slot.essence", ILiquids.ESSENCE, 1000.0F, 100.0F);
        setInvSize(4);
    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        this.handler.read(compound);
        if (compound.contains("OwnerId", Constants.NBT.TAG_STRING)) {
            String ownerId = compound.getString("OwnerId");
            String ownerName = compound.getString("OwnerName");
            setOwner(new GameProfile(UUID.fromString(ownerId), ownerName));
        }
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        this.handler.write(compound);
        if (!"null".equals(this.owner.getName())) {
            compound.putString("OwnerId", this.owner.getId().toString());
            compound.putString("OwnerName", this.owner.getName());
        }
        return super.writeSync(compound);
    }

    @Override
    public void tick() {
        if (isServerWorld()) {
            if (this.player == null) {
                this.player = PlayerUtil.get(getOwnerID());
            }
            if (this.player != null) {
                TheoraAPI.getPlayerData(this.player).ifPresent(playerData -> {
                    HorData horData = playerData.hor;
                    horData.setLastCheck(this.world.getGameTime());
                    horData.setTileEntity(this);
                    syncPlayer(horData);
                });
            }
        }
        super.tick();
    }

    public void syncPlayer(HorData horData) {
        LiquidHandler handler = horData.getLiquidHandler();
        handler.read(this.handler.serialize());
        boolean guiOpen = horData.playerGuiOpen;
        if (guiOpen && this.world.getGameTime() % 10 == 0) {
            Theora.NET.toClient(new SyncHorData(horData.serialize()), this.player);
        }
    }

    public LiquidHandler getHandler() {
        return handler;
    }

    public GameProfile getOwner() {
        return owner;
    }

    public void setOwner(GameProfile owner) {
        this.owner = owner;
    }

    public UUID getOwnerID() {
        return getOwner().getId();
    }

    public String getOwnerName() {
        return getOwner().getName();
    }

    @Nullable
    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(@Nullable EntityPlayer player) {
        this.player = player;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.pos.add(-5, -5, -5),
                this.pos.add(5, 5, 5));
    }

    @Override
    public Container getContainer(EntityPlayer player) {
        return new ContainerGate(player, this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public GuiScreen getGui(EntityPlayer player, EnumHand hand) {
        return new GuiHor(player, this);
    }
}
