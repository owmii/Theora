package xieao.theora.client.helper;

import net.minecraft.client.resources.I18n;
import xieao.theora.Theora;

public class I18nHelper {

    public static String format(String translateKey, Object... parameters) {
        return I18n.format(Theora.MOD_ID + "." + translateKey, parameters);
    }
}
