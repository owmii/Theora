package xieao.theora.block.heat;

import net.minecraft.block.state.IBlockState;
import xieao.theora.block.ITiles;
import xieao.theora.block.Tile;

public class TileHeat extends Tile.Tickable {
    private int age;

    public TileHeat() {
        super(ITiles.HEAT);
    }

    @Override
    public void tick() {
        super.tick();
        IBlockState state = getBlockState();
        if (state.getBlock() instanceof BlockHeat) {
            BlockHeat blockHeat = (BlockHeat) state.getBlock();
            int maxAge = blockHeat.getMaxAge();
            if (maxAge > 0 && this.age++ >= maxAge) {
                this.world.removeBlock(this.pos);
                this.world.removeTileEntity(this.pos);
                //TODO particles, sound
            }
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
