package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class Keystrokes extends ModuleDraggable {
    public Keystrokes() {
        super("Keystrokes", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        return 48;
    }

    @Override
    public int getHeight() {
        return 63;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(6).asToggle().state) {
            int width = 15;
            int offset = 1;

            {
                RenderUtils.drawRect(pos.getAbsoluteX() + width, pos.getAbsoluteY(), pos.getAbsoluteX() + (width * 2), pos.getAbsoluteY() + width,
                        mc.options.keyForward.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 100).getRGB());

                RenderUtils.drawRect(pos.getAbsoluteX() + width, (pos.getAbsoluteY() + offset) + width, pos.getAbsoluteX() + (width * 2), (pos.getAbsoluteY() + offset) + (width * 2),
                        mc.options.keyBack.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 100).getRGB());

                RenderUtils.drawRect((pos.getAbsoluteX() + offset) + (width * 2), pos.getAbsoluteY() + offset + width, (pos.getAbsoluteX() + offset) + (width * 3), (pos.getAbsoluteY() + offset) + (width * 2),
                        mc.options.keyRight.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 100).getRGB());

                RenderUtils.drawRect(pos.getAbsoluteX(), pos.getAbsoluteY() + offset + width, (pos.getAbsoluteX() - offset) + width, (pos.getAbsoluteY() + offset) + (width * 2),
                        mc.options.keyLeft.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 100).getRGB());

                RenderUtils.drawRect(pos.getAbsoluteX(), (pos.getAbsoluteY() + (offset * 2)) + (width * 2), (pos.getAbsoluteX() + offset) + (width * 3), (pos.getAbsoluteY() + (offset * 2)) + (width * 3),
                        mc.options.keyJump.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 100).getRGB());

                RenderUtils.drawRect(pos.getAbsoluteX(), (pos.getAbsoluteY() + (offset * 3)) + (width * 3), (pos.getAbsoluteX() - (offset * 2)) + (width * 1.6), (pos.getAbsoluteY() + (offset * 2)) + (width * 4),
                        mc.options.keyAttack.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 100).getRGB());

                RenderUtils.drawRect((pos.getAbsoluteX()) + (width * 1.6), (pos.getAbsoluteY() + (offset * 3)) + (width * 3), (pos.getAbsoluteX() - (offset * 2)) + (width * 3.2), (pos.getAbsoluteY() + (offset * 2)) + (width * 4),
                        mc.options.keyUse.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 100).getRGB());
            } /* Rectangles */

            {
                mc.textRenderer.draw(matrixStack, "W", (float) (pos.getAbsoluteX() + width + 4.5), (pos.getAbsoluteY() + 4),
                        mc.options.keyForward.isPressed() ? new Color(0, 0, 0, 200).getRGB(): new Color(255, 255, 255, 200).getRGB());

                mc.textRenderer.draw(matrixStack, "S", (float) (pos.getAbsoluteX() + width + 4.5), (pos.getAbsoluteY() + offset) + width + 4,
                        mc.options.keyBack.isPressed() ? new Color(0, 0, 0, 200).getRGB(): new Color(255, 255, 255, 200).getRGB());

                mc.textRenderer.draw(matrixStack, "D", (float) ((pos.getAbsoluteX() + offset) + (width * 2) + 4.5), (pos.getAbsoluteY() + offset) + width + 4,
                        mc.options.keyRight.isPressed() ? new Color(0, 0, 0, 200).getRGB(): new Color(255, 255, 255, 200).getRGB());

                mc.textRenderer.draw(matrixStack, "A", ((pos.getAbsoluteX() + offset) + 3), (pos.getAbsoluteY() + offset) + width + 4,
                        mc.options.keyLeft.isPressed() ? new Color(0, 0, 0, 200).getRGB(): new Color(255, 255, 255, 200).getRGB());

                mc.textRenderer.draw(matrixStack, "SPACE", (float) (pos.getAbsoluteX() + (width / 2)), (pos.getAbsoluteY() + (offset * 2)) + (width * 2) + 4,
                        mc.options.keyJump.isPressed() ? new Color(0, 0, 0, 200).getRGB(): new Color(255, 255, 255, 200).getRGB());

                mc.textRenderer.draw(matrixStack, "LMB", pos.getAbsoluteX() + offset + 1, (pos.getAbsoluteY() + (offset * 2)) + (width * 3) + 4,
                        mc.options.keyAttack.isPressed() ? new Color(0, 0, 0, 200).getRGB(): new Color(255, 255, 255, 200).getRGB());

                mc.textRenderer.draw(matrixStack, "RMB", (float) ((pos.getAbsoluteX() - offset) + (width * 1.6) + 3), (pos.getAbsoluteY() + (offset * 2)) + (width * 3) + 4,
                        mc.options.keyUse.isPressed() ? new Color(0, 0, 0, 200).getRGB(): new Color(255, 255, 255, 200).getRGB());
            } /* Text */
        }
    }

}
