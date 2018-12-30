package xieao.theora.common.block.orb;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import xieao.lib.block.TileBase;
import xieao.theora.api.liquid.IliquidContainer;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.api.liquid.LiquidSlot.TransferType;
import xieao.theora.api.liquid.LiquidUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static xieao.theora.api.liquid.LiquidSlot.TransferType.*;

public class TileOrb extends TileBase implements ITickable {

    public final Set<BlockPos> linkedPositions = new HashSet<>();

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        NBTTagList tagList = nbt.getTagList("linkedPositions", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            BlockPos pos = NBTUtil.getPosFromTag(compound.getCompoundTag("linkedPos"));
            this.linkedPositions.add(pos);
        }
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        NBTTagList tagList = new NBTTagList();
        for (BlockPos pos : this.linkedPositions) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("linkedPos", NBTUtil.createPosTag(pos));
            tagList.appendTag(compound);
        }
        nbt.setTag("linkedPositions", tagList);
    }

    @Override
    public void update() {
        Set<BlockPos> toRemove = new HashSet<>();
        for (BlockPos pos : this.linkedPositions) {
            if (getWorld().isBlockLoaded(pos)) {
                IliquidContainer container = getLiquidContainer(pos);
                if (container != null) {
                    LiquidSlot[] slots = container.getLiquidSlots();
                    for (LiquidSlot slot : slots) {
                        TransferType transferType = slot.getTransferType();
                        Liquid liquid = slot.getLiquid();
                        float transferRate = slot.getTransfer();
                        if (transferType == SEND) {
                            for (BlockPos pos1 : this.linkedPositions) {
                                if (getWorld().isBlockLoaded(pos1)) {
                                    if (!pos.equals(pos1)) {
                                        IliquidContainer container1 = getLiquidContainer(pos1);
                                        if (container1 != null) {
                                            LiquidSlot[] slots1 = container1.getLiquidSlots();
                                            for (LiquidSlot slot1 : slots1) {
                                                TransferType transferType1 = slot1.getTransferType();
                                                Liquid liquid1 = slot1.getLiquid();
                                                if (!liquid1.isEmpty() && transferType1 == ALL) {
                                                    slot1.fill(slot, false);
                                                }
                                            }
                                        } else {
                                            toRemove.add(pos1);
                                        }
                                    }
                                }
                            }
                        } else if (transferType == RECEIVE) {
                            for (BlockPos pos1 : this.linkedPositions) {
                                if (getWorld().isBlockLoaded(pos1)) {
                                    if (!pos.equals(pos1)) {
                                        IliquidContainer container1 = getLiquidContainer(pos1);
                                        if (container1 != null) {
                                            LiquidSlot[] slots1 = container1.getLiquidSlots();
                                            for (LiquidSlot slot1 : slots1) {
                                                TransferType transferType1 = slot1.getTransferType();
                                                if (transferType1 == SEND || transferType1 == ALL) {
                                                    slot1.drain(slot, false, true);
                                                }
                                            }
                                        } else {
                                            toRemove.add(pos1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    toRemove.add(pos);
                }
            }
        }
        this.linkedPositions.removeAll(toRemove);
    }

    public boolean link(BlockPos pos) {
        if (getLiquidContainer(pos) != null) {
            return linkedPositions.add(pos);
        }
        return false;
    }

    public boolean unlink(BlockPos pos) {
        return linkedPositions.remove(pos);
    }

    @Nullable
    public IliquidContainer getLiquidContainer(BlockPos pos) {
        return LiquidUtil.getContainer(getWorld(), pos, null);
    }

}
