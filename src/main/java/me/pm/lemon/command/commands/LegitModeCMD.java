package me.pm.lemon.command.commands;

import com.google.common.collect.Lists;
import me.pm.lemon.Main;
import me.pm.lemon.command.Command;
import me.pm.lemon.gui.clickGui.ClickGuiScreen;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.FileHelper;
import me.pm.lemon.utils.LemonLogger;

import java.util.ArrayList;
import java.util.List;

public class LegitModeCMD extends Command {
    @Override
    public String getAlias() {
        return "legit-mode";
    }

    @Override
    public String getDescription() {
        return "Legit.";
    }

    @Override
    public String getSyntax() {
        return PREFIX + "legit-mode";
    }

    @Override
    public List<String> getSuggestedArgs() {
        return null;
    }

    @Override
    public boolean isCommandEnabled() {
        return true;
    }

    private List<Module> list = Lists.newArrayList();

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(Main.legitMode) {
            for(Module mod : list) {
                if(!(mod.getName().equals(ModuleManager.Instances.panic.getName())))
                    mod.toggle();
            }
            Main.legitMode = false;
            Main.ClientScreens.testScreen.panel.resetButtons();
            if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                LemonLogger.infoMessage("Success!","Legit Mode Disabled.");
            } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                LemonLogger.infoMessage("Udało się!","Wyłączono Legit Mode.");
            }
        } else {
            for(Module mod : ModuleManager.getModules()) {
                if(ModuleManager.nonLegitModules.contains(mod) && mod.isToggled() && !(mod.getName().equals(ModuleManager.Instances.panic.getName()))) {
                    list.add(mod);
                    mod.toggle();
                }
            }
            Main.legitMode = true;
            Main.ClientScreens.testScreen.panel.resetButtons();
            if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                LemonLogger.infoMessage("Success!","Legit Mode Enabled.");
            } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                LemonLogger.infoMessage("Udało się!","Włączono Legit Mode.");
            }
        }
        FileHelper.createEmptyFile("empty.txt");
        FileHelper.appendFile(Main.legitMode ? "true" : "false", "empty.txt");
    }
}
