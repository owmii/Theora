package xieao.theora.common.block;

import net.minecraft.item.ItemBlock;
import xieao.theora.common.item.IGenericItem;

public interface IGenericBlock {

    <T extends ItemBlock & IGenericItem> T getItemBlock();
}
