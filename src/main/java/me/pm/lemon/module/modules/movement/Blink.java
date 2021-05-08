package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.SendPacketEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.PlayerCopyEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.awt.*;
import java.util.ArrayDeque;

public class Blink extends Module {
    public Blink(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color, new SettingSlider("Packet Limit", 0, 500, 0, 0)
                .withDesc("Zostaw na 0 jak nie wiesz co to jest to nie ruszaj!"));
    }

    private PlayerCopyEntity dummy;
    private final ArrayDeque<PlayerMoveC2SPacket> packets = new ArrayDeque<>();

    @Override
    public void onEnable() {
        dummy = new PlayerCopyEntity();
        dummy.copyPositionAndRotation(mc.player);
        dummy.setBoundingBox(dummy.getBoundingBox().expand(0.1));

        dummy.spawn();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        dummy.despawn();
        packets.forEach(p -> mc.player.networkHandler.sendPacket(p));
        packets.clear();
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(TickEvent event) {
        if(getSetting(0).asSlider().getValue() == 0)
            return;

        if(packets.size() >= getSetting(0).asSlider().getValue())
        {
            cancel();
        }
    }

    @EventTarget
    public void onSentPacket(SendPacketEvent event)
    {
        if(!this.isToggled()) return;
        if(!(event.getPacket() instanceof PlayerMoveC2SPacket))
            return;

        event.setCancelled(true);

        PlayerMoveC2SPacket packet = (PlayerMoveC2SPacket)event.getPacket();
        PlayerMoveC2SPacket prevPacket = packets.peekLast();

        if(prevPacket != null && packet.isOnGround() == prevPacket.isOnGround()
                && packet.getYaw(-1) == prevPacket.getYaw(-1)
                && packet.getPitch(-1) == prevPacket.getPitch(-1)
                && packet.getX(-1) == prevPacket.getX(-1)
                && packet.getY(-1) == prevPacket.getY(-1)
                && packet.getZ(-1) == prevPacket.getZ(-1))
            return;

        packets.addLast(packet);
    }

    public void cancel()
    {
        packets.clear();
        dummy.despawn();
        toggle();
    }
}
