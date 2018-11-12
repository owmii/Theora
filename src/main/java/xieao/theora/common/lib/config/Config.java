package xieao.theora.common.lib.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xieao.theora.Theora;

import javax.annotation.Nullable;
import java.io.File;

public class Config {

    @Nullable
    private static Configuration main;

    @Nullable
    private static Configuration worldGen;


    public static void preInit(FMLPreInitializationEvent event) {
        String dir = event.getModConfigurationDirectory().getPath() + "/" + Theora.MOD_ID + "/";

        main = new Configuration(new File(dir, Theora.MOD_ID + ".cfg"));
        ConfigMain.load(main);

        worldGen = new Configuration(new File(dir, "worldGen.cfg"));
        ConfigWorldGen.load(worldGen);
    }
}
