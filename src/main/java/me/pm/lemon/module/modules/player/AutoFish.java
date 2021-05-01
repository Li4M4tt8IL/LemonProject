package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.ReadPacketEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.mixin.IMinecraftClient;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;

import java.awt.*;

public class AutoFish extends Module {
    public AutoFish(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Recast Delay", 1, 10, 3, 0));
    }

    public static int ticksPassed;
    public static boolean pullFish;

    @Override
    public void onDisable() {
        pullFish = false;
        ticksPassed = 0;
        super.onDisable();
    }

    @EventTarget
    public void onReceivePacket(ReadPacketEvent event) {
        if (
                mc.player != null
                        && (mc.player.getMainHandStack().getItem() == Items.FISHING_ROD || mc.player.getOffHandStack().getItem() == Items.FISHING_ROD)
                        && event.getPacket() instanceof PlaySoundS2CPacket
                        && SoundEvents.ENTITY_FISHING_BOBBER_SPLASH.equals(((PlaySoundS2CPacket) event.getPacket()).getSound())
        ) {
            pullFish = true;
            ticksPassed = (int) getSetting(0).asSlider().getValue() * 20;
        }
    }
    @EventTarget
    public void onTick(TickEvent event) {
        assert mc.player != null;
        if (ticksPassed != 0) {
            ticksPassed--;
        }
        if (pullFish && ticksPassed == (int) getSetting(0).asSlider().getValue() * 20 - 5) {
            ((IMinecraftClient) mc).callDoItemUse();
            pullFish = false;
        }
        if (ticksPassed == 1) {
            ((IMinecraftClient) mc).callDoItemUse();
            ticksPassed = 0;
        }
    }

}
