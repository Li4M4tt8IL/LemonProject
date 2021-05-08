package me.pm.lemon.gui.clickGui;

import me.pm.lemon.gui.clickGui.elements.Panel;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

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
