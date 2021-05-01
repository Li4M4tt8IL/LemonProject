package me.pm.lemon.module.modules.render;

import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;

import java.awt.*;

public class OG extends Module {
    public OG(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @Override
    public void onEnable() {
        for(Module mod : ModuleManager.getModules()) {
            String name = mod.getDisplayName();
            String ogName = "OG " + name;

            mod.setDisplayName(ogName);
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        for(Module mod : ModuleManager.getModules()) {
            mod.setDisplayName(mod.getName());
        }
        super.onDisable();
    }
}
