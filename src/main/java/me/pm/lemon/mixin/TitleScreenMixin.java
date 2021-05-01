package me.pm.lemon.mixin;

import me.pm.lemon.gui.autoUpdate.AutoUpdateScreen;
import me.pm.lemon.utils.UpdateChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at=@At("RETURN"), method = "initWidgetsNormal")
    private void addCustomButton(int y, int spacingY, CallbackInfo ci) {
//        this.addButton(new ButtonWidget(this.width / 2 - 100 + 205, y, 20, 20, Text.of("PM"), (buttonWidget) -> {
//            this.client.openScreen(new SelectWorldScreen(this));
//        }));
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) throws IOException {
//        if(!UpdateChecker.isUpToDate()) {
//            MinecraftClient.getInstance().openScreen(new AutoUpdateScreen(Text.of("update.auto")));
//        }

//        TitleScreenEvent event = new TitleScreenEvent();
//        event.call();

//        if(event.isCancelled()) info.cancel();
    }
}
