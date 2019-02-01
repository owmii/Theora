package xieao.theora.block.base;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPlant extends BlockBush implements IBlock {
    public BlockPlant(Builder builder) {
        super(builder);
    }

    public BlockPlant() {
        this(Builder.create(Material.PLANTS)
                .doesNotBlockMovement()
                .sound(SoundType.PLANT));
    }
}
