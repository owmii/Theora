package xieao.theora.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import xieao.theora.api.Consts;

import java.nio.file.Path;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER_MAIN = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER_MAIN);

    public static class General {
        public final ForgeConfigSpec.BooleanValue testBoolean;

        General(ForgeConfigSpec.Builder builder) {
            builder.comment("General settings.").push("general");

            this.testBoolean = builder
                    .comment("Test boolean.")
                    .define("testBoolean", false);
        }
    }

    public static void load() {
        loadFrom(FMLPaths.CONFIGDIR.get().resolve(Consts.MOD_ID + "/"));
    }

    public static final ForgeConfigSpec spec = BUILDER_MAIN.build();

    private static void loadFrom(Path root) {
        if (!root.toFile().exists()) {
            root.toFile().mkdir();
        }
        Path configPath = root.resolve("main.toml");
        //spec.setConfigFile(configPath);
    }
}
