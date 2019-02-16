package xieao.theora.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER_MAIN = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec GENERAL_SPEC;
    public static final General GENERAL;

    public static class General {
        public final ForgeConfigSpec.BooleanValue testBoolean;

        General(ForgeConfigSpec.Builder builder) {
            builder.comment("General settings.").push("general");

            this.testBoolean = builder
                    .comment("Test boolean.")
                    .define("testBoolean", false);
        }
    }

    static {
        final Pair<General, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(General::new);
        GENERAL_SPEC = specPair.getRight();
        GENERAL = specPair.getLeft();
    }
}
