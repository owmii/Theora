package xieao.theora.proxy;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xieao.lib.util.RenderUtil;
import xieao.theora.Theora;
import xieao.theora.client.handler.KeyHandler;
import xieao.theora.client.renderer.TheoraTextures;
import xieao.theora.client.renderer.blockstate.TheoraStateMapper;
import xieao.theora.client.renderer.entity.EntityRenderer;
import xieao.theora.client.renderer.tesr.TESRRenderer;
import xieao.theora.common.item.TheoraItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderUtil.renderItems(TheoraItems.ITEMS);
        TheoraStateMapper.register();
        EntityRenderer.register();
        KeyHandler.register();
        MinecraftForge.EVENT_BUS.register(new TheoraTextures());
        OBJLoader.INSTANCE.addDomain(Theora.MOD_ID);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        TESRRenderer.register();
        RenderUtil.renderItemsColor(TheoraItems.ITEMS);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
