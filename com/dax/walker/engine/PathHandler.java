package com.dax.walker.engine;

import com.allatori.annotations.DoNotRename;
import com.dax.walker.engine.definitions.PathHandleState;
import com.dax.walker.engine.definitions.PathLink;
import com.dax.walker.engine.definitions.Teleport;
import com.dax.walker.engine.definitions.WalkCondition;
import com.dax.walker.engine.pathfinding.BFSMapCache;
import com.dax.walker.engine.pathfinding.Region;
import com.dax.walker.engine.utils.RunManager;
import com.dax.walker.engine.utils.ShipHandler;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.ui.Log;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

@DoNotRename
public class PathHandler {

    /**
     * @param path          >= 1 in size
     * @param walkCondition exit condition/ passive actions
     * @param maxRetries       max retries on fail
     * @return true if path completed successfully.
     */
    public static boolean walk(List<Position> path, WalkCondition walkCondition, int maxRetries, List<PathLink> pathLinks) {
        assert Thread.currentThread() instanceof Script;
        Script script = (Script) Thread.currentThread();

        if (!handleTeleports(path)) {
            Log.log(Level.WARNING, "DaxWalker", "Failed to handle teleports...");
            return false;
        }

        int fail = 0;
        Position previous = null;
        while (!script.isStopping() && !isFinishedPathing(path.get(path.size() - 1))) {

            if (script.isPaused()) {
                Time.sleep(500);
                continue;
            }

            Time.sleep(50);
            if (fail >= maxRetries) return false;
            Position next = furthestTileInPath(path, randomDestinationDistance());
            PathHandleState state = handleNextAction(previous, next, path, walkCondition, pathLinks);


            switch (state) {
                case SUCCESS:
                    previous = next;
                    fail = 0;
                    break;

                case FAILED:
                    fail++;
                    previous = next;
                    Time.sleep(2500, 3500);
                    continue;

                case EXIT:
                    return false;
            }
        }

        return true;
    }

    private static PathHandleState handleNextAction(Position previous, Position now, List<Position> path, WalkCondition walkCondition, List<PathLink> pathLinks) {
        if (ShipHandler.isOnShip()) return ShipHandler.getOffBoat() ? PathHandleState.SUCCESS : PathHandleState.FAILED;
        if (now == null) return PathHandleState.FAILED;

        // Disconnected Path
        if (previous != null && previous.equals(now)) {
            Position next = getNextTileInPath(now, path);
            if (next != null) {
                Log.log(Level.FINE, "DaxWalker", String.format("Disconnected path: (%d,%d,%d) -> (%d,%d,%d)",
                        now.getX(), now.getY(), now.getFloorLevel(),
                        next.getX(), next.getY(), next.getFloorLevel()
                ));
                PathHandleState pathHandleState = BrokenPathHandler.handlePathLink(now, next, walkCondition, pathLinks);
                if (pathHandleState != null) return pathHandleState;
                return BrokenPathHandler.handle(now, next, walkCondition);
            }
            Log.log(Level.FINE, "DaxWalker", "Clicking supposed last tile in path...");
            return Movement.setWalkFlagWithConfirm(now) ? PathHandleState.SUCCESS : PathHandleState.FAILED; // Finished Path most likely
        }

        Position destination = new BFSMapCache(now, new Region()).getRandom(2);
        if (destination == null) return PathHandleState.FAILED;

        // Normal Walking
        if (!Movement.setWalkFlagWithConfirm(destination)) return PathHandleState.FAILED;

        AtomicBoolean exitCondition = new AtomicBoolean(false);
        RunManager runManager = new RunManager();
        int distance = randomWaitDistance();
        Time.sleepUntil(() -> {
            if (walkCondition.getAsBoolean()) {
                exitCondition.set(true);
                return true;
            }
            return !runManager.isWalking() || Players.getLocal().getPosition().distance(destination) <= distance;
        }, 15000);
        return exitCondition.get() ? PathHandleState.EXIT : PathHandleState.SUCCESS;
    }

    private static boolean isFinishedPathing(Position destination) {
        if (Players.getLocal().getPosition().equals(destination)) return true;
        Position walkingTo = Movement.getDestination();
        if (walkingTo == null) return false;
        if (walkingTo.equals(destination)) return true;
        return walkingTo.distance(destination) < 5 && new BFSMapCache(walkingTo, new Region()).getMoveCost(destination) <= 2;
    }

    private static Position getNextTileInPath(Position current, List<Position> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).equals(current)) return path.get(i + 1);
        }
        return null;
    }

    public static Position furthestTileInPath(List<Position> path, int limit) {
        Position playerPosition = Players.getLocal().getPosition();
        BFSMapCache bfsMapCache = new BFSMapCache(playerPosition, new Region());
        for (int i = path.size() - 1; i >= 0; i--) {
            if (path.get(i).getFloorLevel() != playerPosition.getFloorLevel()) continue;
            if (path.get(i).distance(playerPosition) <= limit && bfsMapCache.getMoveCost(path.get(i)) <= limit)
                return path.get(i);
        }
        return null;
    }

    private static boolean handleTeleports(List<Position> path) {
        Position startPosition = path.get(0);
        Position playerPosition = Players.getLocal().getPosition();
        for (Teleport teleport : Teleport.values()) {
            if (!teleport.getRequirement().satisfies()) continue;
            if (!teleport.isAtTeleportSpot(playerPosition) && teleport.isAtTeleportSpot(startPosition)) {
                Log.fine("Let use: " + teleport);
                if (!teleport.trigger()) return false;
                if (!Time.sleepUntil(() -> teleport.isAtTeleportSpot(Players.getLocal().getPosition()), 8000)) return false;
                Log.log(Level.FINE, "Teleport", "Using teleport: " + teleport);
                break;
            }
        }
        return true;
    }

    private static int randomWaitDistance() {
        return Random.mid(4, 15);
    }

    private static int randomDestinationDistance() {
        return Random.mid(20, 25);
    }

}
