package me.pm.lemon.mixin;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.pm.lemon.event.events.*;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
    private void onKeyEvent(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        //if (InputUtil.getKeycodeName(InputUtil.fromKeyCode(key, scanCode).getKeyCode()) != null && InputUtil.getKeycodeName(InputUtil.fromKeyCode(key, scanCode).getKeyCode()).equals(CommandManager.prefix)) {
        //    MinecraftClient.getInstance().openScreen(new ChatScreen(CommandManager.prefix));
        //}

        if (key != -1) {
//            Main.checkIfValid();
            KeyPressEvent event = new KeyPressEvent(key, scanCode);
            event.call();
            if (event.isCancelled()) callbackInfo.cancel();
        }
    }
}
