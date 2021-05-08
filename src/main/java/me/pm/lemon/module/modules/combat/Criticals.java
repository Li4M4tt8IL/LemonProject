package me.pm.lemon.module.modules.combat;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.particle.ParticleTypes;
import me.pm.lemon.event.events.*;

import java.awt.*;
import java.util.Random;

public class Criticals extends Module {
    public Criticals(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingMode("Mode", "Packet", "Legit"));
    }

    @EventTarget
    public void sendPacket(SendPacketEvent event) {
        if(getSetting(0).asMode().mode == 0) {
            this.setDisplayName("Criticals \2477Packet");
        }
        if(getSetting(0).asMode().mode == 1) {
            this.setDisplayName("Criticals \2477Legit");
        }
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket packet = (PlayerInteractEntityC2SPacket) event.getPacket();
            if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.ATTACK) {
                if(getSetting(0).asMode().mode == 0) {
                    this.doCritical();
                }
                if(getSetting(0).asMode().mode == 1) {
                    doLegitCrit();
                }

                Entity e = packet.getEntity(mc.world);
                Random r = new Random();
                for (int i = 0; i < 10; i++) {
                    mc.particleManager.addParticle(ParticleTypes.CRIT, e.getX(), e.getY() + e.getHeight() / 2, e.getZ(),
                            r.nextDouble() - 0.5, r.nextDouble() - 0.5, r.nextDouble() - 0.5);
                }
            }
        }
    }

    public void doCritical() {
        if (!mc.player.isOnGround()) return;
        if (mc.player.isInLava() || mc.player.isSubmergedInWater()) return;
        double posX = mc.player.getX();
        double posY = mc.player.getY();
        double posZ = mc.player.getZ();
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(posX, posY + 0.0625, posZ, true));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(posX, posY, posZ, false));
    }

    public void doLegitCrit() {
        if (!mc.player.isOnGround()) return;
        if (mc.player.isInLava() || mc.player.isSubmergedInWater()) return;
        mc.player.setVelocity(0, 0.1, 0);
        mc.player.fallDistance = 0.1F;
        mc.player.setOnGround(false);
    }
}
