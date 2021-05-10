package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.LemonLogger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.List;

public class VClip extends Command {
    @Override
    public String getAlias() {
        return "vclip";
    }

    @Override
    public String getDescription() {
        return "Teleportuje cie dana liczbe blockow nad ciebie.";
    }

    @Override
    public String getSyntax() {
        return PREFIX+"vclip";
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
                player.updatePosition(player.getX(), player.getY() + blocks, player.getZ());
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
