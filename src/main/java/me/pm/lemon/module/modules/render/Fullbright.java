package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class Fullbright extends Module {
    public Fullbright(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    @Override
    public void onDisable() {
        mc.options.gamma = 1F;
        super.onDisable();
    }

    @EventTarget
    public void onTick(TickEvent event) {
        mc.options.gamma = 10F;
    }
}
