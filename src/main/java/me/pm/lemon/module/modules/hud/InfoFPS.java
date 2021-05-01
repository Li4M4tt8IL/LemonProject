package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import me.pm.lemon.utils.generalUtils.FabricReflect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class InfoFPS extends ModuleDraggable {
    public InfoFPS() {
        super("Info FPS", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        int fps = (int) FabricReflect.getFieldValue(MinecraftClient.getInstance(), "field_1738", "currentFps");
        return MinecraftClient.getInstance().textRenderer.getWidth("FPS: " + fps);
    }

    @Override
    public int getHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(2).asToggle().state) {
            if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(2).asToggle().getChild(4).asToggle().state) {
                int fps = (int) FabricReflect.getFieldValue(MinecraftClient.getInstance(), "field_1738", "currentFps");
                mc.textRenderer.drawWithShadow(matrixStack, "FPS: \247f" + fps, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, ColorUtils.textColor());
            }
        }
    }
}
