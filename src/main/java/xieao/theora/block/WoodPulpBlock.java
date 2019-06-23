package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.client.particle.Effects;
import xieao.theora.core.lib.util.math.V3d;

import java.util.Random;

public class WoodPulpBlock extends BlockBase {
    public static final DirectionProperty ROTATION = HorizontalBlock.HORIZONTAL_FACING;

    public WoodPulpBlock(Properties properties) {
        super(properties);
        setDefaultState(this.stateContainer.getBaseState().with(ROTATION, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        final Random random = new Random();
        return getDefaultState().with(ROTATION, Direction.byHorizontalIndex(random.nextInt(4)));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(ROTATION, rot.rotate(state.get(ROTATION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(ROTATION)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        switch (rand.nextInt(4)) {
            case 0:
                Effects.create(Effects.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .south(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .west(0.05D)).maxAge(10).color(0xa1ff00)
                        .blendFunc().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
            case 1:
                Effects.create(Effects.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .south(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .east(1.05D)).maxAge(10).color(0xa1ff00)
                        .blendFunc().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
            case 2:
                Effects.create(Effects.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .north(0.05D)
                        .east(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)).maxAge(10).color(0xa1ff00)
                        .blendFunc().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
            default:
                Effects.create(Effects.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .south(1.05D)
                        .east(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)).maxAge(10).color(0xa1ff00)
                        .blendFunc().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
        }
    }
}
