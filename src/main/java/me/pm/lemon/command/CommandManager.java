package me.pm.lemon.command;

import me.pm.lemon.command.commands.*;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.LemonLogger;

import java.util.ArrayList;

public class CommandManager {

    private static ArrayList<Command> commands;

    public CommandManager() {
        commands = new ArrayList<>();
        registerCommand(new HelpCMD());
        registerCommand(new StopPanicCMD());
        registerCommand(new LegitModeCMD());
        registerCommand(new XrayCMD());
        registerCommand(new BindCommandCMD());
        registerCommand(new PrefixCMD());
        registerCommand(new VClip());
        registerCommand(new HClip());
        registerCommand(new ApiChangeCMD());
    }

    public void registerCommand(Command c) {
        commands.add(c);
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static void callCommand(String input) {
        String [] split = input.split(" ");
        String command = split[0];
        String args = input.substring(command.length()).trim();

        for(Command c : getCommands()) {
            if(c.getAlias().equalsIgnoreCase(command)) {
                try {
                    c.onCommand(args, args.split(" "));
                } catch (Exception e) {
                    if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                        LemonLogger.errorMessage("Command","Invalid Usage!");
                        LemonLogger.infoMessage("Proper syntax", c.getSyntax());
                    } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                        LemonLogger.errorMessage("Komenda","Złe użycie!!");
                        LemonLogger.infoMessage("Poprawne użycie:", c.getSyntax());
                    }
                    e.printStackTrace();
                }
                return;
            }
        }
        if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
            LemonLogger.warningMessage("Command", "Not found!");
        } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
            LemonLogger.warningMessage("Komenda", "Nie znaleziona!");
        }
    }

}
