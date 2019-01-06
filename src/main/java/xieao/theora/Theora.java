package xieao.theora;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.lib.network.NetworkWrapper;
import xieao.lib.proxy.IProxy;
import xieao.theora.common.block.TheoraBlocks;

@SuppressWarnings("NullableProblems")
@Mod(name = Theora.MOD_NAME, modid = Theora.MOD_ID, version = Theora.MOD_VERSION, dependencies = "required-after:onelib")
public class Theora {

    public static final String MOD_NAME = "Theora";
    public static final String MOD_ID = "theora";
    public static final String MOD_VERSION = "@VERSION@";

    public static final Logger LOG = LogManager.getLogger(MOD_ID);
    public static final NetworkWrapper NET = new NetworkWrapper(MOD_ID);

    public static final CreativeTabs TAB = new CreativeTabs(MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(TheoraBlocks.SHROOM);
        }
    };

    @SidedProxy(serverSide = "xieao.theora.proxy.CommonProxy", clientSide = "xieao.theora.proxy.ClientProxy")
    public static IProxy proxy;

    @Mod.Instance(MOD_ID)
    public static Theora instance;


    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public static ResourceLocation assets(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
