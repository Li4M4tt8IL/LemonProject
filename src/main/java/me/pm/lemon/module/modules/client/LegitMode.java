package me.pm.lemon.module.modules.client;

import me.pm.lemon.Main;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;

import java.awt.*;

public class LegitMode extends Module {
    public LegitMode(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Main.isPanic = true;
//        setToggled(true);

        for(Module module : ModuleManager.getModules()) {
            if(!module.getName().equalsIgnoreCase("panic!")) {
                if(module.isToggled()) {
                    module.toggle();
                }
            }
        }
    }
}