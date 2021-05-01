package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;
import net.minecraft.network.Packet;

public class ReadPacketEvent extends Event {
    private Packet<?> packet;

    public ReadPacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}
