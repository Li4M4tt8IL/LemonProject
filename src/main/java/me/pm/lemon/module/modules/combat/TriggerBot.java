package me.pm.lemon.module.modules.combat;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

import java.awt.*;
import java.util.Optional;

public class TriggerBot extends Module {
    public TriggerBot(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color, new SettingSlider("Range", 1, 5, 3, 1),
                new SettingToggle("Player", true),
                new SettingToggle("Monster", true),
                new SettingToggle("Passive", true));
    }

    @EventTarget
    public void onTick(TickEvent event) {
        Optional<Entity> lookingAt = DebugRenderer.getTargetedEntity(mc.player, (int) getSetting(0).asSlider().getValue());

        if(lookingAt.isPresent()) {
            Entity e = lookingAt.get();
            assert mc.player != null;
            assert mc.interactionManager != null;
            if(e instanceof LivingEntity) {
                if(mc.player.getAttackCooldownProgress(mc.getTickDelta()) >= 1) {
                    if(e instanceof PlayerEntity && getSetting(1).asToggle().state) {
                        if(!getFriendManager().isFriend(e.getName().getString())) {
                            mc.interactionManager.attackEntity(mc.player, e);
                            mc.player.swingHand(Hand.MAIN_HAND);
                        }
                    } else if(e instanceof Monster && getSetting(2).asToggle().state) {
                        mc.interactionManager.attackEntity(mc.player, e);
                        mc.player.swingHand(Hand.MAIN_HAND);
                    } else if(EntityUtil.isAnimal(e) && getSetting(3).asToggle().state) {
                        mc.interactionManager.attackEntity(mc.player, e);
                        mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        }
    }
}
