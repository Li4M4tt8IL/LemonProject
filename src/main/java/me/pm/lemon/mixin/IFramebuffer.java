package me.pm.lemon.mixin;

import net.minecraft.client.gl.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Framebuffer.class)
public interface IFramebuffer {
    @Accessor("depthAttachment")
    public void setDepth(int depth);
}
