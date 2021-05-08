package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class AutoWaterBucket extends Module {
    public AutoWaterBucket(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        assert mc.player != null;
        if(mc.player.fallDistance >= 2F) {
            System.out.println("should mlg water bucket");
        }
    }
}
