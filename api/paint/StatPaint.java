package com.dax.api.paint;

import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.event.listeners.SkillListener;
import org.rspeer.runetek.event.types.SkillEvent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.dax.api.paint.PaintUtils.timeToString;

public class StatPaint implements SkillListener {

    private static final String DIVIDER = "  |  ";

    private static final int OFFSET_Y = 14;

    private Map<Skill, Long> map;
    private Lock lock;
    private long startTime;

    private final int x;
    private final int y;
    private final int width;

    private DecimalFormat perHourFormatter;
    private DecimalFormat totalExpFormatter;

    private PaintStyle paintStyle;
    private Map<Skill, Color> colorMap;

    public StatPaint(PaintStyle paintStyle) {
        this.map = new EnumMap<>(Skill.class);
        this.lock = new ReentrantLock();
        this.startTime = System.currentTimeMillis();
        this.x = 10;
        this.y = 342;
        this.width = 490;
        this.perHourFormatter = new DecimalFormat("#,###");
        this.totalExpFormatter = new DecimalFormat("#,##0.00");
        this.paintStyle = paintStyle;
        this.colorMap = new EnumMap<>(Skill.class);
        addColors();
        Game.getEventDispatcher().register(this);
    }

    public void end() {
        Game.getEventDispatcher().deregister(this);
    }


    public void draw(Graphics graphics) {
        lock.lock();
        long timeElapsed = System.currentTimeMillis() - startTime;

        Graphics2D g = (Graphics2D) graphics;
        PaintUtils.drawDebug(g, paintStyle.getStringText(), paintStyle.getNumberText(), paintStyle, "Timer", timeToString(timeElapsed), x - 5, 332);

        int startY = y + OFFSET_Y;
        for (Map.Entry<Skill, Long> entry : map.entrySet()) {

            graphics.setColor(paintStyle.getFill());
            graphics.fillRect(x - 4, startY - OFFSET_Y + 2, width, OFFSET_Y);

            graphics.setColor(colorMap.get(entry.getKey()));
            graphics.fillRect(x - 4, startY - OFFSET_Y + 2, (int) (width * percentToNextLevel(entry.getKey())), OFFSET_Y);


            graphics.setColor(Color.BLACK);
            graphics.drawRect(x - 4, startY - OFFSET_Y + 2, width, OFFSET_Y);



            AttributedString bg = getString(entry.getKey(), timeElapsed, entry.getValue());
            bg.addAttribute(TextAttribute.FOREGROUND, paintStyle.getStrongFill(), 0, toString(bg).length());
            graphics.drawString(bg.getIterator(), x + 1, startY + 1);

            AttributedString as = getString(entry.getKey(), timeElapsed, entry.getValue());
            graphics.drawString(as.getIterator(), x, startY);

            startY += OFFSET_Y;
        }
        lock.unlock();
    }

    private double percentToNextLevel(Skill skill) {
        int level = Skills.getLevelAt(Skills.getExperience(skill));
        int nextLevelExp = Skills.getExperienceAt(level + 1);
        int currentLevelExp = Skills.getExperienceAt(level);
        int currentExp = Skills.getExperience(skill);
        return Math.min(1, (currentExp - currentLevelExp) / (double) Math.max(1, (nextLevelExp - currentLevelExp)));
    }

    private long amountPerHour(long amount, long time) {
        double seconds = time / 1000D;
        double minutes = seconds / 60D;
        double hours = minutes / 60D;
        return (long) (amount / Math.max(hours, 0.00001));
    }

    private String timeToLevel(long exp, long expPerHour) {
        double time = exp / (double) expPerHour;
        return timeToString((long) (time * 60 * 60 * 1000));

    }

    private AttributedString getString(Skill skill, long time, long exp) {
        String skillIndicator = skill + ": ";
        String level = Skills.getLevel(skill) + "";
        String totalExp = totalExpFormatter.format(exp / 1000D);
        String totalExpUnits = "k xp";
        String perHour = perHourFormatter.format(amountPerHour(exp, time));
        String perHourUnits = "xp/hr";
        String timeToLevelUnits = "TTL: ";
        String timeToLevel = timeToLevel(Skills.getExperienceToNextLevel(skill), amountPerHour(exp, time));

        String output = String.format("%s%s%s%s%s%s%s%s%s%s%s", skillIndicator, level, DIVIDER, totalExp, totalExpUnits, DIVIDER, perHour, perHourUnits, DIVIDER, timeToLevelUnits, timeToLevel);

        AttributedString as = new AttributedString(output);

        as.addAttribute(TextAttribute.FONT, FontSelector.getInstance().getFontSmallItalics(), 0, output.length());
        int index = 0;
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getStringText(), index, index += (skillIndicator.length()));
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getNumberText(), index, index += (level.length()));

        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getInfoText(), index, index += (DIVIDER.length()));
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getNumberText(), index, index += (totalExp.length()));
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getInfoText(), index, index += (totalExpUnits.length()));

        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getInfoText(), index, index += (DIVIDER.length()));
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getNumberText(), index, index += (perHour.length()));
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getInfoText(), index, index += (perHourUnits.length()));

        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getInfoText(), index, index += (DIVIDER.length()));
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getInfoText(), index, index += (timeToLevelUnits.length()));
        as.addAttribute(TextAttribute.FOREGROUND, paintStyle.getNumberText(), index, index += (timeToLevel.length()));

        return as;
    }

    private String toString(AttributedString s) {
        AttributedCharacterIterator x = s.getIterator();
        StringBuilder a = new StringBuilder();
        a.append(x.current());
        while (x.getIndex() < x.getEndIndex())
            a.append(x.next());
        a = new StringBuilder(a.substring(0, a.length() - 1));
        return a.toString();
    }

    private void addColors() {
        colorMap.put(Skill.FIREMAKING, new Color(255, 74, 61, 150));
        colorMap.put(Skill.FARMING, new Color(1, 255, 31, 150));
        colorMap.put(Skill.CONSTRUCTION, new Color(255, 145, 67, 150));
        colorMap.put(Skill.STRENGTH, new Color(0, 194, 28, 150));
        colorMap.put(Skill.MINING, new Color(138, 106, 21, 150));
        colorMap.put(Skill.SMITHING, new Color(138, 85, 59, 150));
        colorMap.put(Skill.THIEVING, new Color(87, 19, 138, 150));
        colorMap.put(Skill.ATTACK, new Color(255, 0, 10, 150));
        colorMap.put(Skill.AGILITY, new Color(4, 67, 255, 150));
        colorMap.put(Skill.FISHING, new Color(46, 194, 255, 150));
        colorMap.put(Skill.RANGED, new Color(79, 193, 87, 150));
        colorMap.put(Skill.FLETCHING, new Color(0, 43, 45, 150));
        colorMap.put(Skill.MAGIC, new Color(0, 10, 189, 150));
        colorMap.put(Skill.HUNTER, new Color(175, 129, 88, 150));
        colorMap.put(Skill.DEFENCE, new Color(91, 108, 161, 150));
        colorMap.put(Skill.HITPOINTS, new Color(253, 255, 255, 150));
        colorMap.put(Skill.HERBLORE, new Color(9, 72, 9, 150));
        colorMap.put(Skill.COOKING, new Color(61, 24, 71, 150));
        colorMap.put(Skill.WOODCUTTING, new Color(125, 102, 57, 150));
        colorMap.put(Skill.RUNECRAFTING, new Color(130, 130, 121, 150));
        colorMap.put(Skill.PRAYER, new Color(255, 255, 194, 150));
        colorMap.put(Skill.SLAYER, new Color(0, 0, 0, 150));
        colorMap.put(Skill.CRAFTING, new Color(92, 72, 52, 150));
    }

    @Override
    public void notify(SkillEvent skillEvent) {
        lock.lock();
        long diff = (long) skillEvent.getCurrent() - skillEvent.getPrevious();
        map.compute(skillEvent.getSource(), (skill, aLong) -> aLong != null ? aLong + diff : diff);
        lock.unlock();
    }

}
