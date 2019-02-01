package xieao.theora.block.heat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import xieao.theora.block.base.Tile;
import xieao.theora.core.ITiles;

import javax.annotation.Nullable;

public class TileHeat extends Tile.Tickable {
    private int age;

    public TileHeat() {
        super(ITiles.HEAT);
    }

    @Override
    public void readStorable(NBTTagCompound compound) {
        this.age = compound.getInt("Age");
    }

    @Nullable
    @Override
    public NBTTagCompound writeStorable(NBTTagCompound compound) {
        compound.setInt("Age", this.age);
        return compound;
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
