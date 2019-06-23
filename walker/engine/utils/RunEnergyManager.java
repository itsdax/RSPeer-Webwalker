package com.dax.walker.engine.utils;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;

public class RunEnergyManager {

    private int runActivation;

    public RunEnergyManager() {
        runActivation = getRandomRunTarget();
    }

    public void trigger() {
        if (Movement.isRunEnabled()) return;
        if (Movement.getRunEnergy() >= runActivation) {
            Movement.toggleRun(true);
            Time.sleep(1000, 2000);
            runActivation = getRandomRunTarget();
        }
    }

    private int getRandomRunTarget() {
        return Random.nextInt(1, 30);
    }

}
