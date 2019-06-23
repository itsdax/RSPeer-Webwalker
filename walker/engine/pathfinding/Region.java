package com.dax.walker.engine.pathfinding;

import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.Scene;

public class Region {

    private Tile[][] map;
    private Position base;

    public Region() {
        this(Scene.getBase(), Scene.getCollisionFlags());
    }

    public Region(Position b, int[][] collision) {
        this.base = b;
        map = new Tile[collision.length][collision[0].length];
        if (map.length != map[0].length) throw new IllegalStateException("Collision data error");
        Position position = Players.getLocal().getPosition();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Tile(i + b.getX(), j + b.getY(), b.getFloorLevel(), collision[i][j]);
                if (map[i][j].getX() == position.getX() && map[i][j].getY() == position.getY()) map[i][j].setBlocked(false);
            }
        }
    }

    public boolean contains(Position position) {
        if (position.getFloorLevel() != base.getFloorLevel()) return false;
        if (position.getX() < base.getX()) return false;
        if (position.getX() > base.getX() + 104) return false;
        if (position.getY() < base.getY()) return false;
        if (position.getY() > base.getY() + 104) return false;
        return true;
    }

    public Position getBase() {
        return base;
    }

    public Tile[][] getMap() {
        return map;
    }
}
