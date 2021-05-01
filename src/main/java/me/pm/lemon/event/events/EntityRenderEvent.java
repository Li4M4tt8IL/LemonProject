package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;
import net.minecraft.entity.Entity;

public class EntityRenderEvent extends Event {
    protected Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public static class Render extends EntityRenderEvent {
        public Render(Entity entity) {
            this.entity = entity;
        }
    }

    public static class Label extends EntityRenderEvent {
        public Label(Entity entity) {
            this.entity = entity;
        }
    }
}
