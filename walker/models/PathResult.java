package com.dax.walker.models;

import java.util.List;


public class PathResult {
    private PathStatus pathStatus;
    private List<Point3D> path;
    private int cost;

    public PathStatus getPathStatus() {
        return pathStatus;
    }

    public List<Point3D> getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }
}