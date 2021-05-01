package me.pm.lemon.gui.testScreen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pm.lemon.Main;
import me.pm.lemon.gui.testScreen.elements.Panel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() {
        super(Text.of("Lemon Project Gui"));

        this.panel = new Panel(this,5, 5, 400, 260);
    }

    private Panel panel;

    public int mouseX;
    public int mouseY;

    public int keyDown = -1;
    public boolean lmDown = false;
    public boolean rmDown = false;
    public boolean mwDown = false;
    public boolean lmHeld = false;
    public int mwScroll = 0;

    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        renderBackground(matrix);

        panel.render(matrix, mouseX, mouseY);
        Identifier TEXTURE = new Identifier(Main.MOD_ID, "icon.png");

        GL11.glPushMatrix();
        GL11.glScalef(0.25F, 0.25F, 0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
        this.drawTexture(matrix, panel.x+5, panel.y+16, 0, 0, 256, 256);
        GL11.glPopMatrix();

        RenderSystem.pushMatrix();
//        RenderSystem.translatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
        RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        float h = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        h = h * 100.0F / (float)(this.textRenderer.getWidth("Lemon") + 64);
        RenderSystem.scalef(h, h, h);
        drawCenteredString(matrix, this.textRenderer, "Lemon", panel.x+2, panel.y+35, 16776960);
        RenderSystem.popMatrix();

        panel.updateKeys(mouseX, mouseY, keyDown, lmDown, rmDown, mwDown, lmHeld, mwScroll);

        lmDown = false;
        rmDown = false;
        mwDown = false;
        keyDown = -1;
        mwScroll = 0;
    }

    public boolean mouseOver(int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY < maxY;
    }

    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (int_1 == 0) {
            lmDown = true;
            lmHeld = true;
        } else if (int_1 == 1) {
            rmDown = true;
        } else if(int_1 == 2) {
            mwDown = true;
        }

        return super.mouseClicked(double_1, double_2, int_1);
    }

    public boolean mouseReleased(double double_1, double double_2, int int_1) {
        if (int_1 == 0)
            lmHeld = false;

        return super.mouseReleased(double_1, double_2, int_1);
    }

    public boolean keyPressed(int int_1, int int_2, int int_3) {
        keyDown = int_1;
        return super.keyPressed(int_1, int_2, int_3);
    }
    public boolean mouseScrolled(double double_1, double double_2, double double_3) {
        mwScroll = (int) double_3;
        return super.mouseScrolled(double_1, double_2, double_3);
    }
}
