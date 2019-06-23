package com.dax.api.paint;

import com.dax.api.file_cache.WebGrabber;
import org.rspeer.runetek.api.movement.position.Position;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class MapDisplay {

    private static final int GLOBAL_BASE_X = 1152;
    private static final int GLOBAL_BASE_Y = 1215;
    private static final int IMAGE_SIZE = 256;

    private static final String SOURCE_URL = "https://raw.githubusercontent.com/Explv/osrs_map_full_2019_05_29/master";

    public enum Scale {
        LARGEST(256, 1, 6),
        LARGE(128, 2, 7),
        NORMAL(64, 4, 8),
        SMALL(32, 8, 9),
        SMALLER(16, 16, 10),
        MICRO(8, 32, 11);

        final int regionSize;
        final int pixelPerTile;
        final int conversion;

        Scale(int regionSize, int pixelPerTile, int conversion) {
            this.regionSize = regionSize;
            this.pixelPerTile = pixelPerTile;
            this.conversion = conversion;
        }

        public Scale larger() {
            switch (this) {
                case LARGEST:
                    return this;
                case LARGE:
                    return LARGEST;
                case NORMAL:
                    return LARGE;
                case SMALL:
                    return NORMAL;
                case SMALLER:
                    return SMALL;
                case MICRO:
                    return SMALLER;
            }
            return null;
        }

        public Scale smaller() {
            switch (this) {
                case LARGEST:
                    return LARGE;
                case LARGE:
                    return NORMAL;
                case NORMAL:
                    return SMALL;
                case SMALL:
                    return SMALLER;
                case SMALLER:
                    return MICRO;
                case MICRO:
                    return MICRO;
            }
            return null;
        }

    }

    public static BufferedImage centeredImage(Scale scale, int x, int y, int z, Position... positions) {
        BufferedImage bufferedImage = new BufferedImage((IMAGE_SIZE / 2) * 3, (IMAGE_SIZE / 2) * 3, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR));

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                BufferedImage subImage = getImage(scale, x + (i * scale.regionSize), y - (j * scale.regionSize), z, positions);
                graphics2D.drawImage(subImage, ((i + 1) * (IMAGE_SIZE / 2)), ((j + 1) * (IMAGE_SIZE / 2)), IMAGE_SIZE / 2, IMAGE_SIZE / 2, null);
            }
        }

        return bufferedImage;
    }


    public static BufferedImage getImage(Scale scale, int x, int y, int z, Position... positions) {
        BufferedImage bufferedImage = WebGrabber.getImage(getURL(scale, x, y, z));

        if (bufferedImage == null) return null;

        for (Position position : positions) {
            mark(scale, (Graphics2D) bufferedImage.getGraphics(), position.getX(), position.getY(), position.getFloorLevel());
        }

        return bufferedImage;
    }

    private static void mark(Scale scale, Graphics2D g, int x, int y, int z) {
        int offsetX = x - GLOBAL_BASE_X;
        int offsetY = y - GLOBAL_BASE_Y;
        int localX = (offsetX % scale.regionSize) * scale.pixelPerTile;
        int localY = (offsetY % scale.regionSize) * scale.pixelPerTile;
        int flippedY = scale.pixelPerTile * scale.regionSize - localY;
        g.setColor(new Color(180, 72, 12, 140));
        g.fillRect(localX, flippedY, scale.pixelPerTile, scale.pixelPerTile);
        g.setColor(new Color(0, 0,0, 155));
        g.drawRect(localX, flippedY, scale.pixelPerTile, scale.pixelPerTile);
    }

    public static String getURL(Scale scale, int x, int y, int z) {
        int offsetX = x - GLOBAL_BASE_X;
        int offsetY = y - GLOBAL_BASE_Y;
        int baseX = offsetX - (offsetX % scale.regionSize);
        int baseY = offsetY - (offsetY % scale.regionSize);
        int mapX = baseX / scale.regionSize;
        int mapY = baseY / scale.regionSize;
        return String.format("%s/%d/%d/%d/%d.png", SOURCE_URL, z, scale.conversion, mapX, mapY);
    }


}
