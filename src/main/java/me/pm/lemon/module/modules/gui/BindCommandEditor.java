package me.pm.lemon.module.modules.gui;

import me.pm.lemon.Main;
import me.pm.lemon.gui.clickGui.settings.Setting;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class BindCommandEditor extends Module {
    public BindCommandEditor(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.openScreen(Main.ClientScreens.bindClickGuiScreen);
        toggle();
    }
}
