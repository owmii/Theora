package xieao.theora.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xieao.theora.client.handler.KeyHandler;
import xieao.theora.client.renderer.blockstate.TheoraStateMapper;
import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.TheoraItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        for (Item item : TheoraItems.ITEMS) {
            if (item instanceof IGenericItem) {
                ((IGenericItem) item).renderItem();
            }
        }
        TheoraStateMapper.registerStateMappers();
        KeyHandler.register();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
