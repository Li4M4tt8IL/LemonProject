package me.pm.lemon.mixin;

import me.pm.lemon.event.events.DrawOverlayEvent;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HUDMixin {

    @Inject(at = @At(value = "RETURN"), method = "render", cancellable = true)
    public void render(MatrixStack matrixStack, float float_1, CallbackInfo info) {
        DrawOverlayEvent event = new DrawOverlayEvent(matrixStack);
        event.call();
        if (event.isCancelled()) info.cancel();
    }
}
