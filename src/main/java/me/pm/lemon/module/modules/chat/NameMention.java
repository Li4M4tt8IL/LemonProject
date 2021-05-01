package me.pm.lemon.module.modules.chat;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.ReadPacketEvent;
import me.pm.lemon.gui.testScreen.settings.Setting;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.sound.SoundEvents;

import java.awt.*;

public class NameMention extends Module {
    public NameMention(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @EventTarget
    public void sendPacket(ReadPacketEvent event) {
        Packet packet = event.getPacket();
        if(packet instanceof ChatMessageC2SPacket) {
            String message = ((ChatMessageC2SPacket) packet).getChatMessage();
            if(message.contains(mc.player.getName().getString())) {
                mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_ANVIL_LAND, 1.0F));
            }
        }
    }
}
