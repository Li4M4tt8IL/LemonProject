package me.pm.lemon.mixin;

import me.pm.lemon.Main;
import me.pm.lemon.event.events.WorldRenderEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    private static Boolean currentlyZoomed = false;
    private static Boolean originalSmoothCameraEnabled = false;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    @Inject(at = @At("HEAD"), method = "renderHand", cancellable = true)
    private void renderHand(MatrixStack matrixStack_1, Camera camera, float tickDelta, CallbackInfo info) {
        WorldRenderEvent event = new WorldRenderEvent(tickDelta);
        event.call();
        if (event.isCancelled()) info.cancel();
    }

    @Inject(
            method = {"getFov(Lnet/minecraft/client/render/Camera;FZ)D"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
        if (isZooming()) {
            callbackInfo.setReturnValue(19.0D);
        }

        manageSmoothCamera();
    }

    private static Boolean isZooming() {
        return Main.ClientKeys.zoomKey.isPressed();
    }

    private static void manageSmoothCamera() {
        if (zoomStarting()) {
            zoomStarted();
            enableSmoothCamera();
        }

        if (zoomStopping()) {
            zoomStopped();
            resetSmoothCamera();
        }

    }

    private static Boolean isSmoothCamera() {
        return mc.options.smoothCameraEnabled;
    }

    private static void enableSmoothCamera() {
        mc.options.smoothCameraEnabled = true;
    }

    private static void disableSmoothCamera() {
        mc.options.smoothCameraEnabled = false;
    }

    private static boolean zoomStarting() {
        return isZooming() && !currentlyZoomed;
    }

    private static boolean zoomStopping() {
        return !isZooming() && currentlyZoomed;
    }

    private static void zoomStarted() {
        originalSmoothCameraEnabled = isSmoothCamera();
        currentlyZoomed = true;
    }

    private static void zoomStopped() {
        currentlyZoomed = false;
    }

    private static void resetSmoothCamera() {
        if (originalSmoothCameraEnabled) {
            enableSmoothCamera();
        } else {
            disableSmoothCamera();
        }
    }
}
