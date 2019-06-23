package com.dax.walker.engine.utils;

import org.rspeer.runetek.api.scene.Players;

public class MovementManager {

    private long initial;

    public MovementManager() {
        initial = System.currentTimeMillis();
    }

    public boolean isWalking() {
        return System.currentTimeMillis() - initial < 1600 || Players.getLocal().isMoving();
    }

}
