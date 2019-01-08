package xieao.theora;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xieao.lib.proxy.IProxy;
import xieao.lib.util.RegistryUtil;
import xieao.theora.block.TheoraBlocks;
import xieao.theora.item.TheoraItems;

public class Proxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RegistryUtil.registerAll(TheoraBlocks.BLOCKS);
        RegistryUtil.registerAll(TheoraItems.ITEMS);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @OnlyIn(Dist.CLIENT)
    public static class Client extends Proxy {

        @Override
        public void preInit(FMLPreInitializationEvent event) {
            super.preInit(event);
        }

        @Override
        public void init(FMLInitializationEvent event) {
            super.init(event);
        }
    }
}
