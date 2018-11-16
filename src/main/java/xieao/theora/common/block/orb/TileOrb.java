package xieao.theora.common.block.orb;

import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import xieao.theora.api.liquid.IliquidContainer;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.api.liquid.LiquidSlot.TransferType;
import xieao.theora.api.liquid.LiquidUtil;
import xieao.theora.common.block.TileBase;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static xieao.theora.api.liquid.LiquidSlot.TransferType.*;

public class TileOrb extends TileBase implements ITickable {

    private final Set<BlockPos> linkedPositions = new HashSet<>();

    @Override
    public void update() {
        Set<BlockPos> toRemove = new HashSet<>();
        for (BlockPos pos : this.linkedPositions) {
            if (getWorld().isAreaLoaded(pos, 4)) {
                IliquidContainer container = getLiquidContainer(pos);
                if (container != null) {
                    LiquidSlot[] slots = container.getLiquidSlots();
                    for (LiquidSlot slot : slots) {
                        TransferType transferType = slot.getTransferType();
                        float transferRate = slot.getTransfer();
                        if (transferType == SEND) {
                            for (BlockPos pos1 : this.linkedPositions) {
                                if (getWorld().isAreaLoaded(pos1, 4)) {
                                    if (!pos.equals(pos1)) {
                                        IliquidContainer container1 = getLiquidContainer(pos1);
                                        if (container1 != null) {
                                            LiquidSlot[] slots1 = container1.getLiquidSlots();
                                            for (LiquidSlot slot1 : slots1) {
                                                TransferType transferType1 = slot1.getTransferType();
                                                if (transferType1 == ALL) {
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
                                if (getWorld().isAreaLoaded(pos1, 4)) {
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
