package me.pm.lemon.gui.bindcommandClickGui;

import me.pm.lemon.bindcommand.BindCommand;
import me.pm.lemon.bindcommand.BindCommandManager;
import me.pm.lemon.gui.bindcommandClickGui.windows.BindClickGuiWindow;
import me.pm.lemon.gui.bindcommandClickGui.windows.ModuleWindow;
import me.pm.lemon.gui.window.AbstractWindowScreen;
import me.pm.lemon.gui.window.Window;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;
import java.util.ArrayList;

public class BindClickGuiScreen extends AbstractWindowScreen {

    private int keyDown = -1;
    private boolean lmDown = false;
    private boolean rmDown = false;
    private boolean lmHeld = false;
    private int mwScroll = 0;

    public BindClickGuiScreen() {
        super(new LiteralText("gui.test"));
    }

    public void init() {

    }

    public void initWindows() {
        int len = 110;
        int i = 10;
        ArrayList<BindCommand> list = new ArrayList<>();
        for(BindCommand b :
            BindCommandManager.commands) {
            i += len + 5;
            list.add(b);
        }
        windows.add(new ModuleWindow(list, 115, 10, len,
                "BindCommands"));
    }

    public void closeWindows() {
        windows.clear();
    }

    public void resetWindows() {
        closeWindows();
        initWindows();
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void onClose() {
        client.openScreen(null);
    }

    public void render(MatrixStack matrix, int mX, int mY, float float_1) {
        this.renderBackground(matrix);
        super.render(matrix, mX, mY, float_1);

        int width = MinecraftClient.getInstance().getWindow().getWidth();
        int height = MinecraftClient.getInstance().getWindow().getHeight();

        Color background = new Color(44, 44, 44);

        for (Window w : windows) {
            if (w instanceof BindClickGuiWindow) {
                if (w instanceof ModuleWindow) {
                    ((ModuleWindow) w).setLen(110);
                }

                ((BindClickGuiWindow) w).updateKeys(mX, mY, keyDown, lmDown, rmDown, lmHeld, mwScroll);
            }
        }

        lmDown = false;
        rmDown = false;
        keyDown = -1;
        mwScroll = 0;
    }

    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (int_1 == 0) {
            lmDown = true;
            lmHeld = true;
        } else if (int_1 == 1)
            rmDown = true;

        // Fix having to double click windows to move them
        for (Window w : windows) {
            if (double_1 > w.x1 && double_1 < w.x2 && double_2 > w.y1 && double_2 < w.y2 && !w.closed) {
                w.onMousePressed((int) double_1, (int) double_2);
                break;
            }
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

    public void resetGui() {
        int x = 30;
        for (Window m : windows) {
            m.x1 = x;
            m.y2 = 35;
            x += (int) 110 + 5;
        }
    }

    private void drawHollowRect(MatrixStack matrixStack, int x, int y, int w, int h, int c) {
        this.drawHorizontalLine(matrixStack, x, x + w, y, c);
        this.drawHorizontalLine(matrixStack, x, x + w, y + h, c);

        this.drawVerticalLine(matrixStack, x, y + h, y, c);
        this.drawVerticalLine(matrixStack, x + w, y + h, y, c);
    }
}
