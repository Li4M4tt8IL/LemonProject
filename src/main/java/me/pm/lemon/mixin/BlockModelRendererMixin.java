package me.pm.lemon.mixin;

import me.pm.lemon.event.events.BlockRenderEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Blocks are still tessellated even if they're transparent because Minecraft's
 * rendering engine is retarded.
 */
@Mixin(BlockModelRenderer.class)
public class BlockModelRendererMixin {

    @Inject(method = "renderQuad", at = @At("HEAD"), cancellable = true)
    private void renderQuad(BlockRenderView world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry, BakedQuad quad,
                            float brightness0, float brightness1, float brightness2, float brightness3, int light0, int light1, int light2, int light3, int overlay, CallbackInfo ci) {
        try {
            BlockRenderEvent event = new BlockRenderEvent(state, pos);
            event.call();
            if(event.isCancelled()) ci.cancel();
//            Xray xray = (Xray) Module.getModule(Xray.class);
//            if (!xray.isVisible(state.getBlock())) {
//                //ci.setReturnValue(false);
//                ci.cancel();
//            }
        } catch (Exception ignored) {
        }
    }

    /* @ModifyArg(method = "render", at = @At(value = "HEAD", target =
     * "Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z"
     * )) private boolean renderSmooth(boolean boolean_1) { try { if
     * (ModuleManager.getModule(Xray.class).isToggled()) return false; } catch
     * (Exception ignored) {} return boolean_1; }
     *
     * @ModifyArg(method = "render", at = @At(value = "HEAD", target =
     * "Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z"
     * )) private boolean renderFlat(boolean boolean_1) { try { if
     * (ModuleManager.getModule(Xray.class).isToggled()) return false; } catch
     * (Exception ignored) {}
     *
     * return boolean_1; } */

}
