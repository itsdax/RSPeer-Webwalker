package com.dax.api.game;


import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.ui.Log;

import java.util.Arrays;
import java.util.logging.Level;

public class HerbTarHelper {

    public static final int PESTLE_AND_MOTOR = 233;
    public static final int SWAMP_TAR = 1939;

    public enum Strategy {
        GUAM(249, 199),
        MARRENTILL(201, 201);

        private int leafID;
        private int grimyID;

        Strategy(int leafID, int grimyID) {
            this.leafID = leafID;
            this.grimyID = grimyID;
        }

        public boolean hasMaterials() {
            switch (this) {
                case GUAM:
                case MARRENTILL:
                    if (Inventory.getCount(leafID) != 1) {
                        if (Inventory.getCount(leafID) == 0 && Inventory.getCount(grimyID) > 0) {
                            Arrays.stream(Inventory.getItems(item -> item.getId() == grimyID)).findAny().ifPresent(grimy -> grimy.interact("Clean"));
                            Time.sleep(100, 150);
                        }
                        return false;
                    }
                    return Inventory.getCount(PESTLE_AND_MOTOR) >= 1 && Inventory.getCount(SWAMP_TAR) >= 1;
            }

            return true;
        }

        public boolean trigger() {
            int firstItem = Random.nextInt(1, 6) == 1 ? SWAMP_TAR : leafID;
            int secondItem = firstItem == SWAMP_TAR ? leafID : SWAMP_TAR;

            if (Inventory.use(item -> item.getId() == firstItem, Inventory.getFirst(secondItem))) {
                Time.sleep(50, 100);
                return true;
            }
            if (Inventory.use(item -> item.getId() == firstItem, Inventory.getFirst(secondItem))) {
                Time.sleep(50, 100);
                return true;
            }
            Log.log(Level.WARNING, "HerbTar", "Failed to use herb tar: " + this);
            return false;
        }

    }

    public static boolean hasHerbTarMaterials() {
        for (Strategy tickStrategy : Strategy.values()) {
            if (tickStrategy.hasMaterials()) return true;
        }
        return false;
    }

    public static Strategy getHerbTarStrategy() {
        for (Strategy tickStrategy : Strategy.values()) {
            if (tickStrategy.hasMaterials()) return tickStrategy;
        }
        return null;
    }

    public static boolean herbTar() {
        Strategy tickStrategy = getHerbTarStrategy();
        if (tickStrategy == null) return false;
        return tickStrategy.trigger();
    }

}
