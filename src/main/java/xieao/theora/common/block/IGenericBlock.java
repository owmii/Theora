package xieao.theora.common.block;

import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.ItemBlockBase;

public interface IGenericBlock {

    <T extends ItemBlockBase & IGenericItem> T getItemBlock();
}
