package me.pm.lemon.module.modules.combat;

import me.pm.lemon.gui.clickGui.settings.Setting;
import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class Reach extends Module {
    public Reach(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Reach", 1, 10, 4, 1));
    }
}
