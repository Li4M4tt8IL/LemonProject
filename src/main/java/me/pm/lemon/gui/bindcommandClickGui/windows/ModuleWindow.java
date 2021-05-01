package me.pm.lemon.gui.bindcommandClickGui.windows;

import me.pm.lemon.bindcommand.BindCommand;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import org.apache.commons.lang3.tuple.Triple;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ModuleWindow extends BindClickGuiWindow {

    public List<BindCommand> modList = new ArrayList<>();
    public LinkedHashMap<BindCommand, Boolean> mods = new LinkedHashMap<>();

    public boolean hiding;

    private int len;

    private Triple<Integer, Integer, String> tooltip = null;

    public ModuleWindow(List<BindCommand> commands, int x1, int y1, int len, String title) {
        super(x1, y1, x1 + len, 0, title);

        this.len = len;
        modList = commands;

        for (BindCommand m : commands)
            this.mods.put(m, false);
        y2 = getHeight();
    }

    public void render(MatrixStack matrix, int mX, int mY) {
        super.render(matrix, mX, mY);

        TextRenderer textRend = mc.textRenderer;

        tooltip = null;
        int x = x1 + 1;
        int y = y1 + 13;
        x2 = x + len + 1;

        if (rmDown && mouseOver(x, y - 12, x + len, y)) {
            mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            hiding = !hiding;
        }

        if (hiding) {
            y2 = y;
            return;
        } else {
            y2 = y + getHeight();
        }

        int curY = 0;
        for (Map.Entry<BindCommand, Boolean> m : new LinkedHashMap<>(mods).entrySet()) {
            if (m.getValue()) {
                //fillReverseGrey(x, y + curY, x+len-1, y + 12 + curY);
                fillGreySides(matrix, x, y + curY, x + len - 1, y + 12 + curY);
                //DrawableHelper.fill(matrix, x, y + curY, x + len - 2, y + curY + 1, 0x90000000);
                //DrawableHelper.fill(matrix, x + len - 3, y + curY + 1, x + len - 2, y + curY + 12, 0x90b0b0b0);
            }
            DrawableHelper.fill(matrix, x, y + curY, x + len + 1, y + 13 + curY,
                    mouseOver(x, y + curY, x + len, y + 12 + curY) ? 0x70303070 : 0x00000000);

            if(Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 0) {
                Color color  = new Color(0, 255, 255, 150).darker();

                DrawableHelper.fill(matrix, x, y + curY, x + len + 1, y + 12 + curY,
                        m.getKey().isToggled() ? color.getRGB() : 0x00ff0000);
            } else if (Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 1) {
                DrawableHelper.fill(matrix, x, y + curY, x + len + 1, y + 12 + curY,
                        m.getKey().isToggled() ? ColorUtils.guiColor() : 0x00ff0000);
            }

            textRend.drawWithShadow(matrix, textRend.trimToWidth(m.getKey().getCommandName(), len),
                    x + 3, y + 2 + curY, m.getKey().isToggled() ? 0xFFFFFF : 0xc0c0c0);

            if(Module.getModule(ClickGui.class).getSetting(5).asToggle().state) {
                textRend.draw(matrix, m.getValue() ? "\u00a7a>" :"\u00a7cv", x + len - 6, y + 2 + curY, -1);
            }
            /* Set which module settings show on */
            if (mouseOver(x, y + curY, x + len, y + 12 + curY)) {
                if (lmDown) m.getKey().toggle();
                if (rmDown) {
                    mods.replace(m.getKey(), !m.getValue());
                    for(Map.Entry<BindCommand, Boolean> moduleBooleanEntry : mods.entrySet()) {
                        if(!m.getKey().getCommandName().equals(moduleBooleanEntry.getKey().getCommandName())) {
                            if(moduleBooleanEntry.getValue()) {
                                mods.replace(moduleBooleanEntry.getKey(), !moduleBooleanEntry.getValue());
                            }
                        }
                    }
                }
                if (lmDown || rmDown)
                    mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }

            curY += 12;

            /* draw settings */
            if (m.getValue()) {
                drawBindSetting(matrix, m.getKey(), keyDown, x, y + curY, textRend);
                curY += 12;
            }
        }
    }

    public void drawBindSetting(MatrixStack matrix, BindCommand m, int key, int x, int y, TextRenderer textRend) {
        //DrawableHelper.fill(matrix, x, y + 11, x + len - 2, y + 12, 0x90b0b0b0);
        //DrawableHelper.fill(matrix, x + len - 2, y, x + len - 1, y + 12, 0x90b0b0b0);
        //DrawableHelper.fill(matrix, x, y - 1, x + 1, y + 11, 0x90000000);

        if (key >= 0 && mouseOver(x, y, x + len, y + 12))
            m.setKeyCode((key != GLFW.GLFW_KEY_DELETE && key != GLFW.GLFW_KEY_ESCAPE) ? key : GLFW.GLFW_KEY_UNKNOWN);

        String name = m.getKeyCode() < 0 ? "NONE" : InputUtil.fromKeyCode(m.getKeyCode(), -1).getLocalizedText().getString();
        if (name == null)
            name = "KEY" + m.getKeyCode();
        else if (name.isEmpty())
            name = "NONE";

        textRend.drawWithShadow(matrix, "Bind: " + name + (mouseOver(x, y, x + len, y + 12) ? "..." : ""), x + 2, y + 2,
                mouseOver(x, y, x + len, y + 12) ? 0xcfc3cf : 0xcfe0cf);
    }

    public void fillReverseGrey(MatrixStack matrix, int x1, int y1, int x2, int y2) {
        DrawableHelper.fill(matrix, x1, y1, x1 + 1, y2 - 1, 0x90000000);
        DrawableHelper.fill(matrix, x1 + 1, y1, x2 - 1, y1 + 1, 0x90000000);
        DrawableHelper.fill(matrix, x1 + 1, y2 - 1, x2, y2, 0x90b0b0b0);
        DrawableHelper.fill(matrix, x2 - 1, y1 + 1, x2, y2 - 1, 0x90b0b0b0);
        DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2 - 1, y2 - 1, 0xff505059);
    }

    private void fillGreySides(MatrixStack matrix, int x1, int y1, int x2, int y2) {
        DrawableHelper.fill(matrix, x1, y1, x1 + 1, y2 - 1, 0x90000000);
        DrawableHelper.fill(matrix, x2 - 1, y1 + 1, x2, y2, 0x90b0b0b0);
    }

    protected void drawBar(MatrixStack matrix, int mX, int mY, TextRenderer textRend) {
        super.drawBar(matrix, mX, mY, textRend);
        textRend.draw(matrix, hiding ? "+" : "_", x2 - 11, y1 + (hiding ? 4 : 0), ColorUtils.guiTextColor());
    }

    public Triple<Integer, Integer, String> getTooltip() {
        return tooltip;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getHeight() {
        int h = 1;
        for(Map.Entry<BindCommand, Boolean> e : mods.entrySet()) {
            h += 12;

            if (e.getValue()) {
                h += 12;
            }
        }
        return h;
    }
}
