package xieao.theora.common.lib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigMain {

    public static final String CAT_LIQUID_PRODUCTION = "Liquids production";

    public static float runicUrnBaseProduction;

    public static void load(Configuration cfg) {
        runicUrnBaseProduction = cfg.getFloat("runicUrnBaseProduction", CAT_LIQUID_PRODUCTION, 0.00214F, 0.0F, 2.0F, "Runic urn generation per knowledge power");
        if (cfg.hasChanged()) {
            cfg.save();
        }
    }
}
