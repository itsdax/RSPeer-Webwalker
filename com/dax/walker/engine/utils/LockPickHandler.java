package com.dax.walker.engine.utils;

import com.dax.walker.engine.BrokenPathHandler;
import com.dax.walker.engine.definitions.PathHandleState;
import com.dax.walker.engine.definitions.WalkCondition;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class LockPickHandler {

    private static final Pattern PICK_LOCK_ACTION = Pattern.compile("(?i)pick.*lock");

    private static final Position HAM_TRAPDOOR = new Position(3166, 3251, 0);

    public static PathHandleState handle(Position position, Position destination, WalkCondition walkCondition) {
        if (position.equals(HAM_TRAPDOOR)) return hamHideOutHandler(position, destination, walkCondition);
        return pickLock(position, destination, walkCondition);
    }

    private static SceneObject getPickableObject(Position position) {
        return Arrays.stream(SceneObjects.getLoaded(sceneObject -> sceneObject.containsAction(s -> s.matches("(?i)pick.*lock"))))
                .min(Comparator.comparingDouble(o -> o.distance(position)))
                .orElse(null);
    }

    private static PathHandleState pickLock(Position position, Position destination, WalkCondition walkCondition) {
        SceneObject sceneObject = getPickableObject(position);
        if (sceneObject == null) return PathHandleState.FAILED;

        // Pick the lock
        if (!sceneObject.interact(s -> PICK_LOCK_ACTION.matcher(s).matches())) return PathHandleState.FAILED;
        AtomicBoolean exitCondition = new AtomicBoolean(false);
        if (Time.sleepUntil(() -> {
            if (walkCondition.getAsBoolean()) {
                exitCondition.set(true);
                return true;
            }
            return destination.equals(Players.getLocal().getPosition());
        }, Random.nextInt(800, 2000)) && exitCondition.get()) {
            return PathHandleState.EXIT;
        }
        return PathHandleState.SUCCESS; // Multiple attempts required usually.
    }

    private static PathHandleState hamHideOutHandler(Position position, Position destination, WalkCondition walkCondition) {
        SceneObject sceneObject = getPickableObject(position);

        // Lock already picked
        if (sceneObject == null) return BrokenPathHandler.handle(position, destination, walkCondition);

        // Pick the lock
        if (!sceneObject.interact(s -> PICK_LOCK_ACTION.matcher(s).matches())) return PathHandleState.FAILED;
        AtomicBoolean exitCondition = new AtomicBoolean(false);
        if (Time.sleepUntil(() -> {
            if (walkCondition.getAsBoolean()) {
                exitCondition.set(true);
                return true;
            }
            return destination.equals(Players.getLocal().getPosition()) || getPickableObject(position) == null;
        }, 15000) && exitCondition.get()) {
            return PathHandleState.EXIT;
        }
        return destination.equals(Players.getLocal().getPosition()) ? PathHandleState.SUCCESS : PathHandleState.FAILED;
    }

}
