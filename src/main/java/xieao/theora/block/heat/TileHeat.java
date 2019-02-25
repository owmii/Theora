package xieao.theora.block.heat;

import net.minecraft.nbt.NBTTagCompound;
import xieao.theora.block.base.Tile;
import xieao.theora.core.ITiles;

public class TileHeat extends Tile.Tickable {
    private int age;

    public TileHeat() {
        super(ITiles.HEAT);
    }

    @Override
    public void readStorable(NBTTagCompound compound) {
        this.age = compound.getInt("Age");
    }

    @Override
    public NBTTagCompound writeStorable(NBTTagCompound compound) {
        compound.putInt("Age", this.age);
        return compound;
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (getBlockState().getBlock() instanceof BlockHeat) {
                BlockHeat blockHeat = (BlockHeat) getBlockState().getBlock();
                int maxAge = blockHeat.getMaxAge();
                if (maxAge > 0 && this.age++ >= maxAge) {
                    this.world.removeBlock(this.pos);
                    this.world.removeTileEntity(this.pos);
                }
            }
        }
        super.tick();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
