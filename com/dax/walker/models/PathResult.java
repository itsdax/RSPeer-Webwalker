package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;

import java.util.List;


@DoNotRename
public class PathResult {
    @DoNotRename
    private PathStatus pathStatus;
    @DoNotRename
    private List<Point3D> path;
    @DoNotRename
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