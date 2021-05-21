package me.pm.lemon.module.modules.client;

import me.pm.lemon.Main;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ForcedStuff extends Module {

    public ForcedStuff() {
        super("API", Category.CLIENT, "Shows players that are using the same mod", -1, Colors.CLIENT);
    }

    private static Identifier TEXTURE = new Identifier(Main.MOD_ID, "32x32.png");

    @EventTarget
    public void onLivingRender(EntityRenderEvent.Label event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) event.getEntity();
            if(e instanceof PlayerEntity) {
                if(Main.getNames().contains(e.getUuidAsString())) {
//                    WorldRenderUtils.drawIcon(TEXTURE, e.getDisplayName().getString(),
//                            e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (1),
//                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), 0.5);
                }
            }
        }
    }
}
