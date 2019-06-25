package com.dax.walker.engine.definitions;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;

import java.util.regex.Pattern;

public class WearableItemTeleport {

    public static final Pattern RING_OF_WEALTH_MATCHER = Pattern.compile("(?i)ring of wealth.?\\(.+");
    public static final Pattern RING_OF_DUELING_MATCHER = Pattern.compile("(?i)ring of dueling.?\\(.+");
    public static final Pattern NECKLACE_OF_PASSAGE_MATCHER = Pattern.compile("(?i)necklace of passage.?\\(.+");
    public static final Pattern COMBAT_BRACE_MATCHER = Pattern.compile("(?i)combat brace.+\\(.+");
    public static final Pattern GAMES_NECKLACE_MATCHER = Pattern.compile("(?i)game.+neck.+\\(.+");
    public static final Pattern GLORY_MATCHER = Pattern.compile("(?i).+glory.*\\(.+");


    private WearableItemTeleport() {

    }

    public static boolean has(Pattern itemMatcher) {
        return Inventory.contains(item -> itemMatcher.matcher(item.getName()).matches() && !item.isNoted())
                || Equipment.contains(item -> itemMatcher.matcher(item.getName()).matches());
    }

    public static boolean teleport(Pattern itemMatcher, Pattern option) {
        if (teleportEquipment(itemMatcher, option)) return true;
        Item inventoryItem = Inventory.getFirst(item -> itemMatcher.matcher(item.getName()).matches() && !item.isNoted());
        if (inventoryItem == null) return false;
        if (!inventoryItem.interact(s -> option.matcher(s).matches()) && !inventoryItem.interact("Rub")) return false;
        if (!Time.sleepUntil(Dialog::isOpen, 5000)) return false;
        return Dialog.process(s -> option.matcher(s).matches());
    }


    public static boolean teleportEquipment(Pattern itemMatcher, Pattern option) {
        return Equipment.interact(item -> itemMatcher.matcher(item.getName()).matches(), s -> option.matcher(s).matches());
    }

}
