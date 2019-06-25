package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public enum  PathStatus {
    UNMAPPED_REGION,
    SUCCESS,
    BLOCKED_END,
    BLOCKED_START,
    EXCEEDED_SEARCH_LIMIT,
    UNREACHABLE,
    NO_WEB_PATH;
}
