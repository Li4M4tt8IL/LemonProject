package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.world.Xray;
import me.pm.lemon.utils.FileHelper;
import me.pm.lemon.utils.LemonLogger;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class XrayCMD extends Command {
    @Override
    public String getAlias() {
        return "xray";
    }

    @Override
    public String getDescription() {
        return "Mozesz zarzadzac xrayem.";
    }

    @Override
    public String getSyntax() {
        return PREFIX + "xray add [block] " +
                "\n| xray remove [block] " +
                "\n| xray clear" +
                "\n| xray list";
    }

    @Override
    public List<String> getSuggestedArgs() {
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("remove");
        list.add("clear");
        list.add("list");
        return list;
    }

    @Override
    public boolean isCommandEnabled() {
        return true;
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        FileHelper.createFile("xray.txt");

        List<String> lines = FileHelper.readFileLines("xray.txt");
        lines.removeIf(s -> s.isEmpty());
        System.out.println(lines);

        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
            Xray xray = (Xray) Module.getModule(Xray.class);
            String block = (args[1].contains(":") ? "" : "minecraft:") + args[1].toLowerCase();

            if (args[0].equalsIgnoreCase("add")) {
                if (Registry.BLOCK.get(new Identifier(block)) == Blocks.AIR) {
                    LemonLogger.errorMessage("Invalid Block: " , args[1]);
                    return;
                } else if (lines.contains(block)) {
                    LemonLogger.errorMessage("Error","Block is already added!");
                    return;
                }

                FileHelper.appendFile(block, "xray.txt");

                if (xray.isToggled()) {
                    xray.toggle();
                    xray.toggle();
                }

                LemonLogger.infoMessage("Added","Block: " + args[1]);

            } else if (args[0].equalsIgnoreCase("remove")) {
                if (lines.contains(block)) {
                    lines.remove(block);

                    String s = "";
                    for (String s1 : lines) s += s1 + "\n";

                    FileHelper.createEmptyFile("xray.txt");
                    FileHelper.appendFile(s, "xray.txt");

                    if (xray.isToggled()) {
                        xray.toggle();
                        xray.toggle();
                    }

                    LemonLogger.infoMessage("Removed ","Block: " + args[1]);
                } else {
                    LemonLogger.errorMessage("Block ","Not In List: " + args[1]);
                }
            }
        } else if (args[0].equalsIgnoreCase("clear")) {
            FileHelper.createEmptyFile("xray.txt");
            LemonLogger.infoMessage("Cleared ","Xray Blocks");
        } else if (args[0].equalsIgnoreCase("list")) {
            String s = "";
            for (String l : lines) {
                s += "\n\u00a76" + l;
            }

            LemonLogger.noPrefixMessage(s);
        }
    }
}
