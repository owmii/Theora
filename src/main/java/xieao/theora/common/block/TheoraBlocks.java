package xieao.theora.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TheoraBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final BlockShroom SHROOM;
    public static final BlockWood WOOD;
    public static final BlockMineral MINERAL;
    public static final BlockCauldron CAULDRON;
    public static final BlockEmber EMBER;
    public static final BlockOrb ORB;
    public static final BlockLiquidUrn LIQUID_URN;
    public static final BlockRunicUrn RUNIC_URN;
    public static final BlockKnowledgeStone KNOWLEDGE_STONE;

    public static final BlockBindingCenter BINDING_CENTER;
    public static final BlockBindingRing BINDING_RING;

    public static final BlockDeathChamber DEATH_CHAMBER;
    public static final BlockDeathChamberWall DEATH_CHAMBER_WALL;

    static {
        SHROOM = register(new BlockShroom(), "shroom");
        WOOD = register(new BlockWood(), "wood");
        MINERAL = register(new BlockMineral(), "mineral");
        CAULDRON = register(new BlockCauldron(), "cauldron");
        EMBER = register(new BlockEmber(), "ember");
        ORB = register(new BlockOrb(), "orb");
        LIQUID_URN = register(new BlockLiquidUrn(), "liquidurn");
        RUNIC_URN = register(new BlockRunicUrn(), "runicurn");
        KNOWLEDGE_STONE = register(new BlockKnowledgeStone(), "knowledgestone");
        BINDING_CENTER = register(new BlockBindingCenter(), "binding");
        BINDING_RING = register(new BlockBindingRing(), "bindingring");
        DEATH_CHAMBER = register(new BlockDeathChamber(), "deathchamber");
        DEATH_CHAMBER_WALL = register(new BlockDeathChamberWall(), "deathchamberwall");

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
