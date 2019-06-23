package com.dax.walker.models;

public enum  PathStatus {
    UNMAPPED_REGION,
    SUCCESS,
    BLOCKED_END,
    BLOCKED_START,
    EXCEEDED_SEARCH_LIMIT,
    UNREACHABLE,
    NO_WEB_PATH;
}
