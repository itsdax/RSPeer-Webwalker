package com.dax.api.utils;

import com.dax.api.paint.PaintUtils;

import java.awt.Graphics2D;

public class StackTracePainter {

    public static void paint(Graphics2D g, int x, int y, Thread thread) {
        StackTraceElement[] stackTraceElements = thread.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
//            if (!stackTraceElement.toString().matches(".+\\.dax\\..+")) continue;
            String clean = stackTraceElement.toString().substring("com.dax.".length());
            PaintUtils.drawDebug(g, clean, x, y += 15, 2, 1);
        }
    }

}
