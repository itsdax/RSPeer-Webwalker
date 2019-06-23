package com.dax.api.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.concurrent.TimeUnit;

public class PaintUtils {

    public static void drawDebug(Graphics2D graphics2D, Color titleColor, Color debugColor, PaintStyle paintStyle, String title, String debug, int x, int y) {
        graphics2D.setFont(FontSelector.getInstance().getFontNormal());

        String text = title + ": " + debug;
        AttributedString attributedString = new AttributedString(text);

        attributedString.addAttribute(TextAttribute.FONT, graphics2D.getFont(), 0, text.length());
        int offset = 0;
        attributedString.addAttribute(TextAttribute.FOREGROUND, titleColor, offset, offset += title.length());
        attributedString.addAttribute(TextAttribute.FOREGROUND, paintStyle.getInfoText(), offset, offset += 2);
        attributedString.addAttribute(TextAttribute.FOREGROUND, debugColor, offset, offset += debug.length());

        Rectangle rectangle = getBoundingBox(graphics2D, text, 4, 2);
        rectangle.translate(x, y);


        graphics2D.setColor(paintStyle.getFill());
        graphics2D.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        graphics2D.setColor(paintStyle.getBorder());
        graphics2D.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        graphics2D.drawString(attributedString.getIterator(), x + 4, y + 2);
    }

    public static String timeToString(long time) {
        final long hr = TimeUnit.MILLISECONDS.toHours(time);
        final long min = TimeUnit.MILLISECONDS.toMinutes(time - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(time - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }

    public static String timeToStringShort(long time) {
        final long hr = TimeUnit.MILLISECONDS.toHours(time);
        final long min = TimeUnit.MILLISECONDS.toMinutes(time - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(time - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        return String.format("%02d:%02d", min, sec);
    }

    public static void drawDebug(Graphics2D graphics2D, String text, int x, int y, int paddingX, int paddingY) {
        drawDebug(graphics2D,
                FontSelector.getInstance().getFontSmall(),
                text, x, y,
                PaintStyle.DEFAULT.getBorder(),
                PaintStyle.DEFAULT.getFill(),
                PaintStyle.DEFAULT.getStringText(),
                paddingX, paddingY);
    }

    public static void drawDebug(Graphics2D graphics2D, Font font, String text, int x, int y, Color border, Color fill, Color textColor, int paddingX, int paddingY) {
        graphics2D.setFont(font);

        Rectangle rectangle = getBoundingBox(graphics2D, text, paddingX, paddingY);
        rectangle.translate(x, y);

        drawBorder(graphics2D, rectangle, border, fill, paddingX, paddingY);

        graphics2D.setColor(textColor);
        graphics2D.drawString(text, rectangle.x + paddingX, rectangle.y + rectangle.height - paddingY);
    }


    public static Rectangle getBoundingBox(Graphics2D graphics2D, String text) {
        FontRenderContext frc = graphics2D.getFontRenderContext();
        GlyphVector gv = graphics2D.getFont().createGlyphVector(frc, text);
        return gv.getPixelBounds(null, 0, 0);
    }

    public static Rectangle getBoundingBox(Graphics2D graphics2D, String text, int paddingX, int paddingY) {
        Rectangle rectangle = getBoundingBox(graphics2D, text);
        return new Rectangle(rectangle.x, rectangle.y, rectangle.width + (paddingX * 2), rectangle.height + (paddingY * 2));
    }

    public static void drawBorder(Graphics2D graphics2D, Rectangle rectangle, Color border, Color fill, int paddingX, int paddingY) {
        drawBorder(graphics2D, rectangle.x, rectangle.y, rectangle.width, rectangle.height, border, fill, paddingX, paddingY);
    }

    public static void drawBorder(Graphics2D graphics2D, int x, int y, int width, int height, Color border, Color fill, int paddingX, int paddingY) {
        graphics2D.setColor(border);
        graphics2D.drawRect(x - paddingX, y - paddingY, width + (paddingX * 2), height + (paddingY * 2));

        if (fill != null) {
            graphics2D.setColor(fill);
            graphics2D.fillRect(x - paddingX, y - paddingY, width + (paddingX * 2), height + (paddingY * 2));
        }
    }

}
