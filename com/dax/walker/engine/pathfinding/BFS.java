package com.dax.walker.engine.pathfinding;


import java.util.List;

public class BFS extends Pathfinder {

    public BFS(Region region) {
        super(region);
    }

    @Override
    List<Tile> getPath(Tile start, Tile end) {
        throw new IllegalStateException("XD"); //TODO: 2lazy
    }

}
