package com.dax.walker.engine.utils;

import org.rspeer.runetek.api.scene.Players;

public class RunManager {

    private long initial;

    public RunManager() {
        initial = System.currentTimeMillis();
    }

    public boolean isWalking() {
        return System.currentTimeMillis() - initial < 1300 || Players.getLocal().isMoving();
    }

}
