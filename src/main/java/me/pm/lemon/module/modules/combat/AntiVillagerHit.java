package me.pm.lemon.module.modules.combat;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.SendPacketEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

import java.awt.*;

public class AntiVillagerHit extends Module {
    public AntiVillagerHit(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @EventTarget
    public void onPacketSend(SendPacketEvent event) {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket packet = (PlayerInteractEntityC2SPacket) event.getPacket();
            if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.ATTACK) {
                Entity e = packet.getEntity(mc.world);
                if(e instanceof VillagerEntity) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
