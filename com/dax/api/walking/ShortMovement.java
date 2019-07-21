package com.dax.api.walking;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;

public class ShortMovement {

    public static boolean walkTo(Position position) {
        Movement.setWalkFlag(position);
        if (shouldDoubleClick()) {
            Time.sleep(Random.mid(75, 150));
            Movement.setWalkFlag(position);
        }
        return Time.sleepUntil(() -> WalkQueue.isWalkingTowards(position), 1200);
    }


    private static boolean shouldDoubleClick() {
        if ((System.currentTimeMillis() & 1 << 18) > 0) {
            return Random.nextInt(1, 10) == 1; //Pseudo fatigue every 2^17 ms or ~2 minutes
        }
        return Random.nextInt(1, 3) == 1;
    }

}
