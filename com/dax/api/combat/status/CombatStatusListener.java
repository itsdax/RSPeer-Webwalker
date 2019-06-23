package com.dax.api.combat.status;

import com.dax.api.utils.DaxListener;

public interface CombatStatusListener extends DaxListener<AttackEvent> {
    @Override
    void trigger(AttackEvent event);
}
