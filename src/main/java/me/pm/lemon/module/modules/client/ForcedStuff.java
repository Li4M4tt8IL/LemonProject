package me.pm.lemon.module.modules.client;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.Module;

import java.util.HashMap;

public class ForcedStuff extends Module {

    private int tickCounter = 0;
    public ForcedStuff() {
        super("API", Category.CLIENT, "Shows players that are using the same mod", -1, Colors.CLIENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    private HashMap<String, String> nameMap = new HashMap<>();
    private HashMap<String,  String> nameLog = new HashMap<>();

//    @Override
//    public void onDisable() {
//        for (PlayerListEntry f : mc.player.networkHandler.getPlayerList()) {
//            if(nameMap.get(f.getProfile().getId().toString()) == null) {
//                continue;
//            }
//            if(Main.getNames().contains(f.getProfile().getId().toString())) {
//                Objects.requireNonNull(mc.player.networkHandler.getPlayerListEntry(f.getProfile().getName()))
//                        .setDisplayName(Text.of(nameLog.get(f.getProfile().getId().toString())));
//            }
//        }
//
//        nameMap.clear();
//        nameLog.clear();
//        super.onDisable();
//    }

//    @EventTarget
//    public void onTick(TickEvent event) {
//        try {
//            assert mc.player != null;
//            if (mc.player.age % 10 == 0) {
//                for (PlayerListEntry f : mc.player.networkHandler.getPlayerList()) {
//                    if(nameMap.get(f.getProfile().getId().toString()) == null) {
//                        if(f.getProfile().getName().equals(Main.ClientInfo.clientCreators)) {
//                            nameMap.put(f.getProfile().getId().toString(), (f.getDisplayName() == null ? f.getProfile().getName() : f.getDisplayName().getString()) + " \u00A78[\u00A7bPM DEV\u00A78]");
//                        } else {
//                            nameMap.put(f.getProfile().getId().toString(), (f.getDisplayName() == null ? f.getProfile().getName() : f.getDisplayName().getString()) + " \u00A78[\u00A7bPM\u00A78]");
//                        }
//                        nameLog.put(f.getProfile().getId().toString(), f.getDisplayName().getString());
//                    }
//                    if(Main.getOnlinePlayers().contains(f.getProfile().getName())) {
//                        Objects.requireNonNull(mc.player.networkHandler.getPlayerListEntry(f.getProfile().getName()))
//                                .setDisplayName(Text.of(nameMap.get(f.getProfile().getId().toString())));
////                        PMLogger.infoMessage(nameMap.get(f.getProfile().getId().toString()));
//                    }
//                }
//            }
//        } catch (Exception e) { }
//    }

    @EventTarget
    public void onScreenOpen(OpenScreenEvent event) {
        if(mc.world == null) {
            nameMap.clear();
            nameLog.clear();
        }
    }
}
