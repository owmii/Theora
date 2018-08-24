package xieao.theora;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.item.TheoraItems;
import xieao.theora.proxy.IProxy;

@Mod.EventBusSubscriber
@Mod(name = Theora.MOD_NAME, modid = Theora.MOD_ID, version = Theora.MOD_VERSION)
public class Theora {

    public static final String MOD_NAME = "Theora";
    public static final String MOD_ID = "theora";
    public static final String MOD_VERSION = "@VERSION@";

    public static final String SIDE_SERVER = "xieao.theora.proxy.CommonProxy";
    public static final String SIDE_CLIENT = "xieao.theora.proxy.ClientProxy";

    @SuppressWarnings("NullableProblems")
    @SidedProxy(serverSide = SIDE_SERVER, clientSide = SIDE_CLIENT)
    public static IProxy proxy;

    @Mod.Instance(MOD_ID)
    @SuppressWarnings("NullableProblems")
    public static Theora instance;

    public static final Logger LOG = LogManager.getLogger(MOD_ID);

    public static final CreativeTabs TAB = new CreativeTabs(MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(TheoraBlocks.SHROOM);
        }
    };

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

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : TheoraItems.ITEMS) {
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (Block block : TheoraBlocks.BLOCKS) {
            event.getRegistry().register(block);
        }
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
