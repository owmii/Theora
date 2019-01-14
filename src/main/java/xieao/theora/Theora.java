package xieao.theora;

import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.lib.proxy.IProxy;
import xieao.theora.command.TheoraCommand;

@Mod(Theora.MOD_ID)
public class Theora {
    public static final String MOD_ID = "theora";
    public static final Logger LOG = LogManager.getLogger(MOD_ID.toUpperCase());
    public static final SimpleChannel NET;

    public static final ItemGroup MAIN = new ItemGroup(MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Items.APPLE);
        }
    };

    private final IProxy proxy;

    public Theora() {
        this.proxy = DistExecutor.runForDist(() -> Proxy.Client::new, () -> Proxy::new);
        FMLModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLModLoadingContext.get().getModEventBus().addListener(this::postInit);
    }

    static {
        NET = NetworkRegistry.ChannelBuilder.named(loc("main"))
                .clientAcceptedVersions(a -> true)
                .serverAcceptedVersions(a -> true)
                .networkProtocolVersion(() -> NetworkHooks.NETVERSION)
                .simpleChannel();
    }

    public void preInit(FMLPreInitializationEvent event) {
        this.proxy.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        this.proxy.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        this.proxy.postInit(event);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        new TheoraCommand(event.getCommandDispatcher());
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
