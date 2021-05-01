package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class Sneak extends Module {
    public Sneak(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    @Override
    public void onDisable() {
        mc.options.sneakToggled = false;
        assert mc.player != null;
        mc.player.setSneaking(false);
        super.onDisable();
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if(!this.isToggled()) return;
        assert mc.player != null;
        mc.options.sneakToggled = true;
        mc.player.setSneaking(true);
    }
}
