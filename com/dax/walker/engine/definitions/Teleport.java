package com.dax.walker.engine.definitions;

import com.dax.walker.models.Requirement;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Magic;
import org.rspeer.runetek.api.component.tab.Spell;
import org.rspeer.runetek.api.movement.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public enum Teleport {

    VARROCK_TELEPORT(
            35, new Position(3212, 3424, 0),
            () -> Magic.canCast(Spell.Modern.VARROCK_TELEPORT),
            () -> Magic.cast(Spell.Modern.VARROCK_TELEPORT)
    ),

    VARROCK_TELEPORT_TAB(
            35, new Position(3212, 3424, 0),
            () -> Inventory.getCount(true, 8007) > 0,
            () -> Inventory.getFirst(8007).interact("Break")
    ),

    LUMBRIDGE_TELEPORT(
            35, new Position(3225, 3219, 0),
            () -> Magic.canCast(Spell.Modern.LUMBRIDGE_TELEPORT),
            () -> Magic.cast(Spell.Modern.LUMBRIDGE_TELEPORT)
    ),

    LUMBRIDGE_TELEPORT_TAB(
            35, new Position(3225, 3219, 0),
            () -> Inventory.getCount(true, 8008) > 0,
            () -> Inventory.getFirst(8008).interact("Break")
    ),

    FALADOR_TELEPORT(
            35, new Position(2966, 3379, 0),
            () -> Magic.canCast(Spell.Modern.FALADOR_TELEPORT),
            () -> Magic.cast(Spell.Modern.FALADOR_TELEPORT)
    ),

    FALADOR_TELEPORT_TAB(
            35, new Position(2966, 3379, 0),
            () -> Inventory.getCount(true, 8009) > 0,
            () -> Inventory.getFirst(8009).interact("Break")
    ),

    CAMELOT_TELEPORT_TAB(
            35, new Position(2757, 3479, 0),
            () -> Inventory.getCount(true, 8010) > 0,
            () -> Inventory.getFirst(8010).interact("Break")
    ),

    PISCATORIS_TELEPORT(
            35, new Position(2339, 3649, 0),
            () -> Inventory.getCount(true, 12408) > 0,
            () -> Inventory.getFirst(12408).interact("Teleport")
    ),

    WATSON_TELEPORT(
            35, new Position(1645, 3579, 0),
            () -> Inventory.getCount(true, 23387) > 0,
            () -> Inventory.getFirst(23387).interact("Teleport")
    ),

    RING_OF_WEALTH_GRAND_EXCHANGE(
            35, new Position(3161, 3478, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.RING_OF_WEALTH_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.RING_OF_WEALTH_MATCHER, Pattern.compile("(?i)Grand Exchange"))
    ),

    RING_OF_WEALTH_FALADOR(
            35, new Position(2994, 3377, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.RING_OF_WEALTH_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.RING_OF_WEALTH_MATCHER, Pattern.compile("(?i)falador.*"))
    ),

    RING_OF_DUELING_DUEL_ARENA (
            35, new Position(3313, 3233, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.RING_OF_DUELING_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.RING_OF_DUELING_MATCHER, Pattern.compile("(?i).*duel arena.*"))
    ),

    RING_OF_DUELING_CASTLE_WARS (
            35, new Position(2440, 3090, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.RING_OF_DUELING_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.RING_OF_DUELING_MATCHER, Pattern.compile("(?i).*Castle Wars.*"))
    ),

    RING_OF_DUELING_CLAN_WARS (
            35, new Position(3388, 3161, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.RING_OF_DUELING_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.RING_OF_DUELING_MATCHER, Pattern.compile("(?i).*Clan Wars.*"))
    ),

    NECKLACE_OF_PASSAGE_WIZARD_TOWER (
            35, new Position(3113, 3179, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.NECKLACE_OF_PASSAGE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.NECKLACE_OF_PASSAGE_MATCHER, Pattern.compile("(?i).*wizard.+tower.*"))
    ),

    NECKLACE_OF_PASSAGE_OUTPOST (
            35, new Position(2430, 3347, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.NECKLACE_OF_PASSAGE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.NECKLACE_OF_PASSAGE_MATCHER, Pattern.compile("(?i).*the.+outpost.*"))
    ),

    NECKLACE_OF_PASSAGE_EYRIE (
            35, new Position(3406, 3156, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.NECKLACE_OF_PASSAGE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.NECKLACE_OF_PASSAGE_MATCHER, Pattern.compile("(?i).*eagl.+eyrie.*"))
    ),

    COMBAT_BRACE_WARRIORS_GUILD (
            35, new Position(2882, 3550, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.COMBAT_BRACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.COMBAT_BRACE_MATCHER, Pattern.compile("(?i).*warrior.+guild.*"))
    ),

    COMBAT_BRACE_CHAMPIONS_GUILD (
            35, new Position(3190, 3366, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.COMBAT_BRACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.COMBAT_BRACE_MATCHER, Pattern.compile("(?i).*champion.+guild.*"))
    ),

    COMBAT_BRACE_MONASTRY (
            35, new Position(3053, 3486, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.COMBAT_BRACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.COMBAT_BRACE_MATCHER, Pattern.compile("(?i).*monastery.*"))
    ),

    COMBAT_BRACE_RANGE_GUILD (
            35, new Position(2656, 3442, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.COMBAT_BRACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.COMBAT_BRACE_MATCHER, Pattern.compile("(?i).*rang.+guild.*"))
    ),

    GAMES_NECK_BURTHORPE (
            35, new Position(2897, 3551, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.GAMES_NECKLACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GAMES_NECKLACE_MATCHER, Pattern.compile("(?i).*burthorpe.*"))
    ),

    GAMES_NECK_BARBARIAN_OUTPOST (
            35, new Position(2520, 3570, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.GAMES_NECKLACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GAMES_NECKLACE_MATCHER, Pattern.compile("(?i).*barbarian.*"))
    ),

    GAMES_NECK_CORPREAL (
            35, new Position(2965, 4832, 2),
            () -> WearableItemTeleport.has(WearableItemTeleport.GAMES_NECKLACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GAMES_NECKLACE_MATCHER, Pattern.compile("(?i).*corpreal.*"))
    ),

    GAMES_NECK_WINTER (
            35, new Position(1623, 3937, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.GAMES_NECKLACE_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GAMES_NECKLACE_MATCHER, Pattern.compile("(?i).*wintertodt.*"))
    ),

    GLORY_EDGE (
            35, new Position(3087, 3496, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.GLORY_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GLORY_MATCHER, Pattern.compile("(?i).*edgeville.*"))
    ),

    GLORY_KARAMJA (
            35, new Position(2918, 3176, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.GLORY_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GLORY_MATCHER, Pattern.compile("(?i).*karamja.*"))
    ),

    GLORY_DRAYNOR (
            35, new Position(3105, 3251, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.GLORY_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GLORY_MATCHER, Pattern.compile("(?i).*draynor.*"))
    ),

    GLORY_AL_KHARID (
            35, new Position(3293, 3163, 0),
            () -> WearableItemTeleport.has(WearableItemTeleport.GLORY_MATCHER),
            () -> WearableItemTeleport.teleport(WearableItemTeleport.GLORY_MATCHER, Pattern.compile("(?i).*al kharid.*"))
    ),

    ;

    private int moveCost;
    private Position location;
    private Requirement requirement;
    private Action action;

    Teleport(int moveCost, Position location, Requirement requirement, Action action) {
        this.moveCost = moveCost;
        this.location = location;
        this.requirement = requirement;
        this.action = action;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public Position getLocation() {
        return location;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public boolean trigger() {
        return this.action.trigger();
    }

    public boolean isAtTeleportSpot(Position position) {
        return position.distance(location) < 15;
    }

    public static List<Position> getValidStartingPositions() {
        List<Position> positions = new ArrayList<>();
        for (Teleport teleport : values()) {
            if (!teleport.requirement.satisfies()) continue;
            positions.add(teleport.location);
        }
        return positions;
    }

    private interface Action {
        boolean trigger();
    }

}

