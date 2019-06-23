package com.dax.api.paint.debug;

import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.api.scene.Projection;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

public class PositionalDebug {

    public static void drawArrow(Graphics graphics, Positionable from, Positionable to, Color color) {
        Graphics2D g = (Graphics2D) graphics;

        Point a = getCenter(from);
        if (a == null) return;
        Point b = getCenter(to);
        if (b == null) return;
        drawPointer(g, a.x, a.y, b.x, b.y, 8, color);
        g.drawLine(a.x, a.y, b.x, b.y);
    }

    public static void drawLine(Graphics graphics, Positionable from, Positionable to, Color color) {
        Graphics2D g = (Graphics2D) graphics;
        Point a = getCenter(from);
        if (a == null) return;
        Point b = getCenter(to);
        if (b == null) return;
        g.setColor(color);
        g.drawLine(a.x, a.y, b.x, b.y);
    }

    public static void outline(Graphics graphics, Positionable positionable, Color color) {
        if (positionable == null) return;
        Polygon polygon = Projection.getTileShape(positionable);

        if (polygon == null) return;

        graphics.setColor(color);

        Graphics2D g = (Graphics2D) graphics;

        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(3));
        graphics.drawPolygon(polygon);
        g.setStroke(stroke);
    }

    public static void draw(Graphics graphics, Positionable positionable, String debugMessage) {
        draw(graphics, positionable, debugMessage, null);
    }

    public static void draw(Graphics graphics, Positionable positionable, String debugMessage, Color color) {
        draw(graphics, positionable, debugMessage, color, 0, 0);
    }

    public static void draw(Graphics graphics, Positionable positionable, String debugMessage, Color color, int offsetX, int offsetY) {
        Point point = getCenter(positionable);
        if (point == null) return;

        if (color != null) {
            graphics.setColor(color);
        } else {
            graphics.setColor(Color.RED);
        }
        graphics.drawString(debugMessage, point.x + offsetX, point.y + offsetY);
    }

    public static Point getCenter(Positionable positionable) {
        Polygon polygon = Projection.getTileShape(positionable);

        if (polygon == null) return null;

        int x = Arrays.stream(polygon.xpoints).sum() / 4;
        int y = Arrays.stream(polygon.ypoints).sum() / 4;
        return new Point(x, y);
    }

    private static Line2D.Double drawPointer(Graphics2D g, int x1, int y1, int x2, int y2, float arrowWidth, Color fillColor) {
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        xPoints[0] = x2;
        yPoints[0] = y2;

        // build the line vector
        float[] vecLine = new float[2];
        vecLine[0] = (float) xPoints[0] - x1;
        vecLine[1] = (float) yPoints[0] - y1;

        // build the arrow base vector - normal to the line
        float[] vecLeft = new float[2];
        vecLeft[0] = -vecLine[1];
        vecLeft[1] = vecLine[0];

        // setup length parameters
        float fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1]);
        float th = arrowWidth / (2.0f * fLength);
        float ta = arrowWidth / (2.0f * ((float) Math.tan(0.423f) / 2.0f) * fLength);

        // find the base of the arrow
        float baseX = ((float) xPoints[0] - ta * vecLine[0]);
        float baseY = ((float) yPoints[0] - ta * vecLine[1]);

        // build the points on the sides of the arrow
        xPoints[1] = (int) (baseX + th * vecLeft[0]);
        yPoints[1] = (int) (baseY + th * vecLeft[1]);
        xPoints[2] = (int) (baseX - th * vecLeft[0]);
        yPoints[2] = (int) (baseY - th * vecLeft[1]);

        g.setColor(fillColor);
        g.fillPolygon(xPoints, yPoints, 3);
        return new Line2D.Double(x1, y1, (int) baseX, (int) baseY);
    }

}
