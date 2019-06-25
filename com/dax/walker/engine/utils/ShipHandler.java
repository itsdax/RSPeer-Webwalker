package com.dax.walker.engine.utils;

import com.dax.walker.engine.pathfinding.BFSMapCache;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.scene.SceneObjects;

import java.util.Arrays;

public class ShipHandler {

    private ShipHandler() {
    }

    public static boolean isOnShip() {
        return hasShipObjects() && getGangPlank() != null && new BFSMapCache().getAccessibleArea() < 100;
    }

    public static boolean getOffBoat() {
        SceneObject gangplank = getGangPlank();
        if (gangplank == null) return false;
        return gangplank.interact(s -> s.matches("(?i)(walk.a)?cross")) && Time.sleepUntil(() -> !isOnShip(), 5000);
    }

    private static SceneObject getGangPlank() {
        return Arrays.stream(SceneObjects.getLoaded(sceneObject ->
                sceneObject.distance() < 10 && sceneObject.getName().matches("(?i)(gang.?plank)")))
                .findAny()
                .orElse(null);
    }

    private static boolean hasShipObjects() {
        return SceneObjects.getLoaded(sceneObject -> sceneObject.distance() < 10
                && sceneObject.getName().matches("(?i)(ship.s.+|anchor)")).length > 0;
    }

}
