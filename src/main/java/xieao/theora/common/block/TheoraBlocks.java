package xieao.theora.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;
import xieao.lib.block.IGenericBlock;
import xieao.theora.Theora;
import xieao.theora.common.block.binding.BlockBindingCenter;
import xieao.theora.common.block.binding.BlockBindingRing;
import xieao.theora.common.block.cauldron.BlockCauldron;
import xieao.theora.common.block.deathchamber.BlockDeathChamber;
import xieao.theora.common.block.deathchamber.BlockDeathChamberWall;
import xieao.theora.common.block.liquidurn.BlockLiquidUrn;
import xieao.theora.common.block.misc.*;
import xieao.theora.common.block.orb.BlockOrb;
import xieao.theora.common.block.runicurn.BlockRunicUrn;
import xieao.theora.common.item.TheoraItems;

import java.util.ArrayList;
import java.util.List;

public class TheoraBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block WOOD = register(new BlockWood(), "wood");
    public static final Block MINERAL = register(new BlockMineral(), "mineral");
    public static final Block CAULDRON = register(new BlockCauldron(), "cauldron");
    public static final Block KNOWLEDGE_STONE = register(new BlockKnowledgeStone(), "knowledgestone");
    public static final Block SHROOM = register(new BlockShroom(), "shroom");
    public static final Block EMBER = register(new BlockEmber(), "ember");
    public static final Block ORB = register(new BlockOrb(), "orb");
    public static final Block LIQUID_URN = register(new BlockLiquidUrn(), "liquidurn");
    public static final Block RUNIC_URN = register(new BlockRunicUrn(), "runicurn");

    public static final Block BINDING_CENTER = register(new BlockBindingCenter(), "binding");
    public static final Block BINDING_RING = register(new BlockBindingRing(), "bindingring");

    public static final Block DEATH_CHAMBER = register(new BlockDeathChamber(), "deathchamber");
    public static final Block DEATH_CHAMBER_WALL = register(new BlockDeathChamberWall(), "deathchamberwall");

    static <T extends Block & IGenericBlock> T register(T block, String name) {
        block.setRegistryName(name);
        block.setUnlocalizedName(name);
        block.setCreativeTab(Theora.TAB);
        if (block instanceof ITileEntityProvider) {
            ITileEntityProvider teProvider = (ITileEntityProvider) block;
            TileEntity te = teProvider.createNewTileEntity(DimensionManager.getWorld(0), 0);
            if (te != null && TileEntity.getKey(te.getClass()) == null) {
                TileEntity.register(Theora.assets(name).toString(), te.getClass());
            }
        }
        BLOCKS.add(block);
        TheoraItems.register(block.getItemBlock(), name);
        return block;
    }

}
