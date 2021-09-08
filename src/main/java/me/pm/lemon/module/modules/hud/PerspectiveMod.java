package me.pm.lemon.module.modules.hud;

import me.pm.lemon.Main;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.util.math.MatrixStack;

public class PerspectiveMod extends ModuleDraggable {

    public PerspectiveMod() {
        super("", Category.HUD, "", 0, Colors.HUD);
    }

    public static boolean perspectiveEnabled = false;
    public static float cameraPitch;
    public static float cameraYaw;
    private static boolean held = false;
    private static boolean holdMode = true;

    @EventTarget
    public void onTick(TickEvent e) {
        if(mc.player != null) {
            if (holdMode) {
                this.perspectiveEnabled = Main.ClientKeys.freelookKey.isPressed();
                if (this.perspectiveEnabled && !this.held) {
                    this.held = true;
                    this.cameraPitch = mc.player.pitch;
                    this.cameraYaw = mc.player.yaw;
                    mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                }
            } else if (Main.ClientKeys.freelookKey.wasPressed()) {
                this.perspectiveEnabled = !this.perspectiveEnabled;
                this.cameraPitch = mc.player.pitch;
                this.cameraYaw = mc.player.yaw;
                mc.options.setPerspective(this.perspectiveEnabled ? Perspective.THIRD_PERSON_BACK : Perspective.FIRST_PERSON);
            }

            if (!this.perspectiveEnabled && this.held) {
                this.held = false;
                mc.options.setPerspective(Perspective.FIRST_PERSON);
            }

            if (this.perspectiveEnabled && mc.options.getPerspective() != Perspective.THIRD_PERSON_BACK) {
                this.perspectiveEnabled = false;
            }
        }
    }

    @Override
    public int getWidth() {
        return mc.textRenderer.getWidth("[Perspective Toggled]");
    }

    @Override
    public int getHeight() {
        return mc.textRenderer.fontHeight;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(10).asToggle().state) {
            if(perspectiveEnabled) {
                mc.textRenderer.drawWithShadow(matrixStack, "[Perspective Toggled]", pos.getAbsoluteX(), pos.getAbsoluteY(), ColorUtils.textColor());
            }
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(10).asToggle().state) {
            mc.textRenderer.drawWithShadow(matrixStack, "[Perspective Toggled]", pos.getAbsoluteX(), pos.getAbsoluteY(), ColorUtils.textColor());
        }
    }
}
