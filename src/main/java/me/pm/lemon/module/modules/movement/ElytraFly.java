package me.pm.lemon.module.modules.movement;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.ClientMoveEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class ElytraFly extends Module {
    public ElytraFly(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Auto Open", true).withDesc("Auto opens elytra"),
                new SettingMode("Mode", "Normal", "Control"),
                new SettingSlider("Overworld Speed", 0, 5, 1.28, 2),
                new SettingSlider("Nether Speed", 0, 5, 1.28, 2),
                new SettingSlider("End Speed", 0, 5, 1.28, 2),
                new SettingToggle("2b2t Downwards vel.", true));
    }

    @EventTarget
    public void onClientMove(ClientMoveEvent event) {
        if(!this.isToggled()) return;
        if(getSetting(1).asMode().mode == 1 && mc.player.isFallFlying()) {
            if(!mc.options.keyJump.isPressed() && !mc.options.keySneak.isPressed()) {
                if(getSetting(5).asToggle().state) {
                    event.vec3d = new Vec3d(event.vec3d.x, -0.0001, event.vec3d.z);
                } else {
                    event.vec3d = new Vec3d(event.vec3d.x, 0, event.vec3d.z);
                }
            }
        }

        if(!mc.options.keyBack.isPressed()
                && !mc.options.keyLeft.isPressed()
                && !mc.options.keyRight.isPressed()
                && !mc.options.keyForward.isPressed()) {
            if(getSetting(5).asToggle().state) {
                event.vec3d = new Vec3d(event.vec3d.x, event.vec3d.y-0.0001, event.vec3d.z);
            } else {
                event.vec3d = new Vec3d(event.vec3d.x, event.vec3d.y, event.vec3d.z);
            }
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if(!this.isToggled()) return;
        if(getSetting(1).asMode().mode == 1) this.setDisplayName("Elytra Fly \2477Control");
        if(getSetting(1).asMode().mode == 0) this.setDisplayName("Elytra Fly \2477Normal");
        assert mc.world != null;
        Vec3d vec3d;
        if(mc.world.getRegistryKey().getValue().getPath().equalsIgnoreCase("the_end")) {
            vec3d = new Vec3d(0, 0, getSetting(4).asSlider().getValue())
                    .rotateX(getSetting(1).asMode().mode == 1 ? 0 : -(float) Math.toRadians(mc.player.pitch)).rotateY(-(float) Math.toRadians(mc.player.yaw));
        } else if(mc.world.getRegistryKey().getValue().getPath().equalsIgnoreCase("the_nether")) {
            vec3d = new Vec3d(0, 0, getSetting(3).asSlider().getValue())
                    .rotateX(getSetting(1).asMode().mode == 1 ? 0 : -(float) Math.toRadians(mc.player.pitch)).rotateY(-(float) Math.toRadians(mc.player.yaw));
        } else {
            vec3d = new Vec3d(0, 0, getSetting(2).asSlider().getValue())
                    .rotateX(getSetting(1).asMode().mode == 1 ? 0 : -(float) Math.toRadians(mc.player.pitch)).rotateY(-(float) Math.toRadians(mc.player.yaw));
        }
        if(!mc.player.isFallFlying()
                && !mc.player.isOnGround()
                && getSetting(1).asMode().mode == 1
                && mc.player.age % 10 == 0 && getSetting(0).asToggle().state) {
            mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        }
        if(mc.player.isFallFlying()) {
            if(getSetting(1).asMode().mode == 0 && mc.options.keyForward.isPressed()) {
               mc.player.setVelocity(
                       mc.player.getVelocity().x + vec3d.x + (vec3d.x - mc.player.getVelocity().x),
                       mc.player.getVelocity().y + vec3d.y + (vec3d.y - mc.player.getVelocity().y),
                       mc.player.getVelocity().z + vec3d.z + (vec3d.z - mc.player.getVelocity().z)
               );
            }
            else if(getSetting(1).asMode().mode == 1) {
                if(mc.options.keyBack.isPressed()) vec3d = vec3d.multiply(-1);
                if(mc.options.keyLeft.isPressed()) vec3d = vec3d.rotateY((float) Math.toRadians(90));
                if(mc.options.keyRight.isPressed()) vec3d = vec3d.rotateY(-(float) Math.toRadians(90));

                if(mc.options.keyJump.isPressed()) vec3d = vec3d.add(0, getSetting(2).asSlider().getValue(), 0);
                if(mc.options.keySneak.isPressed()) vec3d = vec3d.add(0, -getSetting(2).asSlider().getValue(), 0);

                if(!mc.options.keyBack.isPressed()
                        && !mc.options.keyLeft.isPressed()
                        && !mc.options.keyRight.isPressed()
                        && !mc.options.keyForward.isPressed()
                        && !mc.options.keyJump.isPressed()
                        && !mc.options.keySneak.isPressed()) {
                    vec3d = Vec3d.ZERO;
                }
                mc.player.setVelocity(vec3d.multiply(2));
            }
        }
    }
}