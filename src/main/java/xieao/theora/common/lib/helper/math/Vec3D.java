package xieao.theora.common.lib.helper.math;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class Vec3D extends Vec3d {

    public static final Vec3D ZERO = new Vec3D(0.0D, 0.0D, 0.0D);

    public Vec3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Vec3D(Vec3i vector) {
        super(vector);
    }

    public Vec3D up() {
        return up(1.0D);
    }

    public Vec3D up(double factor) {
        return addVector(0.0D, factor, 0.0D);
    }

    public Vec3D down() {
        return down(1.0D);
    }

    public Vec3D down(double factor) {
        return addVector(0.0D, -factor, 0.0D);
    }

    public Vec3D north() {
        return north(1.0D);
    }

    public Vec3D north(double factor) {
        return addVector(0.0D, 0.0D, -factor);
    }

    public Vec3D south() {
        return south(1.0D);
    }

    public Vec3D south(double factor) {
        return addVector(0.0D, 0.0D, factor);
    }

    public Vec3D east() {
        return east(1.0D);
    }

    public Vec3D east(double factor) {
        return addVector(factor, 0.0D, 0.0D);
    }

    public Vec3D west() {
        return west(1.0D);
    }

    public Vec3D west(double factor) {
        return addVector(-factor, 0.0D, 0.0D);
    }

    public Vec3D center() {
        return new Vec3D(((int) this.x) + 0.5D, ((int) this.y) + 0.5D, ((int) this.z) + 0.5D);
    }

    public Vec3D center(AxisAlignedBB bb) {
        return new Vec3D(
                bb.minX + (bb.maxX - bb.minX) * 0.5D,
                bb.minY + (bb.maxY - bb.minY) * 0.5D,
                bb.minZ + (bb.maxZ - bb.minZ) * 0.5D
        );
    }

    public Vec3D centerU() {
        return center().up(0.5D);
    }

    public Vec3D centerD() {
        return center().down(0.5D);
    }

    public Vec3D centerN() {
        return center().north(0.5D);
    }

    public Vec3D centerS() {
        return center().south(0.5D);
    }

    public Vec3D centerE() {
        return center().east(0.5D);
    }

    public Vec3D centerW() {
        return center().west(0.5D);
    }

    public Vec3D random(double dist) {
        double d0 = Math.random() < 0.5D ? -dist : dist;
        double d1 = Math.random() < 0.5D ? -dist : dist;
        double d2 = Math.random() < 0.5D ? -dist : dist;
        return addVector(Math.random() * d0, Math.random() * d1, Math.random() * d2);
    }

    @Override
    public Vec3D add(Vec3d vec) {
        return addVector(vec.x, vec.y, vec.z);
    }

    @Override
    public Vec3D addVector(double x, double y, double z) {
        return new Vec3D(this.x + x, this.y + y, this.z + z);
    }

    public List<Vec3D> getCircledVecList(int count, double radius) {
        return getCircledVecList(count, radius, 0, 0.0D);
    }

    public List<Vec3D> getCircledVecList(int count, double radius, int ticks, double speed) {
        List<Vec3D> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double slice = 2.0D * Math.PI / count;
            double angle = slice * i;
            double x = radius * Math.cos(angle + ticks * speed);
            double z = radius * Math.sin(angle + ticks * speed);
            list.add(addVector(x, 0.0D, z));
        }
        return list;
    }

    public Vec3D toOrigin() {
        return new Vec3D((int) this.x, (int) this.y, (int) this.z);
    }

    public BlockPos toBlockPos() {
        return new BlockPos(this);
    }
}
