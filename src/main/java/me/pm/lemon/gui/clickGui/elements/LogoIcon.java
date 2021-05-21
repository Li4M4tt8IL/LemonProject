package me.pm.lemon.gui.clickGui.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class LogoIcon {
    public int x, y, width, height;

    public Panel parent;
    public Identifier TEXTURE;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public LogoIcon(Panel parent, int x, int y, int width, int height, Identifier TEXTURE) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.TEXTURE = TEXTURE;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glScalef(0.25F, 0.25F, 0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        parent.parent.drawTexture(matrixStack, x, y, 0, 0, width, height);
        GL11.glPopMatrix();
    }
}
