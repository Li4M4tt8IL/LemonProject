package me.pm.lemon.command.commands;

import me.pm.lemon.Main;
import me.pm.lemon.bindcommand.BindCommand;
import me.pm.lemon.bindcommand.BindCommandManager;
import me.pm.lemon.command.Command;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.LemonLogger;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class BindCommandCMD extends Command {
    @Override
    public String getAlias() {
        return "bindcommand";
    }

    @Override
    public String getDescription() {
        if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
            return "Auto text hotkey";
        } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
            return "Zarzadzasz komendami.";
        }
        return null;
    }

    @Override
    public String getSyntax() {
        return PREFIX + "bindcommand add command " +
                "\n| bindcommand remove command " +
                "\n| bindcommand list " +
                "\n| Example: ;;bindcommand add /home;exp";
    }

    @Override
    public List<String> getSuggestedArgs() {
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("remove");
        list.add("list");
        return list;
    }

    @Override
    public boolean isCommandEnabled() {
        return true;
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(args[0].equalsIgnoreCase("add")) {
            for(BindCommand bindCommand : BindCommandManager.commands) {
                if(bindCommand.getCommandName().equalsIgnoreCase(args[1].replace(";", " ").toLowerCase())) {
                    if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                        LemonLogger.errorMessage("Command",args[1] + " already exists!");
                    } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                        LemonLogger.errorMessage("Komenda",args[1] + " już istnieje!");
                    }
                    return;
                }
            }
            BindCommandManager.registerBindCommand(new BindCommand(-1, args[1].replace(";", " ").toLowerCase()));
            if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                LemonLogger.infoMessage("Added Command", args[1].replace(";", " ").toLowerCase());
            } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                LemonLogger.infoMessage("Dodano Komendę", args[1].replace(";", " ").toLowerCase());
            }
            Main.ClientScreens.bindClickGuiScreen.resetWindows();
            return;
        } else if(args[0].equalsIgnoreCase("remove")) {
            for(BindCommand bindCommand : BindCommandManager.commands) {
                if(bindCommand.getCommandName().equalsIgnoreCase(args[1].replace(";", " ").toLowerCase())) {
                    BindCommandManager.commands.remove(bindCommand);
                    if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                        LemonLogger.infoMessage("Removed Command",args[1].replace(";", " ").toLowerCase());
                    } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                        LemonLogger.infoMessage("Usunięto Komendę",args[1].replace(";", " ").toLowerCase());
                    }
                    Main.ClientScreens.bindClickGuiScreen.resetWindows();
                    return;
                }
            }
            if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                LemonLogger.errorMessage("Command ", args[1] + " does not exist!");
            } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                LemonLogger.errorMessage("Komenda ", args[1] + " nie istnieje!");
            }
            return;
        } else if(args[0].equalsIgnoreCase("list")) {
            for(BindCommand bindCommand : BindCommandManager.commands) {
                LemonLogger.noPrefixMessage(bindCommand.getCommandName() + " | " + GLFW.glfwGetKeyName(bindCommand.getKeyCode(),
                        GLFW.glfwGetKeyScancode(bindCommand.getKeyCode())));
            }
        }
    }
}
