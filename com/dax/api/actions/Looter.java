package com.dax.api.actions;

import com.dax.api.walking.WalkQueue;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.ui.Log;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Level;

public class Looter {

    public static boolean lootAndWait(Predicate<Pickable> filter) {
        Pickable[] pickables = Pickables.getLoaded(filter);
        Arrays.sort(pickables, Comparator.comparingDouble(o -> o.getPosition().distance()));
        if (pickables.length <= 0) return false;
        return lootAndWait(pickables[0]);
    }

    public static boolean lootAndWait(Pickable item) {
        final int initial = Inventory.getCount(item.getId());
        item.interact("Take");
        if (!Time.sleepUntil(() -> WalkQueue.getTruePosition().equals(item.getPosition()), 3000)) {
            Log.log(Level.WARNING, "Looter", String.format("Failed to pick up %s", item.getName()));
            return false;
        }
        return Time.sleepUntil(() -> initial < Inventory.getCount(item.getId()), 3600);
    }

}
