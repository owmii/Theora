package xieao.theora.core.lib.util.debug;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.theora.api.Consts;

public class ModLogger {
    public static Logger get() {
        return LogManager.getLogger(Consts.MOD_ID.toUpperCase());
    }
}
