package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.WorldRenderEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class Tracers extends Module {
    public Tracers(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Players", true).withDesc("Show Player Tracers").withChildren(
                        new SettingColor("Player Color", 1f, 0.3f, 0.3f, false).withDesc("Tracer color for players"),
                        new SettingColor("Friend Color", 0f, 1f, 1f, false).withDesc("Tracer color for friends")),
                new SettingToggle("Mobs", false).withDesc("Show Mob Tracers").withChildren(
                        new SettingColor("Color", 0.5f, 0.1f, 0.5f, false).withDesc("Tracer color for mobs")),
                new SettingToggle("Animals", false).withDesc("Show Animal Tracers").withChildren(
                        new SettingColor("Color", 0.3f, 1f, 0.3f, false).withDesc("Tracer color for animals")),
                new SettingToggle("Items", true).withDesc("Show Item Tracers").withChildren(
                        new SettingColor("Color", 1f, 0.8f, 0.2f, false).withDesc("Tracer color for items")),
                new SettingToggle("Crystals", true).withDesc("Show End Crystal Tracers").withChildren(
                        new SettingColor("Color", 1f, 0.2f, 1f, false).withDesc("Tracer color for crystals")),
                new SettingToggle("Vehicles", false).withDesc("Show Vehicle Tracers").withChildren(
                        new SettingColor("Color", 0.6f, 0.6f, 0.6f, false).withDesc("Tracer color for vehicles (minecarts/boats)")),
                new SettingSlider("Thick", 0.1, 5, 1.5, 1));
    }

    @EventTarget
    public void onRender(WorldRenderEvent event) {
        assert mc.world != null;
        for (Entity e : mc.world.getEntities()) {
            Vec3d vec = e.getPos();

            assert mc.cameraEntity != null;
            Vec3d vec2 = new Vec3d(0, 0, 75).rotateX(-(float) Math.toRadians(mc.cameraEntity.pitch))
                    .rotateY(-(float) Math.toRadians(mc.cameraEntity.yaw))
                    .add(mc.cameraEntity.getPos().add(0, mc.cameraEntity.getEyeHeight(mc.cameraEntity.getPose()), 0));

            float[] col = null;

            if (e instanceof PlayerEntity && e != mc.player && e != mc.cameraEntity && getSetting(0).asToggle().state) {
                col = getSetting(0).asToggle().getChild(getFriendManager().isFriend(e.getName().asString()) ? 1 : 0).asColor().getRGBFloat();
            } else if (e instanceof Monster && getSetting(1).asToggle().state) {
                col = getSetting(1).asToggle().getChild(0).asColor().getRGBFloat();
            } else if (EntityUtil.isAnimal(e) && getSetting(2).asToggle().state) {
                col = getSetting(2).asToggle().getChild(0).asColor().getRGBFloat();
            } else if (e instanceof ItemEntity && getSetting(3).asToggle().state) {
                col = getSetting(3).asToggle().getChild(0).asColor().getRGBFloat();
            } else if (e instanceof EndCrystalEntity && getSetting(4).asToggle().state) {
                col = getSetting(4).asToggle().getChild(0).asColor().getRGBFloat();
            } else if ((e instanceof BoatEntity || e instanceof AbstractMinecartEntity) && getSetting(5).asToggle().state) {
                col = getSetting(5).asToggle().getChild(0).asColor().getRGBFloat();
            }

            if (col != null) {
                RenderUtils.drawLine(vec2.x, vec2.y, vec2.z, vec.x, vec.y, vec.z, col[0], col[1], col[2], (float) getSetting(6).asSlider().getValue());
                RenderUtils.drawLine(vec.x, vec.y, vec.z, vec.x, vec.y + (e.getHeight() / 1.1), vec.z, col[0], col[1], col[2], (float) getSetting(6).asSlider().getValue());
            }
        }
    }
}
