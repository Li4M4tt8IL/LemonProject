package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.LemonLogger;

import java.awt.*;

public class DeathCoords extends Module {
    public DeathCoords(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        assert mc.player != null;
        if(mc.player.isDead()) {
            int x = (int) mc.player.getX();
            int y = (int) mc.player.getY();
            int z = (int) mc.player.getZ();

            LemonLogger.infoMessage("Coords of death: " + x + " " + y + " " + z);
        }
    }
}
