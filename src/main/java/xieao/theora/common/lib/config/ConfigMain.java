package xieao.theora.common.lib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigMain {

    public static void load(Configuration cfg) {

        if (cfg.hasChanged()) {
            cfg.save();
        }
    }
}
