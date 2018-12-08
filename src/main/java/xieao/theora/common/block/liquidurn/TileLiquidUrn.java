package xieao.theora.common.block.liquidurn;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.TileLiquidContainer;

public class TileLiquidUrn extends TileLiquidContainer {

    public TileLiquidUrn() {
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(Liquid.EMPTY, false, 10000.0F, 0.0F, 100.0F, LiquidSlot.TransferType.ALL)
        );
    }

    public LiquidSlot getLiquidSlot() {
        return this.liquidContainer.getLiquidSlot(0);
    }

    @Override
    public boolean keepData() {
        return !getLiquidSlot().isEmpty();
    }
}
