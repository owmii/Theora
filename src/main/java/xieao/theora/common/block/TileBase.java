package xieao.theora.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class TileBase extends TileEntity {

    protected final Random rand = new Random();

    @Nullable
    private UUID placer;
    private boolean ghostTile;

    @SideOnly(Side.CLIENT)
    public int tickCount;

    @Nullable
    public UUID getPlacer() {
        return placer;
    }

    public void setPlacer(@Nullable UUID placer) {
        this.placer = placer;
    }

    public boolean isGhostTile() {
        return ghostTile;
    }

    public void setGhostTile(boolean ghostTile) {
        this.ghostTile = ghostTile;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound nbt = super.writeToNBT(compound);
        writeNBT(nbt);
        return nbt;
    }

    public void readNBT(NBTTagCompound nbt) {
        if (nbt.hasUniqueId("placerId")) {
            this.placer = nbt.getUniqueId("placerId");
        }
    }

    public void writeNBT(NBTTagCompound nbt) {
        if (this.placer != null) {
            nbt.setUniqueId("placerId", this.placer);
        }
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 5, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    public Vec3d getPosVec() {
        return new Vec3d(getPos());
    }

    public double getX() {
        return getPosVec().x;
    }

    public double getY() {
        return getPosVec().y;
    }

    public double getZ() {
        return getPosVec().z;
    }

    public boolean isServerWorld() {
        return hasWorld() && !getWorld().isRemote;
    }

    public void syncNBTData() {
        if (!isGhostTile() && hasWorld() && isServerWorld()) {
            IBlockState state = getWorld().getBlockState(getPos());
            getWorld().notifyBlockUpdate(getPos(), state, state, 3);
            markDirty();
        }
    }

    public boolean keepData() {
        return false;
    }
}
