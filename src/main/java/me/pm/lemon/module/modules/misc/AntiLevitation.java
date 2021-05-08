package me.pm.lemon.module.modules.misc;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.Hand;

import java.awt.*;

public class AntiLevitation extends Module {
    public AntiLevitation(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color, new SettingSlider("Range", 1, 6, 5, 0),
                new SettingToggle("Auto Aim", true).withDesc("Auto aims on an entity"));
    }

    @EventTarget
    public void onTick(TickEvent event) {
        ClientPlayerEntity player = getPlayer();
        try {
            assert mc.world != null;
            for(Object o: mc.world.getEntities()) {
                Entity e = (Entity) o;

                assert mc.player != null;
                if(mc.player.distanceTo(e) <= getSetting(0).asSlider().getValue()) {
                    if(mc.player.getAttackCooldownProgress(0) >= 1) {
                            boolean checks = (e instanceof ShulkerBulletEntity);
                            if(checks) {
                                player.setSprinting(false);
                                if(getSetting(1).asToggle().state) {
                                    Util.faceEntity(e);
                                }
                                assert mc.interactionManager != null;
                                mc.interactionManager.attackEntity(player, e);
                                player.swingHand(Hand.MAIN_HAND);
                                player.setSprinting(false);
                                break;
                            }
                    }
                }

            }
        }catch(Exception ignored) {}
    }
}
