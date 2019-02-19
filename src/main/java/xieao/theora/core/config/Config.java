package xieao.theora.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER_MAIN = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    public static class Server {
        public final ForgeConfigSpec.BooleanValue testBoolean;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server settings.").push("server");

            this.testBoolean = builder
                    .comment("Test boolean.")
                    .define("testBoolean", false);
        }
    }

    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }
}
