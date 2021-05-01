package me.pm.lemon.bindcommand;

import me.pm.lemon.event.EventManager;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.KeyPressEvent;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.chat.BindCommands;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

public class BindCommandManager {
    public static ArrayList<BindCommand> commands = new ArrayList<>();

    public void init() {
        EventManager.register(this);
        registerBindCommand(new BindCommand(-1, "/example"));
    }

    public static void registerBindCommand(BindCommand bindCommand) {
        commands.add(bindCommand);
    }

    public static BindCommand getCommandByName(String name) {
        for(BindCommand bindCommand : commands) {
            if(bindCommand.getCommandName().equalsIgnoreCase(name)) {
                return bindCommand;
            }
        }
        return null;
    }

    @EventTarget
    public void onKey(KeyPressEvent event) {
        if(Module.getModule(BindCommands.class).isToggled()) {
            for(BindCommand bindCommand : commands) {
                if(bindCommand.getKeyCode() == event.getKey() && bindCommand.isToggled()) {
                    MinecraftClient.getInstance().player.sendChatMessage(bindCommand.getCommandName());
                }
            }
        }
    }
}
