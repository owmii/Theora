package xieao.theora.block.gate;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.LiquidHandler;
import xieao.theora.api.liquid.Transfer;
import xieao.theora.api.player.GateData;
import xieao.theora.block.IInvBase;
import xieao.theora.block.TileBase;
import xieao.theora.core.ILiquids;
import xieao.theora.core.ITiles;
import xieao.theora.lib.util.PlayerUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileGate extends TileBase.Tickable implements IInvBase {
    private final LiquidHandler liquidHandler = new LiquidHandler();
    private final LazyOptional<LiquidHandler> holder = LazyOptional.of(() -> this.liquidHandler);
    private GameProfile owner = new GameProfile(new UUID(0L, 0L), "null");
    private boolean gateBase;

    @Nullable
    private EntityPlayer player;

    public TileGate() {
        super(ITiles.GATE);
        setInvSize(5);
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
        if (isServerWorld()) {
            boolean sync = this.world.getGameTime() % 100L == 0;
            if (this.player == null) {
                this.player = PlayerUtil.get(this.world, this.owner.getId());
            }
            if (this.player != null) {
                TheoraAPI.getPlayerData(this.player).ifPresent(playerData -> {
                    GateData gateData = playerData.gate;
                    gateData.setLastCheck(System.currentTimeMillis());
                    gateData.setTile(this);
                });
            }
            if (sync) {
                markDirtyAndSync();
            }
        }
        LiquidHandler.Slot slot = this.liquidHandler.getSlot("slot.essence");
        slot.add(0.0008F);
        super.tick();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return isGateBase();
    }

    @Override
    public int getSizeInventory() {
        return isGateBase() ? this.stacks.size() : 0;
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
