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
}
