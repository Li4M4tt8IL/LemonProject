package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.awt.*;

public class NoFall extends Module {
    public NoFall(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        assert mc.player != null;
        if(mc.player.fallDistance >= 2F) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
        }
    }
}
