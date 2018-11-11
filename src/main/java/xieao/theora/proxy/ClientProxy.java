package xieao.theora.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xieao.theora.client.handler.KeyHandler;
import xieao.theora.client.renderer.blockstate.TheoraStateMapper;
import xieao.theora.client.renderer.entity.RenderInteractor;
import xieao.theora.client.renderer.item.IColoredItem;
import xieao.theora.client.renderer.tesr.TESRRenderer;
import xieao.theora.common.entity.EntityInteractor;
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
        TheoraStateMapper.register();
        KeyHandler.register();
        RenderingRegistry.registerEntityRenderingHandler(EntityInteractor.class, RenderInteractor::new);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        TESRRenderer.register();
        for (Item item : TheoraItems.ITEMS) {
            if (item instanceof IColoredItem) {
                IColoredItem colorItem = (IColoredItem) item;
                Minecraft mc = Minecraft.getMinecraft();
                ItemColors itemColors = mc.getItemColors();
                itemColors.registerItemColorHandler(colorItem.getItemColor(), item);
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
