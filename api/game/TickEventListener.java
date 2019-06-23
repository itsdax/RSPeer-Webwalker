package com.dax.api.game;

import com.dax.api.utils.DaxListener;

public interface TickEventListener extends DaxListener<TickEvent> {
    @Override
    void trigger(TickEvent event);
}
