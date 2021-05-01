package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class Step extends Module {
    public Step(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Height", 1, 3, 1, 0));
    }

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.5F;
        super.onDisable();
    }

    @EventTarget
    public void onTick(TickEvent event) {
        this.setDisplayName("Step \2477" + getSetting(0).asSlider().getValue());
        mc.player.stepHeight = (float) getSetting(0).asSlider().getValue();
    }
}
