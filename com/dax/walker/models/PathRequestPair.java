package com.dax.walker.models;

public class PathRequestPair {
    private Point3D start;
    private Point3D end;

    public PathRequestPair(Point3D start, Point3D end) {
        this.start = start;
        this.end = end;
    }

    public Point3D getStart() {
        return start;
    }

    public Point3D getEnd() {
        return end;
    }
}
