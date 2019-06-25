package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public enum WalkState {
    @DoNotRename
    FAILED,
    @DoNotRename
    SUCCESS,
    @DoNotRename
    START_BLOCKED,
    @DoNotRename
    END_BLOCKED,
    @DoNotRename
    RATE_LIMIT,
    @DoNotRename
    ERROR,
}
