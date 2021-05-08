package me.pm.lemon.command.commands;

import me.pm.lemon.Main;
import me.pm.lemon.command.Command;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class ApiChangeCMD extends Command {
    @Override
    public String getAlias() {
        return "api";
    }

    @Override
    public String getDescription() {
        if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
            return "Controlls the api manually";
        } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {

            return "Updatuje api manualnie.";
        }
        return null;
    }

    @Override
    public String getSyntax() {
        return PREFIX +  "api";
    }

    @Override
    public List<String> getSuggestedArgs() {
        List<String> list = new ArrayList<>();
        list.add("?name=%s&online=%s&hat=%s&wings=%s");
        list.add("name");
        list.add("online");
        list.add("hat");
        list.add("wings");
        return list;
    }

    @Override
    public boolean isCommandEnabled() {
        if(Main.debugMode) {
            return true;
        } else {
            return MinecraftClient.getInstance().player.getName().getString().equals(Main.ClientInfo.clientCreators);
        }
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
//        if(args.length == 2) {
//            ApiV2.getInstance().updatePlayer(args[0], Boolean.parseBoolean(args[1]));
//        } else if(args.length == 4) {
//            ApiV2.getInstance().updatePlayer(args[0], Boolean.parseBoolean(args[1]), args[2], args[3]);
//        } else if(args.length == 1) {
//            ApiV2.getInstance().updatePlayer(args[0]);
////            ?name=%s&online=%s&tophat=%s&wings=%s
//        } else if (args.length == 3) {
//            ApiV2.getInstance().updatePlayer(String.format("?name=%s&online=%s&hat=%s", args[0], args[1], args[2]));
//        } else {
//            PMLogger.errorMessage("Syntax error!");
//        }
    }
}
