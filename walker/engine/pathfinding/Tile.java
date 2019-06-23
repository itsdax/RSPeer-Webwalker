package com.dax.walker.engine.pathfinding;

import org.rspeer.runetek.api.movement.pathfinding.region.util.CollisionFlags;
import org.rspeer.runetek.api.movement.position.Position;

public class Tile {

    private int x, y, z;
    private boolean blocked;
    private boolean blockedN;
    private boolean blockedE;
    private boolean blockedS;
    private boolean blockedW;

    Tile(int x, int y, int z, int flag) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blocked = TileFlag.BLOCKED.valid(flag);
        this.blockedN = TileFlag.BLOCKED_NORTH.valid(flag);
        this.blockedE = TileFlag.BLOCKED_EAST.valid(flag);
        this.blockedS = TileFlag.BLOCKED_SOUTH.valid(flag);
        this.blockedW = TileFlag.BLOCKED_WEST.valid(flag);
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

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isBlockedN() {
        return blockedN;
    }

    public boolean isBlockedE() {
        return blockedE;
    }

    public boolean isBlockedS() {
        return blockedS;
    }

    public boolean isBlockedW() {
        return blockedW;
    }

    public Position toPosition() {
        return new Position(x, y, z);
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        if (getX() != tile.getX()) return false;
        if (getY() != tile.getY()) return false;
        if (getZ() != tile.getZ()) return false;
        if (isBlocked() != tile.isBlocked()) return false;
        if (isBlockedN() != tile.isBlockedN()) return false;
        if (isBlockedE() != tile.isBlockedE()) return false;
        if (isBlockedS() != tile.isBlockedS()) return false;
        return isBlockedW() == tile.isBlockedW();
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        result = 31 * result + getZ();
        result = 31 * result + (isBlocked() ? 1 : 0);
        result = 31 * result + (isBlockedN() ? 1 : 0);
        result = 31 * result + (isBlockedE() ? 1 : 0);
        result = 31 * result + (isBlockedS() ? 1 : 0);
        result = 31 * result + (isBlockedW() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("(x=%d, y=%d, z=%d)", x, y, z);
    }

}
