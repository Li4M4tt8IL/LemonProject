package me.pm.lemon.mixin;

import me.pm.lemon.event.events.EntityRenderEvent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderEvent.Render.class)
abstract class RendererMixin<T extends Entity> {
    @Shadow
    protected abstract boolean bindEntityTexture(T entity);
}
