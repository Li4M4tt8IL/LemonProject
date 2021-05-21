package me.pm.lemon.command.commands;

import me.pm.lemon.Main;
import me.pm.lemon.command.Command;
import me.pm.lemon.command.CommandManager;
import me.pm.lemon.utils.LemonLogger;
import me.pm.lemon.utils.generalUtils.Util;

import java.util.List;

public class GetOnlineLemonUsersCMD extends Command {
    @Override
    public String getAlias() {
        return "getonline";
    }

    @Override
    public String getDescription() {
        return "Wysyla aktywnych uzytkownikow.";
    }

    @Override
    public String getSyntax() {
        return PREFIX +  "getonline";
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
        for(String s : Main.getNames()) {
            LemonLogger.noPrefixMessage("\n");
            LemonLogger.noPrefixMessage(Util.getNameFromUUID(s));
        }
    }
}
