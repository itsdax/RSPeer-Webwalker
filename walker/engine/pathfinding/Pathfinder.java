package com.dax.walker.engine.pathfinding;

import org.rspeer.runetek.api.movement.position.Position;

import java.util.ArrayList;
import java.util.List;

import static com.dax.walker.engine.pathfinding.Direction.*;

public abstract class Pathfinder {

    private Region region;
    private Position base;
    private Tile[][] map;

    public Pathfinder(Region region) {
        this.region = region;
        this.base = region.getBase();
        this.map = region.getMap();
    }

    abstract List<Tile> getPath(Tile start, Tile end);

    List<Position> getPath(Position start, Position end) {
        Tile a = getTile(start);
        if (a == null || a.isBlocked()) return null;
        Tile b = getTile(end);
        if (b == null || b.isBlocked()) return null;
        return convert(getPath(a, b));
    }

    public List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (!isValidDirection(direction, tile)) continue;
            Tile neighbor = getTile(direction, tile);
            if (neighbor == null) continue;
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    public Tile getTile(Position position) {
        if (!region.contains(position)) return null;
        if (!isValidTile(position.getX(), position.getY())) return null;
        int x = position.getX() - base.getX();
        int y = position.getY() - base.getY();
        return map[x][y];
    }

    public List<Position> convert(List<Tile> path) {
        List<Position> positions = new ArrayList<>();
        for (Tile tile : path) {
            positions.add(new Position(tile.getX(), tile.getY(), tile.getZ()));
        }
        return positions;
    }

    public Position getBase() {
        return base;
    }

    public Tile[][] getMap() {
        return map;
    }

    private boolean isValidDirection(Direction direction, Tile tile) {
        if (tile == null) return false;

        Tile destination = getTile(direction, tile);
        if (destination == null || destination.isBlocked()) return false;

        if (!direction.isDiagonal()) {
            switch (direction) {
                case NORTH:
                    return !tile.isBlockedN();
                case EAST:
                    return !tile.isBlockedE();
                case SOUTH:
                    return !tile.isBlockedS();
                case WEST:
                    return !tile.isBlockedW();
                default:
                    throw new IllegalStateException("There can only be 4 non diagonal neighbors...");
            }
        }

        switch (direction) {
            case NORTH_EAST:
                return isValidDirection(NORTH, tile) && isValidDirection(EAST, getTile(NORTH, tile))
                        && isValidDirection(EAST, tile) && isValidDirection(NORTH, getTile(EAST, tile));
            case SOUTH_WEST:
                return isValidDirection(SOUTH, tile) && isValidDirection(WEST, getTile(SOUTH, tile))
                        && isValidDirection(WEST, tile) && isValidDirection(SOUTH, getTile(WEST, tile));
            case SOUTH_EAST:
                return isValidDirection(SOUTH, tile) && isValidDirection(EAST, getTile(SOUTH, tile))
                        && isValidDirection(EAST, tile) && isValidDirection(SOUTH, getTile(EAST, tile));
            case NORTH_WEST:
                return isValidDirection(NORTH, tile) && isValidDirection(WEST, getTile(NORTH, tile))
                        && isValidDirection(WEST, tile) && isValidDirection(NORTH, getTile(WEST, tile));
        }

        throw new IllegalStateException("No handler for direction: " + direction);
    }

    private Tile getTile(Direction direction, Tile tile) {
        int x = direction.translateX(tile.getX()) - base.getX();
        int y = direction.translateY(tile.getY()) - base.getY();
        return isValidTile(x, y) ? map[x][y] : null;
    }

    private boolean isValidTile(int x, int y) {
        if (x > 104 || y > 104) {
            x = x - base.getX();
            y = y - base.getY();
        }
        return x >= 0 && y >= 0 && x < map.length && y < map.length;
    }

    public Region getRegion() {
        return region;
    }
}
