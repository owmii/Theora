package xieao.theora.client.helper;

import net.minecraft.client.resources.I18n;
import xieao.theora.Theora;

public class I18nHelper {

    public static String format(String translateKey, Object... parameters) {
        return I18n.format(Theora.MOD_ID + "." + translateKey, parameters);
    }

    public static String formatTooltip(String translateKey, Object... parameters) {
        return I18n.format(Theora.MOD_ID + ".tooltip." + translateKey, parameters);
    }

    public static String formatChat(String translateKey, Object... parameters) {
        return I18n.format(Theora.MOD_ID + ".chat." + translateKey, parameters);
    }

    public static String formatBookTitle(String translateKey, Object... parameters) {
        return I18n.format(Theora.MOD_ID + ".book.title." + translateKey, parameters);
    }

    public static String formatBookText(String translateKey, Object... parameters) {
        return I18n.format(Theora.MOD_ID + ".book.text." + translateKey, parameters);
    }

    public static String formatBookSection(String translateKey, Object... parameters) {
        return I18n.format(Theora.MOD_ID + ".book.section." + translateKey, parameters);
    }
}
