package me.pm.lemon.module.modules.movement;

import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class Fly extends Module {
    public Fly(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if(mc.player != null) {
            mc.player.abilities.flying = true;
            mc.player.abilities.allowFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if(mc.player != null) {
            assert mc.player != null;
            mc.player.abilities.flying = false;
            if (!mc.player.abilities.creativeMode) mc.player.abilities.allowFlying = false;
        }
        super.onDisable();
    }

}
