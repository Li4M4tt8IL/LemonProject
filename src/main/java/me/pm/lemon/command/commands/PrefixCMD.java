package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
import me.pm.lemon.utils.FileHelper;

import java.util.List;

public class PrefixCMD extends Command {
    @Override
    public String getAlias() {
        return "prefix";
    }

    @Override
    public String getDescription() {
        return "Zmienia prefix.";
    }

    @Override
    public String getSyntax() {
        return PREFIX + "prefix [key]";
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
        FileHelper.createEmptyFile("prefix.txt");
        this.PREFIX = args[0];
        FileHelper.appendFile(args[0], "prefix.txt");
    }
}
