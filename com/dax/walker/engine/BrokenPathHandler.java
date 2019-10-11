package com.dax.walker.engine;

import com.allatori.annotations.DoNotRename;
import com.dax.walker.engine.definitions.PathHandleState;
import com.dax.walker.engine.definitions.PathLink;
import com.dax.walker.engine.definitions.PopUpInterfaces;
import com.dax.walker.engine.definitions.WalkCondition;
import com.dax.walker.engine.pathfinding.BFSMapCache;
import com.dax.walker.engine.utils.RunManager;
import org.rspeer.runetek.adapter.Interactable;
import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.ui.Log;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DoNotRename
public class BrokenPathHandler {

    public enum NextMove {
        UNDERGROUND(Pattern.compile("(?i)(climb|jump|walk).down"),
                generateCase(sceneObject -> sceneObject.getName().matches("(?i)(trap.?door|manhole)"), Pattern.compile("(?i)Open|(Climb.down)"))
        ),
        FLOOR_UNDER(Pattern.compile("(?i)(climb|jump|walk).down"),
                generateCase(sceneObject -> sceneObject.getName().matches("(?i)(trap.?door|manhole)"), Pattern.compile("(?i)Open|(Climb.down)"))
        ),
        FLOOR_ABOVE(Pattern.compile("(?i)(pass|climb|jump|walk).(up|through)")),
        SAME_FLOOR(Pattern.compile("(Exit|Use(?i-m)|pass|(walk|jump|climb).(across|over|under|into)|(open|push|enter)|(.+.through)|cross|board|mine)"));

        private Pattern pattern;
        private DestinationStateSpecialCase[] specialCases;

        NextMove(Pattern pattern, DestinationStateSpecialCase... specialCases) {
            this.pattern = pattern;
            this.specialCases = specialCases;
        }

        public boolean objectSatisfies(SceneObject sceneObject) {
            for (DestinationStateSpecialCase specialCase : specialCases) {
                if (specialCase.satisfies(sceneObject)) return true;
            }
            if (sceneObject.getName().matches("chest.*")) return false;
            return containsAction(sceneObject, pattern);
        }

        public boolean handle(SceneObject sceneObject) {
            for (DestinationStateSpecialCase specialCase : specialCases) {
                if (specialCase.satisfies(sceneObject)) return specialCase.handle(sceneObject);
            }
            return sceneObject.interact(s -> pattern.matcher(s).matches());
        }

        public boolean handle() {
            SceneObject sceneObject = Arrays.stream(SceneObjects.getLoaded(this::objectSatisfies))
                    .min(Comparator.comparingDouble(Positionable::distance))
                    .orElse(null);
            return sceneObject != null && handle(sceneObject);
        }

    }

    private static DestinationStateSpecialCase generateCase(Predicate<SceneObject> predicate, Pattern pattern) {
        return new DestinationStateSpecialCase() {
            @Override
            public boolean satisfies(SceneObject sceneObject) {
                return predicate.test(sceneObject);
            }

            @Override
            public Pattern getPattern() {
                return pattern;
            }
        };
    }

    private interface DestinationStateSpecialCase {
        boolean satisfies(SceneObject sceneObject);

        Pattern getPattern();

        default boolean handle(SceneObject sceneObject) {
            return sceneObject.interact(s -> getPattern().matcher(s).matches());
        }
    }

    public static PathHandleState handle(Position start, Position end, WalkCondition walkCondition) {
        SceneObject sceneObject = getBlockingObject(start, end);
        if (sceneObject != null) return handleObject(start, end, sceneObject, walkCondition);

        Log.severe(String.format(
                "No PathLink handler for (%d, %d, %d)->(%d, %d, %d)",
                start.getX(), start.getY(), start.getFloorLevel(),
                end.getX(), end.getY(), end.getFloorLevel()
        ));
        return PathHandleState.FAILED;
    }

    public static PathHandleState handlePathLink(Position start, Position end, WalkCondition walkCondition, List<PathLink> pathLinks) {
        PathLink pathLink = pathLinks.stream()
                .filter(link -> link.getStart().equals(start) && link.getEnd().equals(end))
                .findAny()
                .orElse(null);
        if (pathLink != null) return pathLink.handle(walkCondition);
        return null;
    }

    private static PathHandleState handleObject(Position start, Position end, SceneObject sceneObject, WalkCondition walkCondition) {
        if (sceneObject.getName().matches("(?i)Gate of (.+)"))
            return handleStrongHoldDoor(start, end, sceneObject, walkCondition);

        Log.log(Level.FINE, "DaxWalker", "Handling " + sceneObject.getName() + "->" + Arrays.asList(sceneObject.getActions()));
        if (!determine(start, end).handle(sceneObject)) return PathHandleState.FAILED;

        RunManager runManager = new RunManager();
        AtomicBoolean exitCondition = new AtomicBoolean(false);
        if (!Time.sleepUntil(() -> {
            if (!runManager.isWalking()) {
                Time.sleepUntil(() -> PopUpInterfaces.resolve() || end.distance(Players.getLocal()) <= 2 || new BFSMapCache().canReach(end), Players.getLocal().isAnimating() ? Random.mid(1200, 2000) : Random.mid(3500, 4500));
                return true;
            }
            if (walkCondition.getAsBoolean()) {
                exitCondition.set(true);
                return true;
            }
            return false;
        }, 15000)) {
            return PathHandleState.FAILED;
        }
        PopUpInterfaces.resolve();
        if (exitCondition.get()) return PathHandleState.EXIT;
        if (Dialog.isOpen())
            return EntityHandler.handleConversation() ? PathHandleState.SUCCESS : PathHandleState.FAILED;
        return PathHandleState.SUCCESS;

    }

    private static PathHandleState handleStrongHoldDoor(Position start, Position end, SceneObject sceneObject, WalkCondition walkCondition) {
        if (!determine(start, end).handle(sceneObject)) return PathHandleState.FAILED;

        RunManager runManager = new RunManager();
        AtomicBoolean exitCondition = new AtomicBoolean(false);
        if (!Time.sleepUntil(() -> {
            if (!runManager.isWalking()) {
                return true;
            }
            if (walkCondition.getAsBoolean()) {
                exitCondition.set(true);
                return true;
            }
            return false;
        }, 15000)) {
            return PathHandleState.FAILED;
        }
        if (exitCondition.get()) return PathHandleState.EXIT;
        if (!Dialog.isOpen()) return PathHandleState.FAILED;
        return EntityHandler.handleConversation() ? PathHandleState.SUCCESS : PathHandleState.FAILED;
    }

    private static SceneObject getBlockingObject(Position start, Position end) {
        NextMove state = determine(start, end);
        return Arrays.stream(SceneObjects.getLoaded(
                sceneObject -> sceneObject.getPosition().distance(start) <= 5 && state.objectSatisfies(sceneObject)))
                .min(Comparator.comparingDouble(o -> o.distance(start)))
                .orElse(null);
    }

    private static boolean containsAction(Interactable interactable, Pattern regex) {
        String[] actions = interactable.getActions();
        if (actions == null) return false;
        return interactable.containsAction(s -> regex.matcher(s).matches());
    }

    private static NextMove determine(Position start, Position end) {
        if (start.getFloorLevel() < end.getFloorLevel() || isStrongHoldUp(start, end)) return NextMove.FLOOR_ABOVE;
        if (isStrongHold(start) && end.getY() < 3500) return NextMove.FLOOR_ABOVE;
        if (start.getY() > 5000 && end.getY() < 5000) return NextMove.FLOOR_ABOVE;
        if (start.getY() < 5000 && end.getY() > 5000) return NextMove.UNDERGROUND;
        if (start.getFloorLevel() > end.getFloorLevel() || isStrongHoldDown(start, end)) return NextMove.FLOOR_UNDER;
        return NextMove.SAME_FLOOR;
    }

    private static boolean isStrongHold(Position position) {
        return position.getX() >= 1850 && position.getX() <= 2380 && position.getY() >= 5175 && position.getY() <= 5317;
    }

    private static boolean isStrongHoldUp(Position start, Position end) {
        return isStrongHold(start) && isStrongHold(end) && Math.abs(end.getX() - start.getX()) > 52 && start.getX() > end.getX();
    }

    private static boolean isStrongHoldDown(Position start, Position end) {
        return isStrongHold(start) && isStrongHold(end) && Math.abs(end.getX() - start.getX()) > 52 && start.getX() < end.getX();
    }

}
