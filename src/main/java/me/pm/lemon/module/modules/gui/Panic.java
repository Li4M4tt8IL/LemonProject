package me.pm.lemon.module.modules.gui;

import me.pm.lemon.Main;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;

import java.awt.*;

public class Panic extends Module {
    public Panic(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color);
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
