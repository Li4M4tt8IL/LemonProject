package me.pm.lemon.module.modules.combat;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.event.events.*;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

import java.awt.*;

public class KillAura extends Module {
    public KillAura(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Aura Range", 1, 5, 4, 1).withDesc("Hit range"),
                new SettingToggle("Player", true).withDesc("Hit players"),
                new SettingToggle("Monster", true).withDesc("Hit monsters"),
                new SettingToggle("Passive", true).withDesc("Hit animals"),
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
                            if(getSetting(1).asToggle().state) {
                                boolean checks = !getFriendManager().isFriend(e.getName().getString())
                                        && !(e instanceof ClientPlayerEntity)
                                        && (e instanceof PlayerEntity)
                                        && ((LivingEntity)e).getHealth() > 0;
                                if(checks) {
                                    player.setSprinting(false);
                                    if(getSetting(4).asToggle().state) {
                                        Util.faceEntity(e);
                                    }
                                    assert mc.interactionManager != null;
                                    mc.interactionManager.attackEntity(player, e);
                                    player.swingHand(Hand.MAIN_HAND);
                                    player.setSprinting(false);
                                    break;
                                }
                            }
                            if(getSetting(2).asToggle().state) {
                                boolean checks = (e instanceof Monster)
                                        && ((LivingEntity) e).getHealth() > 0;
                                if(checks) {
                                    player.setSprinting(false);
                                    if(getSetting(4).asToggle().state) {
                                        Util.faceEntity(e);
                                    }
                                    assert mc.interactionManager != null;
                                    mc.interactionManager.attackEntity(player, e);
                                    player.swingHand(Hand.MAIN_HAND);
                                    break;
                                }
                            }
                            if(getSetting(3).asToggle().state) {
                                boolean checks = !(e instanceof PlayerEntity)
                                        && (EntityUtil.isAnimal(e))
                                        && ((LivingEntity) e).getHealth() > 0;
                                if(checks) {
                                    player.setSprinting(false);
                                    if(getSetting(4).asToggle().state) {
                                        Util.faceEntity(e);
                                    }
                                    assert mc.interactionManager != null;
                                    mc.interactionManager.attackEntity(player, e);
                                    player.swingHand(Hand.MAIN_HAND);
                                    break;
                                }
                            }
                        }
                    }

                }
            }catch(Exception ignored) {}
        }
}
