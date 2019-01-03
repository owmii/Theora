package xieao.theora.common.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigWorldGen {

    private static final String CATEGORY = "World Gen";
    private static final String CATEGORY_DESC = "World Gen settings";

    public static boolean enabled = true;

    public static void load(Configuration cfg) {
        cfg.load();
        cfg.addCustomCategoryComment(CATEGORY, CATEGORY_DESC);

        enabled = cfg.getBoolean("enabled", CATEGORY, enabled, "Enable or Disable General Worldgen");

        if (cfg.hasChanged()) {
            cfg.save();
        }
    }
}
