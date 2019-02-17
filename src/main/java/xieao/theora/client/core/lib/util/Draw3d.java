package xieao.theora.client.core.lib.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public class Draw3d {
    public static void cube(BufferBuilder bb) {
        for (EnumFacing facing : EnumFacing.values()) {
            quad(bb, facing);
        }
    }

    public static void quad(BufferBuilder bb, EnumFacing facing) {
        double[][] pos = new double[4][3];
        double[][] uv = new double[4][2];
        switch (facing) {
            case DOWN:

                break;
            case UP:
                break;
            case NORTH:
                break;
            case SOUTH:
                break;
            case WEST:
                break;
            case EAST:
                break;
        }
        quad(bb, pos, uv);
    }

    public static void quad(BufferBuilder bb, double[][] pos, double[][] uv) {
        bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bb.pos(pos[0][0], pos[0][2], pos[0][3]).tex(uv[0][0], uv[0][1]).endVertex();
        bb.pos(pos[1][0], pos[1][2], pos[1][3]).tex(uv[1][0], uv[1][1]).endVertex();
        bb.pos(pos[2][0], pos[2][2], pos[2][3]).tex(uv[2][0], uv[2][1]).endVertex();
        bb.pos(pos[3][0], pos[3][2], pos[3][3]).tex(uv[3][0], uv[3][1]).endVertex();
    }
}
