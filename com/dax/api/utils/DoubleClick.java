package com.dax.api.utils;

import org.rspeer.runetek.adapter.Interactable;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;

public class DoubleClick {

    public static boolean interract(Interactable interactable, String option) {
        boolean result = interactable.interact(option);
        if (shouldDoubleClick()) {
            Time.sleep(Random.mid(50, 125));
            result = interactable.interact(option);
        }
        return result;
    }


    private static boolean shouldDoubleClick() {
        if ((System.currentTimeMillis() & 1 << 18) > 0) {
            return Random.nextInt(1, 10) == 1; //Pseudo fatigue every 2^17 ms or ~2 minutes
        }
        return Random.nextInt(1, 2) == 1;
    }

}
