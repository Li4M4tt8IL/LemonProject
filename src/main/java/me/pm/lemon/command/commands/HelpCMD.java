package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
import me.pm.lemon.command.CommandManager;
import me.pm.lemon.utils.LemonLogger;

import java.util.List;

public class HelpCMD extends Command {
    @Override
    public String getAlias() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Wysyla komendy.";
    }

    @Override
    public String getSyntax() {
        return PREFIX +  "help";
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
        for(Command cmd : CommandManager.getCommands()) {
            if(!cmd.isCommandEnabled()) return;
            LemonLogger.noPrefixMessage("\n");
            LemonLogger.noPrefixMessage(cmd.getSyntax() + " | " + cmd.getDescription());
        }
    }
}
