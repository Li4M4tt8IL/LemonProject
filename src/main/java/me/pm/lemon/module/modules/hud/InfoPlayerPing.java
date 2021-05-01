package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class InfoPlayerPing extends ModuleDraggable {
    public InfoPlayerPing() {
        super("Info Player Ping", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        PlayerListEntry playerEntry = mc.player.networkHandler.getPlayerListEntry(mc.player.getGameProfile().getId());
        int ping = playerEntry == null ? 0 : playerEntry.getLatency();
        return MinecraftClient.getInstance().textRenderer.getWidth("Ping: " + ping);
    }

    @Override
    public int getHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(2).asToggle().state) {
            if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(2).asToggle().getChild(6).asToggle().state) {
                PlayerListEntry playerEntry = mc.player.networkHandler.getPlayerListEntry(mc.player.getGameProfile().getId());
                int ping = playerEntry == null ? 0 : playerEntry.getLatency();
                mc.textRenderer.drawWithShadow(matrixStack, "Ping: \247f" + ping, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, ColorUtils.textColor());
            }
        }
    }
}
