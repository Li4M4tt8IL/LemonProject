package me.pm.lemon.module.modules.gui;

import me.pm.lemon.Main;
import me.pm.lemon.gui.testScreen.settings.SettingMode;
import me.pm.lemon.gui.testScreen.settings.SettingSlider;
import me.pm.lemon.gui.testScreen.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class ClickGui extends Module {
    public ClickGui(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Search bar", true),
                new SettingToggle("Help", true),
                new SettingToggle("Static descriptions", true),
                new SettingMode("Set. Pos.", "Below", "Sideways"),
                new SettingMode("Theme", "Classic", "Clear"),
                new SettingToggle("Mark Expanded", false),
                new SettingMode("Language", "English", "Polish"));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.openScreen(Main.ClientScreens.testScreen);
        toggle();
    }
}
