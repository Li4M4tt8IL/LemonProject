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

public class WorldInfoFacing extends ModuleDraggable {
    public WorldInfoFacing() {
        super("World Info Facing", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        String facing = "default";
        if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("NORTH")) {
            facing = "(-Z)";
        }
        if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("SOUTH")) {
            facing = "(+Z)";
        }
        if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("WEST")) {
            facing = "(-X)";
        }
        if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("EAST")) {
            facing = "(+X)";
        }
        return MinecraftClient.getInstance().textRenderer.getWidth("Facing: " + mc.player.getHorizontalFacing().toString().toUpperCase() + " " + facing);
    }

    @Override
    public int getHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(3).asToggle().state) {
            if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(3).asToggle().getChild(1).asToggle().state) {
                String facing = "default";
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("NORTH")) {
                    facing = "(-Z)";
                }
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("SOUTH")) {
                    facing = "(+Z)";
                }
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("WEST")) {
                    facing = "(-X)";
                }
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("EAST")) {
                    facing = "(+X)";
                }
                mc.textRenderer.drawWithShadow(matrixStack, "Facing:\247f " + mc.player.getHorizontalFacing().toString().toUpperCase() + " " + facing, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, ColorUtils.textColor());
            }
        }
    }
}
