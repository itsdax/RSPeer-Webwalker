package com.dax.api.walking;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;

public class ShortMovement {

    public static boolean walkTo(Position position) {
        Movement.setWalkFlag(position);
        return Time.sleepUntil(() -> WalkQueue.isWalkingTowards(position), 1200);
    }

}
