package xieao.theora.common.lib.helper;

import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class VecHelper {

    public static List<Vec3d> getRoundedVecs(Vec3d center, int count, double radius) {
        List<Vec3d> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double slice = 2.0D * Math.PI / count;
            double angle = slice * i;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            list.add(center.addVector(x, center.y, z));
        }
        return list;
    }
}
