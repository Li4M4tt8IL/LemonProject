package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class Sprint extends Module {
    public Sprint(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (!isToggled()) return;
        mc.player.setSprinting(mc.player.input.movementForward > 0 && mc.player.input.movementSideways != 0 ||
                mc.player.input.movementForward > 0 && !mc.player.isSneaking());
    }
}
