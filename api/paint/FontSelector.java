package com.dax.api.paint;

import java.awt.Font;

public class FontSelector {

    private static FontSelector ourInstance = new FontSelector();

    public static FontSelector getInstance() {
        return ourInstance;
    }

    private Font[] fontFamily;

    private FontSelector() {

        String os = System.getProperty("os.name").toLowerCase();

        if (isWindows(os)) {
            fontFamily = new Font[] {
                    new Font("Century Gothic", Font.PLAIN, 11),
                    new Font("Century Gothic", Font.PLAIN, 12),
                    new Font("Century Gothic", Font.PLAIN, 14),
                    new Font("Century Gothic", Font.PLAIN, 18),
                    new Font("Century Gothic", Font.ITALIC, 11),
                    new Font("Century Gothic", Font.ITALIC, 12),
                    new Font("Century Gothic", Font.ITALIC, 14),
                    new Font("Century Gothic", Font.ITALIC, 18),
            };
        } else if (isMac(os)) {
            fontFamily = new Font[] {
                    new Font("Apple Gothic", Font.PLAIN, 11),
                    new Font("Apple Gothic", Font.PLAIN, 12),
                    new Font("Apple Gothic", Font.PLAIN, 14),
                    new Font("Apple Gothic", Font.PLAIN, 18),
                    new Font("Apple Gothic", Font.ITALIC, 11),
                    new Font("Apple Gothic", Font.ITALIC, 12),
                    new Font("Apple Gothic", Font.ITALIC, 14),
                    new Font("Apple Gothic", Font.ITALIC, 18),
            };
        } else {
            throw new IllegalStateException("Cannot determine OS");
        }

    }

    public Font getFontSmall() {
        return fontFamily[0];
    }

    public Font getFontNormal() {
        return fontFamily[1];
    }

    public Font getFontLarge() {
        return fontFamily[2];
    }

    public Font getFontXLarge() {
        return fontFamily[3];
    }

    public Font getFontSmallItalics() {
        return fontFamily[4];
    }

    public Font getFontNormalItalics() {
        return fontFamily[5];
    }

    public Font getFontLargeItalics() {
        return fontFamily[6];
    }

    public Font getFontXLargeItalics() {
        return fontFamily[7];
    }

    private boolean isWindows(String os) {
        return os.contains("win");
    }

    private boolean isMac(String os) {
        return os.contains("mac");
    }

    private boolean isUnix(String os) {
        return os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0;
    }

    private boolean isSolaris(String os) {
        return os.contains("sunos");
    }

}
