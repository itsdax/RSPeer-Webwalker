package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DoNotRename
public class PlayerDetails {

    public static PlayerDetails generate() {
        List<IntPair> inventory = Arrays.stream(Inventory.getItems())
                .filter(s -> !s.getName().equals("Members object"))
                .map(rsItem -> new IntPair(rsItem.getId(), rsItem.getStackSize())).collect(Collectors.toList());
        List<IntPair> equipment = Arrays.stream(Equipment.getItems())
                .filter(s -> !s.getName().equals("Members object"))
                .map(rsItem -> new IntPair(rsItem.getId(), rsItem.getStackSize())).collect(Collectors.toList());
        LinkedHashMap<Integer, Integer> map = Game.getClientPreferences().getProperties();
        List<IntPair> settings = Stream.of(
                176, 32, 71, 273, 144, 63, 179, 145, 68, 655, 10, 964, 399, 869, 314, 794, 440, 622, 131, 335, 299,
                896, 671, 810, 17, 11, 347, 302, 111, 116, 482, 307, 165, 150, 425, 365, 1630
        ).filter(map::containsValue).map(value -> new IntPair(value, map.get(value))).distinct().collect(Collectors.toList());
        List<IntPair> varbit = Arrays.stream(new int[]{5087, 5088, 5089, 5090, 4895})
                .mapToObj(value -> new IntPair(value, Varps.get(value))).distinct().collect(Collectors.toList());
        return new PlayerDetails(
                Skills.getLevel(Skill.ATTACK),
                Skills.getLevel(Skill.DEFENCE),
                Skills.getLevel(Skill.STRENGTH),
                Skills.getLevel(Skill.HITPOINTS),
                Skills.getLevel(Skill.RANGED),
                Skills.getLevel(Skill.PRAYER),
                Skills.getLevel(Skill.MAGIC),
                Skills.getLevel(Skill.COOKING),
                Skills.getLevel(Skill.WOODCUTTING),
                Skills.getLevel(Skill.FLETCHING),
                Skills.getLevel(Skill.FISHING),
                Skills.getLevel(Skill.FIREMAKING),
                Skills.getLevel(Skill.CRAFTING),
                Skills.getLevel(Skill.SMITHING),
                Skills.getLevel(Skill.MINING),
                Skills.getLevel(Skill.HERBLORE),
                Skills.getLevel(Skill.AGILITY),
                Skills.getLevel(Skill.THIEVING),
                Skills.getLevel(Skill.SLAYER),
                Skills.getLevel(Skill.FARMING),
                Skills.getLevel(Skill.RUNECRAFTING),
                Skills.getLevel(Skill.HUNTER),
                Skills.getLevel(Skill.CONSTRUCTION),
                settings,
                varbit,
                Worlds.get(Worlds.getCurrent()).isMembers(),
                equipment,
                inventory
        );
    }

    @DoNotRename
    private int attack;

    @DoNotRename
    private int defence;

    @DoNotRename
    private int strength;

    @DoNotRename
    private int hitpoints;

    @DoNotRename
    private int ranged;

    @DoNotRename
    private int prayer;

    @DoNotRename
    private int magic;

    @DoNotRename
    private int cooking;

    @DoNotRename
    private int woodcutting;

    @DoNotRename
    private int fletching;

    @DoNotRename
    private int fishing;

    @DoNotRename
    private int firemaking;

    @DoNotRename
    private int crafting;

    @DoNotRename
    private int smithing;

    @DoNotRename
    private int mining;

    @DoNotRename
    private int herblore;

    @DoNotRename
    private int agility;

    @DoNotRename
    private int thieving;

    @DoNotRename
    private int slayer;

    @DoNotRename
    private int farming;

    @DoNotRename
    private int runecrafting;

    @DoNotRename
    private int hunter;

    @DoNotRename
    private int construction;

    @DoNotRename
    private List<IntPair> setting;

    @DoNotRename
    private List<IntPair> varbit;

    @DoNotRename
    private boolean member;

    @DoNotRename
    private List<IntPair> equipment;

    @DoNotRename
    private List<IntPair> inventory;

    public PlayerDetails() {

    }

    public PlayerDetails(int attack, int defence, int strength, int hitpoints, int ranged, int prayer, int magic, int cooking, int woodcutting, int fletching, int fishing, int firemaking, int crafting, int smithing, int mining, int herblore, int agility, int thieving, int slayer, int farming, int runecrafting, int hunter, int construction, List<IntPair> setting, List<IntPair> varbit, boolean member, List<IntPair> equipment, List<IntPair> inventory) {
        this.attack = attack;
        this.defence = defence;
        this.strength = strength;
        this.hitpoints = hitpoints;
        this.ranged = ranged;
        this.prayer = prayer;
        this.magic = magic;
        this.cooking = cooking;
        this.woodcutting = woodcutting;
        this.fletching = fletching;
        this.fishing = fishing;
        this.firemaking = firemaking;
        this.crafting = crafting;
        this.smithing = smithing;
        this.mining = mining;
        this.herblore = herblore;
        this.agility = agility;
        this.thieving = thieving;
        this.slayer = slayer;
        this.farming = farming;
        this.runecrafting = runecrafting;
        this.hunter = hunter;
        this.construction = construction;
        this.setting = setting;
        this.varbit = varbit;
        this.member = member;
        this.equipment = equipment;
        this.inventory = inventory;
    }


}
