package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;
import org.rspeer.runetek.api.movement.position.Position;

@DoNotRename
public enum RSBank {
    FALADOR_WEST(2946, 3368, 0),
    FALADOR_EAST(3013, 3355, 0),
    EDGEVILLE(3094, 3492, 0),
    GRAND_EXCHANGE(3164, 3487, 0),
    VARROCK_WEST(3185, 3441, 0),
    VARROCK_EAST(3253, 3420, 0),
    AL_KHARID(3269, 3167, 0),
    LUMBRIDGE_TOP(3208, 3220, 2),
    DRAYNOR(3092, 3243, 0),
    SHAYZIEN_BANK(1504, 3615, 0),
    VINERY_BANK(1809, 3566, 0),
    LUNAR_ISLE(2099, 3919, 0),
    BARBARIAN_OUTPOST(2536, 3574, 0),
    GNOME_TREE_BANK_WEST(2442, 3488, 1),
    GNOME_TREE_BANK_SOUTH(2449, 3482, 1),
    MOTHERLOAD(3760, 5666, 0),
    PEST_CONTROL(2667, 2653, 0),
    DIHN_BANK(1640, 3944, 0),
    ZANARIS(2383, 4458, 0),
    CLAN_WARS(3369, 3170, 0),
    DWARF_MINE_BANK(2837, 10207, 0),
    BLAST_FURNACE_BANK(1948, 4957, 0),
    ZEAH_SAND_BANK(1719, 3465, 0),
    GNOME_BANK(2445, 3425, 1),
    ROGUES_DEN(3043, 4973, 1),
    ARCEUUS(1624, 3745, 0),
    BLAST_MINE(1502, 3856, 0),
    LOVAKENGJ(1526, 3739, 0),
    SHAYZIEN(1504, 3615, 0),
    HOSIDIUS(1676, 3568, 0),
    LANDS_END(1512, 3421, 0),
    VINERY(1808, 3570, 0),
    PISCARILIUS(1803, 3790, 0),
    CATHERBY(2808, 3441, 0),
    CAMELOT(2725, 3493, 0),
    DUEL_ARENA(3381, 3268, 0),
    ARDOUGNE_NORTH(2616, 3332, 0),
    ARGOUDNE_SOUTH(2655, 3283, 0),
    CASTLE_WARS(2443, 3083, 0),
    YANILLE(2613, 3093, 0),
    TZHAAR(2446, 5178, 0),
    CANIFIS(3512, 3480, 0),
    SHANTY_PASS(3308, 3120, 0),
    WOODCUTTING_GUILD(1591, 3479, 0),
    SHILO_VILLAGE(2852, 2954, 0),
    SOPHANEM(2799, 5169, 0),
    LUMBRIDGE_BASEMENT(3218, 9623, 0),
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
