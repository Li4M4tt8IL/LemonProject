package me.pm.lemon.gui.clickGui.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class LogoText {
    public int x, y, width, height;

    public Panel parent;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public LogoText(Panel parent, int x, int y) {
        this.parent = parent;
        this.x = x;
        this.y = y;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glScalef(2F, 2F, 0F);
        parent.parent.drawCenteredString(matrixStack, mc.textRenderer, "Lemon", x, y, 16776960);
        GL11.glPopMatrix();
    }
}