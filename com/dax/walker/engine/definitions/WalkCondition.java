package com.dax.walker.engine.definitions;

import java.util.function.BooleanSupplier;

/**
 * At any time this method returns true, Walker will exit out
 * and return false as it has not completely traversed the path.
 */
public interface WalkCondition extends BooleanSupplier {

    default WalkCondition and(WalkCondition walkCondition) {
        return () -> WalkCondition.this.getAsBoolean() && walkCondition.getAsBoolean();
    }

    default WalkCondition or(WalkCondition walkCondition) {
        return () -> WalkCondition.this.getAsBoolean() || walkCondition.getAsBoolean();
    }

}
