package me.pm.lemon.mixin;

import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.render.Chams;
import me.pm.lemon.utils.LemonLogger;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class RenderLivingMixin<T extends LivingEntity> {

    @Inject(method = "render", at = @At("HEAD"))
    private <T extends LivingEntity> void injectChamsPre(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, final CallbackInfo callbackInfo) {
        try {
            if (Module.getModule(Chams.class) != null && Module.getModule(Chams.class).isToggled()) {
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -1000000.0f);
            }
        } catch (Exception e) {
            LemonLogger.errorMessage("Something went wrong! " + e.getMessage());
        }

    }

    @Inject(method = "render", at = @At("RETURN"))
    private <T extends LivingEntity> void injectChamsPost(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, final CallbackInfo callbackInfo) {
        try {
            if (Module.getModule(Chams.class) != null && Module.getModule(Chams.class).isToggled()) {
                GL11.glPolygonOffset(1.0f, 1000000.0f);
                GL11.glDisable(32823);
            }
        } catch (Exception e) {
            LemonLogger.errorMessage("Something went wrong! " + e.getMessage());
        }
    }
}
