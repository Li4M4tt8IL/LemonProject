package me.pm.lemon.mixin;

import me.pm.lemon.module.modules.hud.PerspectiveMod;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin({Camera.class})
public class CameraMixin {
    @Shadow
    private float pitch;
    @Shadow
    private float yaw;

    public CameraMixin() {
    }

    @Inject(
            method = {"update"},
            at = {@At(
                    value = "INVOKE",
                    target = "net/minecraft/client/render/Camera.moveBy(DDD)V",
                    ordinal = 0
            )}
    )
    private void perspectiveUpdatePitchYaw(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        if (PerspectiveMod.perspectiveEnabled) {
            this.pitch = PerspectiveMod.cameraPitch;
            this.yaw = PerspectiveMod.cameraYaw;
        }

    }

    @ModifyArgs(
            method = {"update"},
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/render/Camera.setRotation(FF)V",
                    ordinal = 0
            )
    )
    private void perspectiveFixRotation(Args args) {
        if (PerspectiveMod.perspectiveEnabled) {
            args.set(0, PerspectiveMod.cameraYaw);
            args.set(1, PerspectiveMod.cameraPitch);
        }

    }
}
