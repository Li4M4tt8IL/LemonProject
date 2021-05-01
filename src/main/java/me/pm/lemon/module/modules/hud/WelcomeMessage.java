package me.pm.lemon.module.modules.hud;

import me.pm.lemon.Main;
import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class WelcomeMessage extends ModuleDraggable {
    public WelcomeMessage() {
        super("Hello World", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        return MinecraftClient.getInstance().textRenderer.getWidth("Welcome to: " + Main.ClientInfo.clientName + " " + Main.ClientInfo.clientVersion);
    }

    @Override
    public int getHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(0).asToggle().state)
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, "Welcome to: \247f" + Main.ClientInfo.clientName + " " + Main.ClientInfo.clientVersion, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, ColorUtils.textColor());
    }
}
