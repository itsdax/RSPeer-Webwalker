package com.dax.walker.engine.pathfinding;

public enum Direction {

    NORTH(new int[]{0, 1}, false),
    EAST(new int[]{1, 0}, false),
    SOUTH(new int[]{0, -1}, false),
    WEST(new int[]{-1, 0}, false),
    NORTH_EAST(new int[]{1, 1}, true),
    SOUTH_WEST(new int[]{-1, -1}, true),
    SOUTH_EAST(new int[]{1, -1}, true),
    NORTH_WEST(new int[]{-1, 1}, true);

    private int[] matrix;
    private boolean diagonal;

    Direction(int[] matrix, boolean diagonal) {
        this.matrix = matrix;
        this.diagonal = diagonal;
    }

    public boolean isDiagonal() {
        return diagonal;
    }

    public int translateX(int initial) {
        return initial + this.matrix[0];
    }

    public int translateY(int initial) {
        return initial + this.matrix[1];
    }

}
