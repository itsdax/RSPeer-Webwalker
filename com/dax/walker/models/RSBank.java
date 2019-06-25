package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;
import org.rspeer.runetek.api.movement.position.Position;

@DoNotRename
public enum RSBank {
    @DoNotRename
    FALADOR_WEST(2946, 3368, 0),
    @DoNotRename
    FALADOR_EAST(3013, 3355, 0),
    @DoNotRename
    EDGEVILLE(3094, 3492, 0),
    @DoNotRename
    GRAND_EXCHANGE(3164, 3487, 0),
    @DoNotRename
    VARROCK_WEST(3185, 3441, 0),
    @DoNotRename
    VARROCK_EAST(3253, 3420, 0),
    @DoNotRename
    AL_KHARID(3269, 3167, 0),
    @DoNotRename
    LUMBRIDGE_TOP(3208, 3220, 2),
    @DoNotRename
    DRAYNOR(3092, 3243, 0),
    @DoNotRename
    SHAYZIEN_BANK(1504, 3615, 0),
    @DoNotRename
    VINERY_BANK(1809, 3566, 0),
    @DoNotRename
    LUNAR_ISLE(2099, 3919, 0),
    @DoNotRename
    BARBARIAN_OUTPOST(2536, 3574, 0),
    @DoNotRename
    GNOME_TREE_BANK_WEST(2442, 3488, 1),
    @DoNotRename
    GNOME_TREE_BANK_SOUTH(2449, 3482, 1),
    @DoNotRename
    MOTHERLOAD(3760, 5666, 0),
    @DoNotRename
    PEST_CONTROL(2667, 2653, 0),
    @DoNotRename
    DIHN_BANK(1640, 3944, 0),
    @DoNotRename
    ZANARIS(2383, 4458, 0),
    @DoNotRename
    CLAN_WARS(3369, 3170, 0),
    @DoNotRename
    DWARF_MINE_BANK(2837, 10207, 0),
    @DoNotRename
    BLAST_FURNACE_BANK(1948, 4957, 0),
    @DoNotRename
    ZEAH_SAND_BANK(1719, 3465, 0),
    @DoNotRename
    GNOME_BANK(2445, 3425, 1),
    @DoNotRename
    ROGUES_DEN(3043, 4973, 1),
    @DoNotRename
    ARCEUUS(1624, 3745, 0),
    @DoNotRename
    BLAST_MINE(1502, 3856, 0),
    @DoNotRename
    LOVAKENGJ(1526, 3739, 0),
    @DoNotRename
    SHAYZIEN(1504, 3615, 0),
    @DoNotRename
    HOSIDIUS(1676, 3568, 0),
    @DoNotRename
    LANDS_END(1512, 3421, 0),
    @DoNotRename
    VINERY(1808, 3570, 0),
    @DoNotRename
    PISCARILIUS(1803, 3790, 0),
    @DoNotRename
    CATHERBY(2808, 3441, 0),
    @DoNotRename
    CAMELOT(2725, 3493, 0),
    @DoNotRename
    DUEL_ARENA(3381, 3268, 0),
    @DoNotRename
    ARDOUGNE_NORTH(2616, 3332, 0),
    @DoNotRename
    ARGOUDNE_SOUTH(2655, 3283, 0),
    @DoNotRename
    CASTLE_WARS(2443, 3083, 0),
    @DoNotRename
    YANILLE(2613, 3093, 0),
    @DoNotRename
    TZHAAR(2446, 5178, 0),
    @DoNotRename
    CANIFIS(3512, 3480, 0),
    @DoNotRename
    SHANTY_PASS(3308, 3120, 0),
    @DoNotRename
    WOODCUTTING_GUILD(1591, 3479, 0),
    @DoNotRename
    SHILO_VILLAGE(2852, 2954, 0),
    @DoNotRename
    SOPHANEM(2799, 5169, 0),
    @DoNotRename
    LUMBRIDGE_BASEMENT(3218, 9623, 0),
    @DoNotRename
    FISHING_GUILD(2586, 3420, 0);


    private int x, y, z;

    RSBank(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D getPoint3D() {
        return new Point3D(x, y, z);
    }

    public Position getPosition() {
        return new Position(x, y, z);
    }

}
