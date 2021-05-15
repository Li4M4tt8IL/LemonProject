package me.pm.lemon.gui.alts;

import me.pm.lemon.utils.FileManager;

import java.util.ArrayList;

public class AltManager {
    public static Alt lastAlt;
    public static ArrayList<Alt> registry;

    static {
        registry = new ArrayList();
        FileManager.loadAlts();
    }

    public static ArrayList<Alt> getRegistry() {
        return registry;
    }
}