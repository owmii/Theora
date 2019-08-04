package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.lib.block.BlockBase;
import xieao.lib.client.particle.Effect;
import xieao.lib.client.particle.Effects;
import xieao.lib.util.math.V3d;

import java.util.Random;

public class LiquidBlock extends BlockBase {
    public static final BooleanProperty FULL = BooleanProperty.create("full");

    public LiquidBlock(Properties properties) {
        super(properties);
        setDefaultState(this.stateContainer.getBaseState().with(FULL, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FULL);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Effects.create(Effect.GLOW_SMALL, worldIn, new V3d(pos).down(!stateIn.get(FULL) ? 0.4D : 0)
                .up(0.9D).south(Math.random())
                .east(Math.random())).maxAge(10).color(0x02b500)
                .alpha(0.3F, 1).blend().scale(0, 3, 0).gravity(-0.03F).spawn();

    }

    @Override
    public boolean hideGroup() {
        return true;
    }
}
