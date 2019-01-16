package xieao.theora;

import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.theora.core.command.TheoraCommand;

@Mod(Theora.MOD_ID)
public class Theora {
    public static final String MOD_ID = "theora";
    public static final Logger LOG = LogManager.getLogger(MOD_ID.toUpperCase());
    public static final SimpleChannel NET;
    public static final ItemGroup MAIN;
    private final Proxy proxy;

    public Theora() {
        this.proxy = DistExecutor.runForDist(() -> Proxy.Client::new, () -> Proxy::new);
        FMLModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.addListener(this::starting);
    }

    static {
        NET = NetworkRegistry.ChannelBuilder.named(loc("main"))
                .clientAcceptedVersions(a -> true)
                .serverAcceptedVersions(a -> true)
                .networkProtocolVersion(() -> NetworkHooks.NETVERSION)
                .simpleChannel();

        MAIN = new ItemGroup(MOD_ID) {
            @OnlyIn(Dist.CLIENT)
            public ItemStack createIcon() {
                return new ItemStack(Items.APPLE);
            }
        };
    }

    public void setup(FMLCommonSetupEvent event) {
        this.proxy.setup(event);
    }

    public void enqueueIMC(InterModEnqueueEvent event) {
        this.proxy.enqueueIMC(event);
    }

    public void processIMC(InterModProcessEvent event) {
        this.proxy.processIMC(event);
    }

    public void starting(FMLServerStartingEvent event) {
        new TheoraCommand(event.getCommandDispatcher());
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
