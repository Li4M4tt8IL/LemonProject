package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
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
                LemonLogger.infoMessage("Success!","If nothing happened tho, there's an AC.");
            } else {
                LemonLogger.infoMessage("Invalid Syntax!","This command requires just one argument!");
            }
        } catch (Exception e) {
            LemonLogger.errorMessage("Error", e.getMessage());
        }
    }
}
