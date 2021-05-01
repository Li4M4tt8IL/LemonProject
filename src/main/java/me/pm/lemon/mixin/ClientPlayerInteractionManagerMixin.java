package me.pm.lemon.mixin;

import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.exploits.SpeedMine;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow
    private MinecraftClient client;
    @Shadow
    private float currentBreakingProgress;
    @Shadow
    private boolean breakingBlock;

    /**
     * blockHitDelay
     */
    @Shadow
    private int blockBreakingCooldown;

    private boolean overrideReach;

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", ordinal = 3))
    public void updateBlockBreakingProgress(ClientPlayerInteractionManager clientPlayerInteractionManager, int i) {
        i = Module.getModule(SpeedMine.class).isToggled()
                && Module.getModule(SpeedMine.class).getSetting(0).asMode().mode == 1
                ? (int) Module.getModule(SpeedMine.class).getSetting(2).asSlider().getValue() : 5;

        this.blockBreakingCooldown = i;
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", ordinal = 4))
    public void updateBlockBreakingProgress2(ClientPlayerInteractionManager clientPlayerInteractionManager, int i) {
        i = Module.getModule(SpeedMine.class).isToggled()
                && Module.getModule(SpeedMine.class).getSetting(0).asMode().mode == 1
                ? (int) Module.getModule(SpeedMine.class).getSetting(2).asSlider().getValue() : 5;

        this.blockBreakingCooldown = i;
    }

    @Redirect(method = "attackBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I"))
    public void attackBlock(ClientPlayerInteractionManager clientPlayerInteractionManager, int i) {
        i = Module.getModule(SpeedMine.class).isToggled()
                && Module.getModule(SpeedMine.class).getSetting(0).asMode().mode == 1
                ? (int) Module.getModule(SpeedMine.class).getSetting(2).asSlider().getValue() : 5;

        this.blockBreakingCooldown = i;
    }
}
