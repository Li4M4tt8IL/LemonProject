package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;

public class KeyPressEvent extends Event {
    private final int key;
    private final int scanCode;

    public KeyPressEvent(int key, int scanCode) {
        this.key = key;
        this.scanCode = scanCode;
    }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scanCode;
    }
}
