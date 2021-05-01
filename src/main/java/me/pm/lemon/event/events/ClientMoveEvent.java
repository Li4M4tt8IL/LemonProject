package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public class ClientMoveEvent extends Event {
    public MovementType type;
    public Vec3d vec3d;

    public ClientMoveEvent(MovementType type, Vec3d vec3d) {
        this.type = type;
        this.vec3d = vec3d;
    }
}
