package xieao.theora.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER_MAIN = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec GENERAL_SPEC;
    public static final Server GENERAL;

    public static class Server {
        public final ForgeConfigSpec.BooleanValue worldGen;
        public final ForgeConfigSpec.BooleanValue worldGenPlants;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Theora general settings.").push("general");
            this.worldGen = builder.comment("Enable global world gen, disabling this will turn off all mod world generations.")
                    .define("worldGenEnabled", true);
            this.worldGenPlants = builder.comment("Enable plants world gen.")
                    .define("worldGenPlantsEnabled", true);
        }
    }

    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        GENERAL_SPEC = specPair.getRight();
        GENERAL = specPair.getLeft();
    }
}
