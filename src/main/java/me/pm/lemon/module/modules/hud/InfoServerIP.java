package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InfoServerIP extends ModuleDraggable {
    public InfoServerIP() {
        super("Info Server IP", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        return MinecraftClient.getInstance().textRenderer.getWidth("IP: " + getServerHostAddress());
    }

    @Override
    public int getHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(2).asToggle().state) {
            if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(2).asToggle().getChild(5).asToggle().state) {
                mc.textRenderer.drawWithShadow(matrixStack, "IP: \247f" + getServerHostAddress(), pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, ColorUtils.textColor());
            }
        }
    }

    public String getServerHostAddress() {
        try {
            InetAddress inetAddress = MinecraftClient.getInstance().getCurrentServerEntry() == null ? null
                    : InetAddress.getByName(MinecraftClient.getInstance().getCurrentServerEntry().address);
            if(inetAddress != null) {
                return inetAddress.getHostAddress();
            }
            return "0";
        } catch (UnknownHostException e) {
            return "0";
        }
    }
}
