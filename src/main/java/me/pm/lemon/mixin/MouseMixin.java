package me.pm.lemon.mixin;

import me.pm.lemon.module.modules.hud.PerspectiveMod;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({Mouse.class})
public class MouseMixin {
    public MouseMixin() {
    }

    @Inject(
            method = {"updateMouse"},
            at = {@At(
                    value = "INVOKE",
                    target = "net/minecraft/client/tutorial/TutorialManager.onUpdateMouse(DD)V"
            )},
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void perspectiveUpdatePitchYaw(CallbackInfo info, double adjustedSens, double x, double y, int invert) {
        if (PerspectiveMod.perspectiveEnabled) {
            PerspectiveMod.cameraYaw = (float)((double)PerspectiveMod.cameraYaw + x / 8.0D);
            PerspectiveMod.cameraPitch = (float)((double)PerspectiveMod.cameraPitch + y * (double)invert / 8.0D);
            if (Math.abs(PerspectiveMod.cameraPitch) > 90.0F) {
                PerspectiveMod.cameraPitch = PerspectiveMod.cameraPitch > 0.0F ? 90.0F : -90.0F;
            }
        }
    }

    @Redirect(
            method = {"updateMouse"},
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/network/ClientPlayerEntity.changeLookDirection(DD)V"
            )
    )
    private void perspectivePreventPlayerMovement(ClientPlayerEntity player, double x, double y) {
        if (!PerspectiveMod.perspectiveEnabled) {
            player.changeLookDirection(x, y);
        }
    }
}
