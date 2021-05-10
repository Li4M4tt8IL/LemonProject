package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.FileHelper;
import me.pm.lemon.utils.LemonLogger;

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
        if(args.length == 1) {
            FileHelper.createEmptyFile("prefix.txt");
            this.PREFIX = args[0];
            FileHelper.appendFile(args[0], "prefix.txt");
        } else {
            if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                LemonLogger.infoMessage("Invalid Syntax!","This command requires just one argument!");
            } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                LemonLogger.infoMessage("Niepoprawna Pisownia!","Ta komenda wymaga przynajmniej jeden argument!");
            }
        }
    }
}
