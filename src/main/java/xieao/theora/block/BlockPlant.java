package xieao.theora.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import xieao.theora.block.base.IBlock;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockPlant extends BlockBush implements IBlock, IShearable {
    private boolean isShearable;
    @Nullable
    private final IItemProvider drop;
    private final int minQuantity;
    private final int maxQuantity;

    public BlockPlant() {
        this(null, 1, 1);
    }

    public BlockPlant(@Nullable IItemProvider drop, int quantity) {
        this(drop, quantity, quantity);
    }

    public BlockPlant(@Nullable IItemProvider drop, int minQuantity, int maxQuantity) {
        this(Properties.create(Material.PLANTS)
                .doesNotBlockMovement()
                .sound(SoundType.PLANT), drop, minQuantity, maxQuantity);
    }

    public BlockPlant(Properties properties) {
        this(properties, null, 1, 1);
    }

    public BlockPlant(Properties properties, @Nullable IItemProvider drop, int quantity) {
        this(properties, drop, quantity, quantity);
    }

    public BlockPlant(Properties properties, @Nullable IItemProvider drop, int minQuantity, int maxQuantity) {
        super(properties);
        this.drop = drop;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        return this.drop != null ? this.drop : super.getItemDropped(state, worldIn, pos, fortune);
    }

    @Override
    public int quantityDropped(IBlockState state, Random random) {
        return this.drop == null ? Math.max(this.minQuantity, random.nextInt(this.maxQuantity) + 1) : super.quantityDropped(state, random);
    }

    @Override
    public boolean isShearable(ItemStack item, IWorldReader world, BlockPos pos) {
        return isShearable;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IWorld world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this, 1));
    }

    public BlockPlant setShearable() {
        isShearable = true;
        return this;
    }
}
