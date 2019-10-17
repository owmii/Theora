package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.lib.block.BlockBase;
import xieao.lib.client.particle.Effect;
import xieao.lib.client.particle.Effects;
import xieao.lib.util.math.V3d;

import java.util.Random;

public class HorLogBlock extends BlockBase {
    public static final DirectionProperty ROTATION = HorizontalBlock.HORIZONTAL_FACING;

    public HorLogBlock(Properties properties) {
        super(properties);
        setDefaultState(this.stateContainer.getBaseState().with(ROTATION, Direction.NORTH));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            worldIn.setBlockState(pos, Blocks.OAK_LOG.getDefaultState(), 2);
        } else {
            for (V3d v3d : V3d.from(pos).center().circled(4, 0.5D)) {
                for (int i = 0; i < 40; i++) {
                    Effects.create(Effect.GLOW_SMALL, worldIn, v3d.random(.15D)
                    ).maxAge(10).color(0x02b500).to(v3d.random(.72D))
                            .alpha(0.3F, 1).blend().spawn();
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ItemStack held = player.getHeldItemMainhand();
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, held) == 0) {
            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos, Blocks.OAK_LOG.getDefaultState(), 2);
            } else {
                for (V3d v3d : V3d.from(pos).center().circled(4, 0.5D)) {
                    for (int i = 0; i < 40; i++) {
                        Effects.create(Effect.GLOW_SMALL, worldIn, v3d.random(.15D)
                        ).maxAge(10).color(0x02b500).to(v3d.random(.72D))
                                .alpha(0.3F, 1).blend().spawn();
                    }
                }
            }
        }
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
                Effects.create(Effect.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .south(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .west(0.05D)).maxAge(10).color(0xa1ff00)
                        .blend().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
            case 1:
                Effects.create(Effect.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .south(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .east(1.05D)).maxAge(10).color(0xa1ff00)
                        .blend().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
            case 2:
                Effects.create(Effect.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .north(0.05D)
                        .east(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)).maxAge(10).color(0xa1ff00)
                        .blend().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
            default:
                Effects.create(Effect.GLOW_SMALL, worldIn, new V3d(pos)
                        .up(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)
                        .south(1.05D)
                        .east(0.5D + Math.random() * 0.2D - Math.random() * 0.2D)).maxAge(10).color(0xa1ff00)
                        .blend().alpha(0.5F, 1).scale(0, 3, 0).spawn();
                break;
        }
    }
}
