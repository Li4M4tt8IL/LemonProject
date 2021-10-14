package me.pm.lemon.mixin;

import me.pm.lemon.Main;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.misc.FakeVanilla;
import net.minecraft.network.NetworkState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandshakeC2SPacket.class)
public class C00HandshadeMixin {
    @Shadow
    int protocolVersion;
    @Shadow
    String address;
    @Shadow
    int port;
    @Shadow
    NetworkState intendedState;

    @Inject(method = "write", at = @At(value = "HEAD"), cancellable = true)
    public void write(PacketByteBuf buf, CallbackInfo info) {
        if (Module.getModule(FakeVanilla.class).isToggled()) {
            info.cancel();
            buf.writeVarInt(protocolVersion);
            buf.writeString(address);
            buf.writeShort(port);
            buf.writeVarInt(intendedState.getId());

            System.out.println(this.protocolVersion + " " + this.address + " " + this.port + " " + this.intendedState.getId());
            return;
        }
    }
}

