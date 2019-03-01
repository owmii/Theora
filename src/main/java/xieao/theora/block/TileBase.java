package xieao.theora.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public abstract class TileBase extends TileEntity {
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
        readStorable(compound);
    }

    public NBTTagCompound writeSync(NBTTagCompound compound) {
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
            if (!this.world.isRemote) {
                IBlockState state = getBlockState();
                this.world.notifyBlockUpdate(getPos(), state, state, 3);
            }
        }
    }

    public static abstract class Tickable extends TileBase implements ITickable {
        @OnlyIn(Dist.CLIENT)
        public int ticks;

        public Tickable(TileEntityType<?> type) {
            super(type);
        }

        @Override
        public void tick() {
            if (this.world.isRemote) {
                this.ticks++;
            }
        }
    }
}
