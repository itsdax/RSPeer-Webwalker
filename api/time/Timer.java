package com.dax.api.time;

public class Timer {

    public static long since(long ms) {
        return System.currentTimeMillis() - ms;
    }

}
