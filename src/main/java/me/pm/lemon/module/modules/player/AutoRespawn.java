package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class AutoRespawn extends Module {
    public AutoRespawn(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if(mc.player.isDead()) {
            mc.player.requestRespawn();
        }
    }
}
