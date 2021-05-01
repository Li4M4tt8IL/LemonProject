package me.pm.lemon.gui.hud;

import net.minecraft.client.MinecraftClient;

public class ScreenPosition {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private double x, y;

    public ScreenPosition(double x, double y) {
        setRelative(x, y);
    }

    public ScreenPosition(int x, int y) {
        setAbsolute(x ,y);
    }

    public static ScreenPosition fromRelativePosition(double x, double y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolutePosition(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public int getAbsoluteX() {
        return (int) (x * mc.getWindow().getScaledWidth());
    }

    public int getAbsoluteY() {
        return (int) (y * mc.getWindow().getScaledHeight());
    }

    public double getRelativeX() {
        return x;
    }

    public double getRelativeY() {
        return y;
    }

    public void setRelative(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setAbsolute(int x,int y) {
        this.x = (double) x / mc.getWindow().getScaledWidth();
        this.y = (double) y / mc.getWindow().getScaledHeight();
    }
}
