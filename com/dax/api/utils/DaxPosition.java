package com.dax.api.utils;

import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.api.movement.position.Position;

public class DaxPosition implements Positionable {

    private int x, y, z;

    public DaxPosition(Position position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getFloorLevel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof DaxPosition)) return false;

        DaxPosition that = (DaxPosition) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public Position getPosition() {
        return new Position(x, y, z);
    }
}
