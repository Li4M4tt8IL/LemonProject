package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class Jesus extends Module {
    public Jesus(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        Entity e = mc.player.getVehicle() != null ? mc.player.getVehicle() : mc.player;

        if(e.isSneaking() || e.fallDistance > 3f) return;

        if(Util.isFluid(new BlockPos(e.getPos().add(0, 0.3, 0)))) {
            e.setVelocity(e.getVelocity().x, 0.08, e.getVelocity().z);
        } else if(Util.isFluid(new BlockPos(e.getPos().add(0, 0.1, 0)))) {
            e.setVelocity(e.getVelocity().x, 0.05, e.getVelocity().z);
        } else if(Util.isFluid(new BlockPos(e.getPos().add(0, 0.05, 0)))) {
            e.setVelocity(e.getVelocity().x, 0.01, e.getVelocity().z);
        } else if(Util.isFluid(new BlockPos(e.getPos()))) {
            e.setVelocity(e.getVelocity().x, -0.005, e.getVelocity().z);
            e.setOnGround(true);
        }
    }
}
