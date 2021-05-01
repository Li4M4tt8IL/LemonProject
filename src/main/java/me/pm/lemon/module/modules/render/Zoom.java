package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.testScreen.settings.SettingSlider;
import me.pm.lemon.gui.testScreen.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class Zoom extends Module {
    public Zoom(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Scale", 1, 10, 3, 0),
                new SettingToggle("Smooth", true));
    }

    public double prevFov;

    @Override
    public void onEnable() {
        super.onEnable();
        if(mc.player == null) return;

        prevFov = mc.options.fov;

        if (getSetting(0).asSlider().getValue() < 1 || !getSetting(1).asToggle().state) {
            mc.options.fov = prevFov / getSetting(0).asSlider().getValue();
        }

        mc.options.smoothCameraEnabled = true;
    }

    @Override
    public void onDisable() {
        if (getSetting(0).asSlider().getValue() < 1 || !getSetting(1).asToggle().state) {
            mc.options.fov = prevFov;

        } else {
            for (double f = mc.options.fov; f < prevFov; f *= 1.4) {
                    mc.options.fov = Math.min(prevFov, mc.options.fov * 1.4);
            }
        }
        mc.options.smoothCameraEnabled = false;
        super.onDisable();
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (getSetting(0).asSlider().getValue() >= 1 && mc.options.fov > prevFov / getSetting(0).asSlider().getValue()) {
            mc.options.fov /= 1.4;

            if (mc.options.fov <= prevFov / getSetting(0).asSlider().getValue()) {
                mc.options.fov = prevFov / getSetting(0).asSlider().getValue();
            }
        } else {
            mc.options.fov = prevFov / getSetting(0).asSlider().getValue();
        }
    }
}
