package com.dax.walker.engine.pathfinding;

import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Builds a cache of local area for pathfinding.
 *
 * ONLY works with tiles loaded within region data. i.e: `Scene.getCollisionFlags()`
 *  - Always 104 x 104
 */
public class BFSMapCache extends BFS {
    private Tile[][] parentMap;
    private int[][] costMap;
    private int accessibleArea;

    public BFSMapCache() {
        this(Players.getLocal().getPosition(), new Region());
    }

    public BFSMapCache(Position start, Region region) {
        super(region);
        calculate(start);
    }

    @Override
    List<Tile> getPath(Tile start, Tile end) {
        List<Tile> path = new ArrayList<>();

        Tile temp = end;
        while (temp != null) {
            path.add(temp);
            temp = getParent(temp);
        }

        Collections.reverse(path);
        return path;
    }

    public boolean canReach(Position position) {
        if (!getRegion().contains(position)) return false;
        if (position.equals(Players.getLocal().getPosition())) return true;
        Tile tile = getTile(position);
        return tile != null && getCost(tile) != Integer.MAX_VALUE;
    }

    public int getMoveCost(Position position) {
        if (!getRegion().contains(position)) return Integer.MAX_VALUE;
        if (position.equals(Players.getLocal().getPosition())) return 0;
        Tile tile = getTile(position);
        return tile != null ? getCost(tile) : Integer.MAX_VALUE;
    }

    /**
     *
     * Collision aware random selector
     *
     * @param maxDistance
     * @return
     */
    public Position getRandom(int maxDistance) {
        // TODO: write an actual method for this...
        for (int i = 0; i < 50000; i++) {
            int x = Random.nextInt(0, 103);
            int y = Random.nextInt(0, 103);
            if (costMap[x][y] < maxDistance) return new Position(getRegion().getBase().getX() + x, getRegion().getBase().getY() + y);
        }
        return null;
    }

    private int getCost(Tile tile) {
        return costMap[tile.getX() - getBase().getX()][tile.getY() - getBase().getY()];
    }

    public Position getParent(Position position) {
        if (!getRegion().contains(position)) return null;
        Tile tile = getTile(position);
        if (tile == null) return null;
        return getParent(tile) != null ? getParent(tile).toPosition() : null;
    }

    private Tile getParent(Tile tile) {
        return parentMap[tile.getX() - getBase().getX()][tile.getY() - getBase().getY()];
    }

    public int getAccessibleArea() {
        return accessibleArea;
    }

    private void calculate(Position start) {
        parentMap = new Tile[getMap().length][getMap().length];
        costMap = new int[getMap().length][getMap().length];

        // Initialize CostMap.
        for (int i = 0; i < costMap.length; i++) for (int j = 0; j < costMap[i].length; j++) costMap[i][j] = Integer.MAX_VALUE;

        Tile initial = getTile(start);
        if (initial == null || initial.isBlocked()) return;

        LinkedList<Tile> queue = new LinkedList<>();
        queue.push(initial);
        costMap[initial.getX() - getBase().getX()][initial.getY() - getBase().getY()] = 0;

        while (!queue.isEmpty()) {
            Tile tile = queue.pop();
            accessibleArea++;
            for (Tile neighbor : getNeighbors(tile)) {
                if (costMap[neighbor.getX() - getBase().getX()][neighbor.getY() - getBase().getY()] != Integer.MAX_VALUE) continue;
                costMap[neighbor.getX() - getBase().getX()][neighbor.getY() - getBase().getY()] = costMap[tile.getX() - getBase().getX()][tile.getY() - getBase().getY()] + 1;
                parentMap[neighbor.getX() - getBase().getX()][neighbor.getY() - getBase().getY()] = tile;
                queue.addLast(neighbor);
            }
        }
    }

}
