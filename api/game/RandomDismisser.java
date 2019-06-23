package com.dax.api.game;

import com.dax.api.time.Timer;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.PathingEntity;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;

import java.util.Arrays;
import java.util.function.Predicate;

public class RandomDismisser {

    private long lastActive;

    public RandomDismisser() {
        this.lastActive = 0L;
    }


    public void performCheck() {
        if (Timer.since(lastActive) < 8000) return;
        Npc npc = Arrays.stream(Npcs.getLoaded(filter())).findAny().orElse(null);

        if (npc == null) return;

        if (!npc.interact("Dismiss")) {
            return;
        }

        lastActive = System.currentTimeMillis();
        Time.sleep(4000, 7000);
    }


    private static Predicate<Npc> filter() {
        Player player = Players.getLocal();
        return npc -> {
            String[] actions = npc.getActions();
            if (actions == null) return false;
            if (!Arrays.asList(actions).contains("Dismiss")) return false;
            PathingEntity pathingEntity = npc.getTarget();
            if (pathingEntity == null) return false;
            return pathingEntity.getIndex() == player.getIndex();
        };
    }

}
