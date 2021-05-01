package me.pm.lemon.command.commands;

import me.pm.lemon.Main;
import me.pm.lemon.bindcommand.BindCommand;
import me.pm.lemon.bindcommand.BindCommandManager;
import me.pm.lemon.command.Command;
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
        return "Zarzadzasz komendami.";
    }

    @Override
    public String getSyntax() {
        return PREFIX + "bindcommand add command " +
                "\n| bindcommand remove command " +
                "\n| bindcommand bind command keyCode " +
                "\n| bindcommand list " +
                "\n| Example: ;;bindcommand add /home;exp";
    }

    @Override
    public List<String> getSuggestedArgs() {
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("remove");
        list.add("bind");
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
                    LemonLogger.errorMessage("Command " + args[1] + " already exists!");
                    return;
                }
            }
            BindCommandManager.registerBindCommand(new BindCommand(-1, args[1].replace(";", " ").toLowerCase()));
            LemonLogger.infoMessage("Added command " + args[1].replace(";", " ").toLowerCase());
            Main.ClientScreens.bindClickGuiScreen.resetWindows();
            return;
        } else if(args[0].equalsIgnoreCase("remove")) {
            for(BindCommand bindCommand : BindCommandManager.commands) {
                if(bindCommand.getCommandName().equalsIgnoreCase(args[1].replace(";", " ").toLowerCase())) {
                    BindCommandManager.commands.remove(bindCommand);
                    LemonLogger.infoMessage("Removed command " + args[1].replace(";", " ").toLowerCase());
                    Main.ClientScreens.bindClickGuiScreen.resetWindows();
                    return;
                }
            }
            LemonLogger.errorMessage("Command " + args[1] + " is not existing!");
            return;
        } else if(args[0].equalsIgnoreCase("list")) {
            for(BindCommand bindCommand : BindCommandManager.commands) {
                LemonLogger.infoMessage(bindCommand.getCommandName() + " | " + GLFW.glfwGetKeyName(bindCommand.getKeyCode(),
                        GLFW.glfwGetKeyScancode(bindCommand.getKeyCode())));
            }
        } else if(args[0].equalsIgnoreCase("bind")) {
            for(BindCommand bindCommand : BindCommandManager.commands) {
                if(args[1].toLowerCase().equalsIgnoreCase(bindCommand.getCommandName().replace(" ", ";").toLowerCase())) {
                   try {
                       bindCommand.setKeyCode(Integer.parseInt(args[2]));
                       Main.ClientScreens.bindClickGuiScreen.resetWindows();
                   } catch (Exception e) {
                       LemonLogger.errorMessage("This is not an number! Check if the keyCode is written correctly or bind the command through the bindClickGui!");
                       e.printStackTrace();
                   }
                    return;
            } else {
                    LemonLogger.errorMessage("Command " + args[1] + " is not existing.");
                    return;
                }
            }
        }
    }
}
