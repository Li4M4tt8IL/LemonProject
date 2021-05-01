package me.pm.lemon.module.modules.misc;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.LemonLogger;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Optional;

public class MiddleClickFriend extends Module {
    public MiddleClickFriend(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
    }

    private boolean antiSpamClick = false;
    private static boolean Clicked = false;

    @EventTarget
    public void onTick(TickEvent event) {
        if (mc.currentScreen != null)
            return;

        int button = GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

        if (GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), button) == 1 && !antiSpamClick) {
            antiSpamClick = true;

            Optional<Entity> lookingAt = DebugRenderer.getTargetedEntity(mc.player, 200);

            if (lookingAt.isPresent()) {
                Entity e = lookingAt.get();

                if (e instanceof PlayerEntity) {
                    if (getFriendManager().isFriend(e.getName().getString())) {
                        getFriendManager().removeFriend(e.getName().getString());
                        LemonLogger.infoMessage("Usunieto gracza \247b" + e.getName().getString() + "\247f ze znajomych!");
                    } else {
                        getFriendManager().addFriend(e.getName().getString());
                        LemonLogger.infoMessage("Dodano gracza \247b" + e.getName().getString() + "\247f do znajomych!");

                    }
//                    if(mc.player.getName().getString().equals(Main.ClientInfo.clientCreators) || Main.debugMode) {
//                        Random r1 = new Random();
//                        Random r2 = new Random((long)System.identityHashCode(new Object()));
//                        BigInteger random1Bi = new BigInteger(128, r1);
//                        BigInteger random2Bi = new BigInteger(128, r2);
//                        BigInteger serverBi = random1Bi.xor(random2Bi);
//                        String serverId = serverBi.toString(16);
//                        String url = String.format("https://www.optifine.net/capeChange?u=%s&n=%s&s=%s", e.getUuidAsString().replace("-", ""), e.getName().getString(), serverId);
//                        PMLogger.urlMessage(url);
//                    }
                }
            }
        } else if (GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), button) == 0) {
            antiSpamClick = false;
        }
    }
}