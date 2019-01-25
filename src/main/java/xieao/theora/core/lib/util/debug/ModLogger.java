package xieao.theora.core.lib.util.debug;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.theora.Theora;

public class ModLogger {
    public static Logger get() {
        return LogManager.getLogger(Theora.ID.toUpperCase());
    }
}
