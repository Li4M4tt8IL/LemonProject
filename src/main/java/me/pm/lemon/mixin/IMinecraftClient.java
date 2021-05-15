package me.pm.lemon.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface IMinecraftClient {

    @Accessor
    void setItemUseCooldown(int itemUseCooldown);

    @Invoker
    void callDoAttack();

    @Invoker
    void callDoItemUse();

    @Accessor
    static int getCurrentFps() {
        throw new UnsupportedOperationException("Untransformed mixin!");
    }

    @Accessor("session")
    public Session getSession();

    @Accessor("session")
    public void setSession(Session session);
}