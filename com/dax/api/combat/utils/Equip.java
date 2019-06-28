package com.dax.api.combat.utils;

import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Magic;

import java.util.regex.Pattern;

public class Equip {

    public static boolean hasLongRangeEquip() {
        if (Equipment.contains(Pattern.compile("(?i).*(short|long|cross|crystal).*bow.*"))) {
            return Equipment.contains(Pattern.compile("(?i).*(bolt|arrow).*"));
        }
        return Magic.Autocast.isEnabled();
    }

}
