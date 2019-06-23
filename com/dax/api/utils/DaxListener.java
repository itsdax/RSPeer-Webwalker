package com.dax.api.utils;

public interface DaxListener <E extends DaxEvent> {
    void trigger(E event);
}
