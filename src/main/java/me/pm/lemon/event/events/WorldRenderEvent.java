package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;

public class WorldRenderEvent extends Event {
    public final float partialTicks;

    public WorldRenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
