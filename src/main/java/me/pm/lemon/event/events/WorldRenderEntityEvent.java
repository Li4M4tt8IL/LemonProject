package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class WorldRenderEntityEvent extends Event {

    public Entity entity;
    public MatrixStack matrix;
    public VertexConsumerProvider vertex;
    public BufferBuilderStorage buffers;

    public WorldRenderEntityEvent(Entity entity, MatrixStack matrix, VertexConsumerProvider vertex, BufferBuilderStorage buffers) {
        this.entity = entity;
        this.matrix = matrix;
        this.vertex = vertex;
        this.buffers = buffers;
    }

}
