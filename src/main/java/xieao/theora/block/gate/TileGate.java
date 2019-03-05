package xieao.theora.block.gate;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.GateData;
import xieao.theora.block.IInvBase;
import xieao.theora.block.TileBase;
import xieao.theora.core.ITiles;
import xieao.theora.lib.util.PlayerUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileGate extends TileBase.Tickable implements IInvBase {
    private boolean gateBase;

    @Nullable
    protected GameProfile owner;

    public TileGate() {
        super(ITiles.GATE);
        setInvSize(5);
    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        this.gateBase = compound.getBoolean("GateBase");
        if (compound.contains("OwnerId", Constants.NBT.TAG_STRING)) {
            String ownerId = compound.getString("OwnerId");
            String ownerName = compound.getString("OwnerName");
            setOwner(new GameProfile(UUID.fromString(ownerId), ownerName));
        }
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        compound.putBoolean("GateBase", this.gateBase);
        if (this.owner != null) {
            compound.putString("OwnerId", this.owner.getId().toString());
            compound.putString("OwnerName", this.owner.getName());
        }
        return super.writeSync(compound);
    }

    @Override
    public void tick() {
        if (this.gateBase) {
            super.tick();
            if (!this.world.isRemote) {
                if (this.owner != null) {
                    EntityPlayer player = PlayerUtil.get(this.world, this.owner.getId());
                    if (player != null) {
                        TheoraAPI.getPlayerData(player).ifPresent(playerData -> {
                            GateData gateData = playerData.gate;
                            gateData.setLastCheck(System.currentTimeMillis());
                        });
                    }
                }
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return isGateBase();
    }

    @Override
    public int getSizeInventory() {
        return isGateBase() ? this.stacks.size() : 0;
    }

    @Nullable
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
