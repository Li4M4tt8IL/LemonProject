package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class BunnyHop extends Module {
    public BunnyHop(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Height", 0.1, 1.2, 0.5, 1),
                new SettingToggle("Legit", true));
    }

    public static double height = 1;
    public static boolean jump = false;

    @EventTarget
    public void onTick(TickEvent event) {
        height = getSetting(0).asSlider().getValue();
        jump = getSetting(1).asToggle().state;
        if(mc.player.isOnGround() && mc.player.forwardSpeed > 0 && !mc.player.isSubmergedInWater() && !mc.player.isInLava()){
            if(jump){
                mc.player.jump();
            }else{
                mc.player.setVelocity(0, height, 0);
            }
        }
    }
}
