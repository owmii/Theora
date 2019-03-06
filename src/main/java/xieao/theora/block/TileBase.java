package xieao.theora.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.lib.util.InvUtil;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class TileBase extends TileEntity {
    public NonNullList<ItemStack> stacks = NonNullList.withSize(0, ItemStack.EMPTY);
    @Nullable
    public ITextComponent customName;

    public TileBase(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        readSync(compound);
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        NBTTagCompound nbt = super.write(compound);
        return writeSync(nbt);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return write(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 3, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readSync(pkt.getNbtCompound());
    }

    public void readSync(NBTTagCompound compound) {
        if (compound.contains("CustomName", 8)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
        if (this instanceof IInvBase) {
            this.stacks = NonNullList.withSize(this.stacks.size(), ItemStack.EMPTY);
            InvUtil.readAllItems(compound, (IInvBase) this);
        }
        readStorable(compound);
    }

    public NBTTagCompound writeSync(NBTTagCompound compound) {
        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        if (this instanceof IInvBase) {
            InvUtil.writeItems(compound, (IInventory) this);
        }
        writeStorable(compound);
        return compound;
    }

    public void readStorable(NBTTagCompound compound) {
    }

    public NBTTagCompound writeStorable(NBTTagCompound compound) {
        return compound;
    }

    public void markDirtyAndSync() {
        if (this.world != null) {
            markDirty();
            if (!isServerWorld()) {
                IBlockState state = getBlockState();
                this.world.notifyBlockUpdate(getPos(), state, state, 3);
            }
        }
    }

    public boolean isServerWorld() {
        return !this.world.isRemote;
    }

    public ITextComponent getName() {
        return new TextComponentTranslation("block." + Objects.requireNonNull(getType()
                .getRegistryName()).toString().replace(':', '.'));
    }

    public static abstract class Tickable extends TileBase implements ITickable {
        @OnlyIn(Dist.CLIENT)
        public int ticks;

        public Tickable(TileEntityType<?> type) {
            super(type);
        }

        @Override
        public void tick() {
            if (isServerWorld()) {
                this.ticks++;
            }
        }
    }
}
