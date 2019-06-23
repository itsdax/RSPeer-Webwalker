package com.dax.walker.engine;

import com.dax.api.paint.debug.PositionalDebug;
import com.dax.walker.engine.definitions.Teleport;
import com.dax.walker.engine.definitions.WalkCondition;
import com.dax.walker.engine.pathfinding.BFSMapCache;
import com.dax.walker.engine.utils.RunEnergyManager;
import com.dax.walker.models.PathResult;
import com.dax.walker.models.PathStatus;
import com.dax.walker.models.Point3D;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.ui.Log;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class WalkerEngine implements RenderListener {

    private Map<Position, Teleport> map;
    private WalkCondition walkCondition;
    private RunEnergyManager runEnergyManager;

    // DEBUG PAINT
    private PathResult lastPath;
    private BFSMapCache bfsMapCache;
    private Position playerPosition;

    public WalkerEngine() {
        map = new ConcurrentHashMap<>();
        for (Teleport teleport : Teleport.values()) {
            map.put(teleport.getLocation(), teleport);
        }
        runEnergyManager = new RunEnergyManager();
        walkCondition = () -> {
            runEnergyManager.trigger();
            return false;
        };
    }

    public boolean walk(List<PathResult> list) {
        Game.getEventDispatcher().register(this);
        try {
            List<PathResult> validPaths = validPaths(list);
            PathResult pathResult = getBestPath(validPaths);
            if (pathResult == null) {
                Log.log(Level.WARNING, "DaxWalker", "No valid path found");
                return false;
            }
            lastPath = pathResult;
            Log.log(Level.FINE, "DaxWalker", String.format("Chose path of cost: %d out of %d options.", pathResult.getCost(), validPaths.size()));
            return PathHandler.walk(convert(pathResult.getPath()), walkCondition, 3);
        } finally {
            Game.getEventDispatcher().deregister(this);
        }
    }

    public List<PathResult> validPaths(List<PathResult> list) {
        return list.stream().filter(pathResult -> pathResult.getPathStatus() == PathStatus.SUCCESS).collect(Collectors.toList());
    }

    public PathResult getBestPath(List<PathResult> list) {
        return list.stream().min(Comparator.comparingInt(this::getPathMoveCost)).orElse(null);
    }

    public void setWalkCondition(WalkCondition walkCondition) {
        this.walkCondition = walkCondition;
    }

    public void andWalkCondition(WalkCondition walkCondition) {
        this.walkCondition.and(walkCondition);
    }

    public void orWalkCondition(WalkCondition walkCondition) {
        this.walkCondition.or(walkCondition);
    }

    private List<Position> convert(List<Point3D> list) {
        List<Position> positions = new ArrayList<>();
        for (Point3D point3D : list) {
            positions.add(new Position(point3D.getX(), point3D.getY(), point3D.getZ()));
        }
        return positions;
    }

    private int getPathMoveCost(PathResult pathResult) {
        Teleport teleport = map.get(pathResult.getPath().get(0).toPosition());
        if (teleport == null) return pathResult.getCost();
        return teleport.getMoveCost() + pathResult.getCost();
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        PathResult pathResult = lastPath;

        if (pathResult == null) return;

        if (playerPosition == null || !playerPosition.equals(Players.getLocal().getPosition())) {
            playerPosition = Players.getLocal().getPosition();
            bfsMapCache = new BFSMapCache();
        }

        List<Point3D> path = pathResult.getPath();
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).getZ() != playerPosition.getFloorLevel()) continue;
            if (!bfsMapCache.canReach(path.get(i).toPosition())) continue;
            PositionalDebug.drawArrow(renderEvent.getSource(), path.get(i).toPosition(), path.get(i + 1).toPosition(), new Color(0, 255, 20, 100));
        }

        PositionalDebug.outline(renderEvent.getSource(), PathHandler.furthestTileInPath(pathResult.getPath().stream().map(Point3D::toPosition).collect(Collectors.toList()), 20), new Color(255, 67,0, 140));
    }

}
