package me.pm.lemon.module.modules.gui;

import me.pm.lemon.Main;
import me.pm.lemon.gui.testScreen.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class ResetGuiPos extends Module {
    public ResetGuiPos(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Expanded", false));
    }

    @Override
    public void onEnable() {
//        Main.ClientScreens.clickGuiScreen.resetGui();
        toggle();
        super.onEnable();
    }
}
