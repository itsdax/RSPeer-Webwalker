package com.dax.walker.engine.pathfinding;

public enum TileFlag {
    BLOCKED(0x100, 0x20000, 0x200000, 0xFFFFFF),
    BLOCKED_NORTH(0x2, 0x400),
    BLOCKED_EAST(0x8, 0x1000),
    BLOCKED_SOUTH(0x20, 0x4000),
    BLOCKED_WEST(0x80, 0x10000),
    INITIALIZED(0x1000000),
    ;

    private int[] flags;

    TileFlag(int... flags) {
        this.flags = flags;
    }

    public boolean valid(int flag) {
        for (int f : this.flags) {
            if ((f & flag) == f) return true;
        }
        return false;
    }

}
