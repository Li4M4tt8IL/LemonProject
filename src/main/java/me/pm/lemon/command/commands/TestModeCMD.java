package me.pm.lemon.command.commands;

import me.pm.lemon.command.Command;
import me.pm.lemon.utils.LemonLogger;

import java.util.ArrayList;
import java.util.List;

public class TestModeCMD extends Command {
    @Override
    public String getAlias() {
        return "stop-test";
    }

    @Override
    public String getDescription() {
        return "Usuwa test.";
    }

    @Override
    public String getSyntax() {
        return PREFIX + "stop-test";
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
//        if(Module.getModule(LegitMode.class).isToggled()) {
//            Module.getModule(LegitMode.class).setToggled(false);
//            Main.testMode = false;
//            PMLogger.infoMessage("Test mode zostal wylaczony!");
//        } else {
//            PMLogger.infoMessage("Test mode jest juz wylaczony!");
//        }
        List<String> testList = new ArrayList<>();
        if(testList.contains("abc")) LemonLogger.infoMessage("What","That shouldn't be there.");
    }
}
