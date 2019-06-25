package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public enum WalkState {
    FAILED,
    SUCCESS,
    START_BLOCKED,
    END_BLOCKED,
    RATE_LIMIT,
    ERROR,
}
