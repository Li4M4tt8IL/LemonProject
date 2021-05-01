package me.pm.lemon.mixin;

import com.mojang.authlib.GameProfile;
import me.pm.lemon.event.Event;
import me.pm.lemon.event.events.ClientMoveEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.player.Freecam;
import me.pm.lemon.module.modules.world.Scaffold;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow
    protected MinecraftClient client;

    @Shadow
    protected void autoJump(float float_1, float float_2) {
    }


    @Inject(at = @At("INVOKE"), method = "tick")
    public void tick(CallbackInfo info) {
        Event event = new TickEvent();
        event.call();
        if (event.isCancelled()) info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "move", cancellable = true)
    public void move(MovementType movementType_1, Vec3d vec3d_1, CallbackInfo info) {
        ClientMoveEvent event = new ClientMoveEvent(movementType_1, vec3d_1);
        event.call();
        if (event.isCancelled()) {
            info.cancel();
        } else if (!movementType_1.equals(event.type) || !vec3d_1.equals(event.vec3d)) {
            double double_1 = this.getX();
            double double_2 = this.getZ();
            super.move(event.type, event.vec3d);
            this.autoJump((float) (this.getX() - double_1), (float) (this.getZ() - double_2));
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "pushOutOfBlocks", cancellable = true)
    protected void pushOutOfBlocks(double double_1, double double_2, CallbackInfo ci) {
        if (Module.getModule(Freecam.class).isToggled()) {
            ci.cancel();
        }
    }

    @Override
    protected boolean clipAtLedge() {
        return super.clipAtLedge()/* || Module.getModule(SafeWalk.class).isToggled() */
                || (Module.getModule(Scaffold.class).isToggled() && Module.getModule(Scaffold.class).getSetting(3).asToggle().state);
    }
}
