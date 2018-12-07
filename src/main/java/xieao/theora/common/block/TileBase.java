package xieao.theora.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.common.lib.helper.math.Vec3;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class TileBase extends TileEntity {

    protected final Random rand = new Random();

    protected float facingAngle = EnumFacing.NORTH.getHorizontalAngle();

    @Nullable
    protected UUID placer;
    protected boolean ghostTile;

    @SideOnly(Side.CLIENT)
    public int tickCount;

    @SideOnly(Side.CLIENT)
    public boolean killParticles;

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

    public void killParticles() {
        this.killParticles = true;
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
        this.facingAngle = nbt.getFloat("facingAngle");
        if (nbt.hasUniqueId("placerId")) {
            this.placer = nbt.getUniqueId("placerId");
        }
    }

    public void writeNBT(NBTTagCompound nbt) {
        nbt.setFloat("facingAngle", this.facingAngle);
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

    public Vec3 getPosVec() {
        return new Vec3(getPos());
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

    @Nullable
    public TileEntity getTileEntity(BlockPos pos) {
        return getWorld().getTileEntity(pos);
    }

    public IBlockState getBlockState(BlockPos pos) {
        return getWorld().getBlockState(pos);
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

    public EnumFacing getFacing() {
        return EnumFacing.fromAngle(this.facingAngle);
    }

    public void setFacing(EnumFacing facing) {
        this.facingAngle = facing.getHorizontalAngle();
    }

    public float getFacingAngle() {
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
    }
}
