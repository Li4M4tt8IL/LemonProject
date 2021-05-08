package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.event.events.WorldRenderEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.ProjectileSimulator;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Trajectories extends Module {
    public Trajectories(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingMode("Draw", "Line", "Dots").withDesc("How to draw the line where the projectile is going"),
                new SettingToggle("Throwables", true).withDesc("Shows snowballs/eggs/epearls"),
                new SettingToggle("XP Bottles", true).withDesc("Shows XP bottles"),
                new SettingToggle("Potions", true).withDesc("Shows splash/lingering potions"),
                new SettingToggle("Flying", true).withDesc("Shows trajectories for flying projectiles").withChildren(
                        new SettingToggle("Throwables", true).withDesc("Shows flying snowballs/eggs/epearls"),
                        new SettingToggle("XP Bottles", true).withDesc("Shows flying XP bottles"),
                        new SettingToggle("Potions", true).withDesc("Shows flying splash/lingering potions")),
                new SettingToggle("Other Players", false).withDesc("Show other players trajectories"),
                new SettingColor("Color", 0.333f, 0.333f, 1f, false),
                new SettingSlider("Thick", 0.1, 5, 2, 2));
    }

    public final List<Triple<List<Vec3d>, Entity, BlockPos>> poses = new ArrayList<>();


    @EventTarget
    public void onTick(TickEvent event) {
        poses.clear();

        Entity entity = ProjectileSimulator.summonProjectile(
                mc.player, getSetting(1).asToggle().state, getSetting(2).asToggle().state, getSetting(3).asToggle().state);

        if (entity != null) {
            poses.add(ProjectileSimulator.simulate(entity));
        }

        if (getSetting(4).asToggle().state) {
            for (Entity e : mc.world.getEntities()) {
                if (e instanceof ProjectileEntity) {
                    if (!getSetting(4).asToggle().getChild(0).asToggle().state
                            && (e instanceof SnowballEntity || e instanceof EggEntity || e instanceof EnderPearlEntity)) {
                        continue;
                    }

                    if (!getSetting(4).asToggle().getChild(1).asToggle().state && e instanceof ExperienceBottleEntity) {
                        continue;
                    }

                    Triple<List<Vec3d>, Entity, BlockPos> p = ProjectileSimulator.simulate(e);

                    if (p.getLeft().size() >= 2) poses.add(p);
                }
            }
        }

        if (getSetting(5).asToggle().state) {
            for (PlayerEntity e : mc.world.getPlayers()) {
                if (e == mc.player) continue;
                Entity proj = ProjectileSimulator.summonProjectile(
                        e, getSetting(1).asToggle().state, getSetting(2).asToggle().state, getSetting(3).asToggle().state);

                if (proj != null) {
                    poses.add(ProjectileSimulator.simulate(proj));
                }
            }

        }
    }

    @EventTarget
    public void onWorldRender(WorldRenderEvent event) {
        float[] col = getSetting(6).asColor().getRGBFloat();

        for (Triple<List<Vec3d>, Entity, BlockPos> t : poses) {
            if (t.getLeft().size() >= 2) {
                if (getSetting(0).asMode().mode == 0) {
                    for (int i = 1; i < t.getLeft().size(); i++) {
                        RenderUtils.drawLine(t.getLeft().get(i - 1).x, t.getLeft().get(i - 1).y, t.getLeft().get(i - 1).z,
                                t.getLeft().get(i).x, t.getLeft().get(i).y, t.getLeft().get(i).z, col[0], col[1], col[2],
                                (float) getSetting(7).asSlider().getValue());
                    }
                } else {
                    for (Vec3d v : t.getLeft()) {
                        RenderUtils.drawFilledBox(new Box(v.x - 0.1, v.y - 0.1, v.z - 0.1, v.x + 0.1, v.y + 0.1, v.z + 0.1),
                                col[0], col[1], col[2], 0.75f);
                    }
                }
            }

            if (t.getMiddle() != null) {
                RenderUtils.drawFilledBox(t.getMiddle().getBoundingBox(), col[0], col[1], col[2], 0.75f);
            }

            if (t.getRight() != null) {
                RenderUtils.drawFilledBox(t.getRight(), col[0], col[1], col[2], 0.75f);
            }
        }
    }
}
