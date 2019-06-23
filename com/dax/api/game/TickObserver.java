package com.dax.api.game;

import com.dax.api.utils.DaxObserver;
import org.rspeer.runetek.api.Game;

public class TickObserver extends DaxObserver<TickEvent, TickEventListener> {

    private long lastCycle;

    public TickObserver() {
        this.lastCycle = Game.getEngineCycle();
    }

    @Override
    public int observe() {
        long current = Game.getEngineCycle();
        if (current - lastCycle > 60) {
            notify(new TickEvent() {});
            this.lastCycle = current;
        }
        return 10;
    }

}
