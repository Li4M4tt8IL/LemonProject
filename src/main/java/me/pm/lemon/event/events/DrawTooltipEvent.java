package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;

import java.util.List;

public class DrawTooltipEvent extends Event {
    public Screen screen;
    public MatrixStack matrix;
    public List<? extends OrderedText> text;
    public int x;
    public int y;
    public int mX;
    public int mY;

    public DrawTooltipEvent(MatrixStack matrix, List<? extends OrderedText> text, int x, int y, int mX, int mY) {
        this.matrix = matrix;
        screen = MinecraftClient.getInstance().currentScreen;
        this.text = text;
        this.x = x;
        this.y = y;
        this.mX = mX;
        this.mY = mY;
    }
}
