package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.LemonLogger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class HClip extends Command {
    @Override
    public String getAlias() {
        return "hclip";
    }

    @Override
    public String getDescription() {
        return "Teleportuje cie dana liczbe blockow przed ciebie.";
    }

    @Override
    public String getSyntax() {
        return PREFIX+"hclip";
    }

    @Override
    public List<String> getSuggestedArgs() {
        return null;
    }

    @Override
    public boolean isCommandEnabled() {
        return true;
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        try {
            if(args.length == 1) {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                assert player != null;

                double blocks = Double.parseDouble(args[0]);
                Vec3d forward = Vec3d.fromPolar(0, player.yaw).normalize();
                player.updatePosition(player.getX() + forward.x * blocks, player.getY(), player.getZ() + forward.z * blocks);
                if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                    LemonLogger.infoMessage("Success!","If nothing happened tho, there's an AC.");
                } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                    LemonLogger.infoMessage("Udało się!","Jeśli nic się nie stało to na serwerze jest Anty Cheat");
                }
            } else {
                if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                    LemonLogger.infoMessage("Invalid Syntax!","This command requires just one argument!");
                } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                    LemonLogger.infoMessage("Niepoprawna Pisownia!","Ta komenda wymaga przynajmniej jeden argument!");
                }
            }
        } catch (Exception e) {
            LemonLogger.errorMessage("Error", e.getMessage());
        }
    }
}
