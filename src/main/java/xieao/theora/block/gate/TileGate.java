package xieao.theora.block.gate;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.block.IInvBase;
import xieao.theora.block.TileBase;
import xieao.theora.core.ITiles;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileGate extends TileBase.Tickable implements IInvBase {
    private boolean gateBase;

    @Nullable
    protected Pair<UUID, String> owner;

    public TileGate() {
        super(ITiles.GATE);
        setInvSize(8);
    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        this.gateBase = compound.getBoolean("GateBase");
        if (compound.contains("OwnerId", Constants.NBT.TAG_STRING)) {
            String ownerId = compound.getString("OwnerId");
            String ownerName = compound.getString("OwnerName");
            setOwner(UUID.fromString(ownerId), ownerName);
        }
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        compound.putBoolean("GateBase", this.gateBase);
        if (this.owner != null) {
            compound.putString("OwnerId", this.owner.getLeft().toString());
            compound.putString("OwnerName", this.owner.getRight());
        }
        return super.writeSync(compound);
    }

    @Override
    public void tick() {
        if (this.gateBase) {
            super.tick();
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
    public Pair<UUID, String> getOwner() {
        return owner;
    }

    public void setOwner(UUID id, String name) {
        this.owner = Pair.of(id, name);
    }

    public boolean isGateBase() {
        return gateBase;
    }

    public void setGateBase(boolean gateBase) {
        this.gateBase = gateBase;
    }
}
