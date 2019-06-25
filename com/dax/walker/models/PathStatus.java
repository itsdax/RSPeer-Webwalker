package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public enum PathStatus {
    @DoNotRename
    UNMAPPED_REGION,
    @DoNotRename
    SUCCESS,
    @DoNotRename
    BLOCKED_END,
    @DoNotRename
    BLOCKED_START,
    @DoNotRename
    EXCEEDED_SEARCH_LIMIT,
    @DoNotRename
    UNREACHABLE,
    @DoNotRename
    NO_WEB_PATH;
}
