package me.pm.lemon.command.commands;

import me.pm.lemon.Main;
import me.pm.lemon.command.Command;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.module.modules.gui.Panic;
import me.pm.lemon.utils.LemonLogger;

import java.util.List;

public class StopPanicCMD extends Command {
    @Override
    public String getAlias() {
        return "stop-panic";
    }

    @Override
    public String getDescription() {
        return "Usuwa panic.";
    }

    @Override
    public String getSyntax() {
        return PREFIX + "stop-panic";
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
        if(Module.getModule(Panic.class).isToggled()) {
            Module.getModule(Panic.class).setToggled(false);
            Main.isPanic = false;
        } else {
            if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                LemonLogger.infoMessage("Success!","Panic disabled.");
            } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                LemonLogger.infoMessage("Udało się!","Panic wyłączony.");
            }
        }
    }
}
