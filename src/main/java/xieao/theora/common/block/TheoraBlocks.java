package xieao.theora.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xieao.theora.Theora;
import xieao.theora.common.block.bindingstone.BlockBindingStone;
import xieao.theora.common.block.fermentingjar.BlockFermentingJar;
import xieao.theora.common.block.misc.BlockShroom;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TheoraBlocks {

    public static final Set<Block> BLOCKS = new HashSet<>();

    public static final BlockShroom SHROOM;
    public static final BlockFermentingJar FERMENTING_JAR;
    public static final BlockBindingStone BINDING_STONE;

    static {
        SHROOM = register(new BlockShroom(), "shroom");
        FERMENTING_JAR = register(new BlockFermentingJar(), "fermentingjar");
        BINDING_STONE = register(new BlockBindingStone(), "bindingstone");

        //noinspection ConstantConditions
        for (Block block : BLOCKS) {
            if (block instanceof ITileEntityProvider) {
                ITileEntityProvider tep = (ITileEntityProvider) block;
                TileEntity tileEntity = tep.createNewTileEntity(DimensionManager.getWorld(0), 0);
                if (tileEntity != null && TileEntity.getKey(tileEntity.getClass()) == null) {
                    ResourceLocation registryName = block.getRegistryName();
                    Objects.requireNonNull(registryName);
                    GameRegistry.registerTileEntity(tileEntity.getClass(), registryName);
                }
            }
        }
    }

    private static <T extends Block & IGenericBlock> T register(T block, String name) {
        block.setRegistryName(name);
        block.setUnlocalizedName(name);
        block.setCreativeTab(Theora.TAB);
        BLOCKS.add(block);
        return block;
    }

}
