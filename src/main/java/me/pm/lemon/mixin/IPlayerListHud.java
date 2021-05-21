package me.pm.lemon.mixin;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.hud.PlayerListHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerListHud.class)

public interface IPlayerListHud {
    @Accessor("ENTRY_ORDERING")
    public Ordering getOrdering();
}
