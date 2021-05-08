package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.ClientMoveEvent;
import me.pm.lemon.event.events.OpenScreenEvent;
import me.pm.lemon.event.events.SendPacketEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.PlayerCopyEntity;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class Freecam extends Module {
    public Freecam(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Speed", 0.1, 3, 0.1, 1),
                new SettingToggle("Horse Inventory", true));
    }

    private PlayerCopyEntity dummy;
    private double[] playerPos;
    private float[] playerRot;
    private Entity riding;

    private boolean prevFlying;
    private float prevFlySpeed;

    @Override
    public void onEnable() {
        playerPos = new double[]{mc.player.getX(), mc.player.getY(), mc.player.getZ()};
        playerRot = new float[]{mc.player.yaw, mc.player.pitch};

        dummy = new PlayerCopyEntity();
        dummy.copyPositionAndRotation(mc.player);
        dummy.setBoundingBox(dummy.getBoundingBox().expand(0.1));

        dummy.spawn();

        if (mc.player.getVehicle() != null) {
            riding = mc.player.getVehicle();
            mc.player.getVehicle().removeAllPassengers();
        }

        if (mc.player.isSprinting()) {
            mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
        }

        prevFlying = mc.player.abilities.flying;
        prevFlySpeed = mc.player.abilities.getFlySpeed();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        dummy.despawn();
        mc.player.noClip = false;
        mc.player.abilities.flying = prevFlying;
        mc.player.abilities.setFlySpeed(prevFlySpeed);

        mc.player.refreshPositionAndAngles(playerPos[0], playerPos[1], playerPos[2], playerRot[0], playerRot[1]);
        mc.player.setVelocity(Vec3d.ZERO);

        if (riding != null && mc.world.getEntityById(riding.getEntityId()) != null) {
            mc.player.startRiding(riding);
        }

        super.onDisable();
    }

    @EventTarget
    public void sendPacket(SendPacketEvent event) {
        if (event.getPacket() instanceof ClientCommandC2SPacket || event.getPacket() instanceof PlayerMoveC2SPacket) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onOpenScreen(OpenScreenEvent event) {
        if (getSetting(1).asToggle().state && riding instanceof HorseBaseEntity) {
            if (event.getScreen() instanceof InventoryScreen) {
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.OPEN_INVENTORY));
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onClientMove(ClientMoveEvent event) {
        mc.player.noClip = true;
    }

    @EventTarget
    public void onTick(TickEvent event) {
        //mc.player.setSprinting(false);
        //mc.player.setVelocity(Vec3d.ZERO);
        mc.player.setOnGround(false);
        mc.player.abilities.setFlySpeed((float) (getSetting(0).asSlider().getValue() / 5));
        mc.player.abilities.flying = true;
    }
}
