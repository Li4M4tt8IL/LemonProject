package me.pm.lemon.utils;

public class Timer {
    long time;
    public Timer() {
        time = System.currentTimeMillis();
    }

    public long getSecondsPassed() {
        return getTime()/1000;
    }

    private long getTime() {
        return System.currentTimeMillis() - time;
    }
}
