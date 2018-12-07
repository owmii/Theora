package xieao.theora.common.lib.helper.math;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class Vec3 extends Vec3d {

    public Vec3(double xIn, double yIn, double zIn) {
        super(xIn, yIn, zIn);
    }

    public Vec3(Vec3i vector) {
        super(vector);
    }

    public Vec3 down() {
        return down(1.0D);
    }

    public Vec3 down(double factor) {
        return (Vec3) addVector(0.0D, -factor, 0.0D);
    }

    public Vec3 up() {
        return up(1.0D);
    }

    public Vec3 up(double factor) {
        return (Vec3) addVector(0.0D, factor, 0.0D);
    }

    public Vec3 north() {
        return north(1.0D);
    }

    public Vec3 north(double factor) {
        return (Vec3) addVector(0.0D, 0.0D, -factor);
    }

    public Vec3 south() {
        return south(1.0D);
    }

    public Vec3 south(double factor) {
        return (Vec3) addVector(0.0D, 0.0D, factor);
    }

    public Vec3 east() {
        return east(1.0D);
    }

    public Vec3 east(double factor) {
        return (Vec3) addVector(factor, 0.0D, 0.0D);
    }

    public Vec3 west() {
        return west(1.0D);
    }

    public Vec3 west(double factor) {
        return (Vec3) addVector(-factor, 0.0D, 0.0D);
    }

    public List<Vec3> getRoundedVecList(int count, double radius) {
        return getRoundedVecList(count, radius, 0, 0.0D);
    }

    public List<Vec3> getRoundedVecList(int count, double radius, int ticks, double speed) {
        List<Vec3> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double slice = 2.0D * Math.PI / count;
            double angle = slice * i;
            double x = radius * Math.cos(angle + ticks * speed);
            double z = radius * Math.sin(angle + ticks * speed);
            list.add((Vec3) addVector(x, 0.0D, z));
        }
        return list;
    }
}
