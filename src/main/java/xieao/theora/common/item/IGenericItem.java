package xieao.theora.common.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGenericItem {

    default boolean isCreativeItem() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    void renderItem();

    Enum<?>[] getSubTypes();
}
