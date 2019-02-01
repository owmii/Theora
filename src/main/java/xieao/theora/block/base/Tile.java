package xieao.theora.block.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import xieao.theora.core.lib.util.InvUtil;
import xieao.theora.core.lib.util.math.V3d;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public abstract class Tile extends TileEntity {
    public NonNullList<ItemStack> inv;
    protected final Random rnd = new Random();
    public EnumFacing facing = EnumFacing.NORTH;
    @Nullable
    public ITextComponent customName;
    @Nullable
    public UUID placerID;

    public Tile(TileEntityType<?> type) {
        this(type, 0);
    }

    public Tile(TileEntityType<?> type, int invSize) {
        super(type);
        this.inv = NonNullList.withSize(invSize, ItemStack.EMPTY);
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

    public void markDirtyAndSync() {
        if (this.world != null) {
            markDirty();
            if (!this.world.isRemote) {
                IBlockState state = getBlockState();
                this.world.notifyBlockUpdate(getPos(), state, state, 3);
            }
        }
    }

    public void readSync(NBTTagCompound compound) {
        if (compound.contains("CustomName", 8)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
        if (this instanceof IInv) {
            this.inv = NonNullList.withSize(this.inv.size(), ItemStack.EMPTY);
            InvUtil.readAllItems(compound, (IInv) this);
        }
        this.facing = EnumFacing.byIndex(compound.getInt("Facing"));
        if (compound.hasUniqueId("PlacerID")) {
            this.placerID = compound.getUniqueId("PlacerID");
        }
        readStorable(compound);
    }

    public NBTTagCompound writeSync(NBTTagCompound compound) {
        if (this.customName != null) {
            compound.setString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        if (this instanceof IInv) {
            InvUtil.writeItems(compound, (IInv) this);
        }
        compound.setInt("Facing", this.facing.getIndex());
        if (this.placerID != null) {
            compound.setUniqueId("PlacerID", this.placerID);
        }
        writeStorable(compound);
        return compound;
    }

    public void readStorable(NBTTagCompound compound) {
    }

    @Nullable
    public NBTTagCompound writeStorable(NBTTagCompound compound) {
        return null;
    }

    public int getSizeInventory() {
        return 0;
    }

    public void setCustomName(@Nullable ITextComponent name) {
        this.customName = name;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    @Nullable
    public UUID getPlacerID() {
        return placerID;
    }

    public void setPlacerID(@Nullable UUID placerID) {
        this.placerID = placerID;
    }

    public V3d getPosVec() {
        return new V3d(getPos());
    }

    @Nullable
    protected LazyOptional<IItemHandlerModifiable> invHandler;

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable EnumFacing side) {
        if (this instanceof IInv && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (this.invHandler == null) {
                this.invHandler = LazyOptional.of(() -> createHandler((IInv) this, side));
            }
            return this.invHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    protected IItemHandlerModifiable createHandler(IInv inv, @Nullable EnumFacing side) {
        return new InvWrapper(inv);
    }

    public boolean interact(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return interact(player, hand, side);
    }

    public boolean interact(EntityPlayer player, EnumHand hand, EnumFacing side) {
        return false;
    }

    public void onAdded(@Nullable EntityLivingBase placer, ItemStack stack) {
    }

    public boolean onCollision(Entity entity) {
        return false;
    }

    public static abstract class Tickable extends Tile implements ITickable {
        @OnlyIn(Dist.CLIENT)
        public int ticks;

        public Tickable(TileEntityType<?> type, int invSize) {
            super(type, invSize);
        }

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
