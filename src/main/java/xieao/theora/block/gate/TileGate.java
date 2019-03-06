package xieao.theora.block.gate;

import com.mojang.authlib.GameProfile;
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

import java.util.UUID;

public class TileGate extends TileBase.Tickable implements IInvBase {
    private final LiquidHandler liquidHandler = new LiquidHandler();
    private final LazyOptional<LiquidHandler> holder = LazyOptional.of(() -> this.liquidHandler);
    private GameProfile owner = new GameProfile(new UUID(0L, 0L), "null");
    private boolean gateBase;

    public TileGate() {
        super(ITiles.GATE);
        setInvSize(5);
        this.liquidHandler.addSlot("slot.main", ILiquids.LAVA, 1000.0F, 1000.0F, Transfer.ALL);
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
        boolean sync = this.world.getGameTime() % 100L == 0;
        if (isServerWorld()) {
            PlayerUtil.get(this.world, this.owner.getId()).ifPresent(player -> {
                TheoraAPI.getPlayerData(player).ifPresent(playerData -> {
                    GateData gateData = playerData.gate;
                    gateData.setLastCheck(System.currentTimeMillis());
                });
            });
            LiquidHandler.Slot slot = this.liquidHandler.getSlot("slot.main");
            slot.add(0.0008F);
        }
        if (sync) {
            markDirtyAndSync();
        }
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
}
