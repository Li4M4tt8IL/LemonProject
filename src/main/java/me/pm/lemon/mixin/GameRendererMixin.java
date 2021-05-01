package me.pm.lemon.mixin;

import me.pm.lemon.event.events.WorldRenderEvent;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(at = @At("HEAD"), method = "renderHand", cancellable = true)
    private void renderHand(MatrixStack matrixStack_1, Camera camera, float tickDelta, CallbackInfo info) {
        WorldRenderEvent event = new WorldRenderEvent(tickDelta);
        event.call();
        if (event.isCancelled()) info.cancel();
    }


//    @Inject(
//            at = @At("HEAD"),
//            method = "bobViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V",
//            cancellable = true)
//    private void onBobViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo ci) {
//        if (ModuleManager.getModule(NoRender.class).isToggled() && ModuleManager.getModule(NoRender.class).getSetting(2).asToggle().state)
//            ci.cancel();
//    }
//
//    @Inject(at = @At("HEAD"), method = "showFloatingItem", cancellable = true)
//    private void showFloatingItem(ItemStack itemStack_1, CallbackInfo ci) {
//        if (ModuleManager.getModule(NoRender.class).isToggled() && ModuleManager.getModule(NoRender.class).getSetting(8).asToggle().state
//                && itemStack_1.getItem() == Items.TOTEM_OF_UNDYING)
//            ci.cancel();
//    }
//
//    @Redirect(
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0),
//            method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V")
//    private float nauseaWobble(float delta, float first, float second) {
//        if (!(ModuleManager.getModule(NoRender.class).isToggled() && ModuleManager.getModule(NoRender.class).getSetting(6).asToggle().state))
//            return MathHelper.lerp(delta, first, second);
//
//        return 0;
//    }
//
//    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"))
//    private HitResult updateTargetedEntityEntityRayTraceProxy(Entity entity, double maxDistance, float tickDelta, boolean includeFluids) {
//        if (ModuleManager.getModule(LiquidInteract.class).isToggled()) return entity.raycast(maxDistance, tickDelta, true);
//        return entity.raycast(maxDistance, tickDelta, includeFluids);
//    }
}
