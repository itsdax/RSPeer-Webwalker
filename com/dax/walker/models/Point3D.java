package com.dax.walker.models;


import com.allatori.annotations.DoNotRename;
import org.rspeer.runetek.api.movement.position.Position;

@DoNotRename
public class Point3D {

    @DoNotRename
    private int x, y, z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }


    public static Point3D from(Position position) {
        return new Point3D(position.getX(), position.getY(), position.getFloorLevel());
    }

    public Position toPosition() {
        return new Position(x, y, z);
    }

}