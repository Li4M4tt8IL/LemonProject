package me.pm.lemon.gui.bindcommandClickGui.windows;

import me.pm.lemon.gui.window.Window;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;

public abstract class BindClickGuiWindow extends Window {

    protected MinecraftClient mc;

    public int mouseX;
    public int mouseY;

    public int keyDown = -1;
    public boolean lmDown = false;
    public boolean rmDown = false;
    public boolean lmHeld = false;
    public int mwScroll = 0;

    public BindClickGuiWindow(int x1, int y1, int x2, int y2, String title) {
        super(x1, y1, x2, y2, title);
        mc = MinecraftClient.getInstance();
    }

    public boolean shouldClose(int mX, int mY) {
        return false;
    }

    protected void drawBar(MatrixStack matrix, int mX, int mY, TextRenderer textRend) {
        /* background and title bar */
        fillGrey(matrix, x1, y1, x2, y2);
        //fillGradient(matrix, x1 + 1, y1 + 1, x2 - 2, y1 + 12, 0xff0000ff, 0xff4080ff);
    }

    public void fillGrey(MatrixStack matrix, int x1, int y1, int x2, int y2) {
        //DrawableHelper.fill(matrix, x1, y1, x1 + 1, y2 - 1, 0x90b0b0b0);
        //DrawableHelper.fill(matrix, x1 + 1, y1, x2 - 1, y1 + 1, 0x90b0b0b0);
        //DrawableHelper.fill(matrix, x1 + 1, y2 - 1, x2, y2, 0x90000000);
        //DrawableHelper.fill(matrix, x2 - 1, y1 + 1, x2, y2, 0x90000000);
        //DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2 - 1, y2 - 1, 0xff505059);
        if(Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 1) {
            DrawableHelper.fill(matrix, x1, y1 + 12, x2, y1 + 13, ColorUtils.guiColor());

            DrawableHelper.fill(matrix, x1, y1, x1 + 1, y2, ColorUtils.guiColor());
            DrawableHelper.fill(matrix, x1, y2, x2 + 1, y2 - 1, ColorUtils.guiColor());
            DrawableHelper.fill(matrix, x2, y1, x1, y1 + 1, ColorUtils.guiColor());
            DrawableHelper.fill(matrix, x2, y2, x2 + 1, y1, ColorUtils.guiColor());
        } else if(Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 0) {
            Color color  = new Color(0, 255, 255).darker();
            Color background  = new Color(48, 48, 48, 150).darker();

            RenderUtils.drawRect(x1, y1, x2, y1 + 13, ColorUtils.guiColor());
            RenderUtils.drawRect(x1, y1 + 12, x2, y2, ColorUtils.guiBackgroundColor());

            DrawableHelper.fill(matrix, x1, y1 + 12, x2, y1 + 13, ColorUtils.guiColor());
        }
    }

    public void fillGreyNoTitle(MatrixStack matrix, int x1, int y1, int x2, int y2) {
//        DrawableHelper.fill(matrix, x1, y1 + 12, x2, y1 + 13, ColorUtils.guiColor());

        if(Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 1) {
            DrawableHelper.fill(matrix, x1, y1, x1 + 1, y2, ColorUtils.guiColor());
            DrawableHelper.fill(matrix, x1, y2, x2 + 1, y2 - 1, ColorUtils.guiColor());
            DrawableHelper.fill(matrix, x2, y1, x1, y1 + 1, ColorUtils.guiColor());
            DrawableHelper.fill(matrix, x2, y2, x2 + 1, y1, ColorUtils.guiColor());
        } else if(Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 0) {
            Color color  = new Color(0, 255, 255).darker();
            Color background  = new Color(48, 48, 48, 150).darker();

            RenderUtils.drawRect(x1, y1, x2, y2, background.getRGB());

            DrawableHelper.fill(matrix, x1, y1, x1 + 1, y2, color.getRGB());
            DrawableHelper.fill(matrix, x1, y2, x2 + 1, y2 - 1, color.getRGB());
            DrawableHelper.fill(matrix, x2, y1, x1, y1 + 1, color.getRGB());
            DrawableHelper.fill(matrix, x2, y2, x2 + 1, y1, color.getRGB());
        }
    }

    public boolean mouseOver(int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY < maxY;
    }

    public Triple<Integer, Integer, String> getTooltip() {
        return null;
    }

    public void updateKeys(int mouseX, int mouseY, int keyDown, boolean lmDown, boolean rmDown, boolean lmHeld, int mwScroll) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.keyDown = keyDown;
        this.lmDown = lmDown;
        this.rmDown = rmDown;
        this.lmHeld = lmHeld;
        this.mwScroll = mwScroll;
    }
}