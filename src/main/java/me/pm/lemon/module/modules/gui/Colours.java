package me.pm.lemon.module.modules.gui;

import me.pm.lemon.gui.clickGui.settings.SettingColor;
import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.gui.clickGui.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.Module;
import org.lwjgl.glfw.GLFW;

public class Colours extends Module {
    public Colours() {
        super("Color Picker", Category.GUI, "HUD color settings", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI,
                new SettingToggle("Rainbow", false).withChildren(
                        new SettingSlider("Saturation", 0.01, 1, 0.5f, 2),
                        new SettingSlider("Brightness", 0.01, 1, 0.5f, 2),
                        new SettingSlider("Speed", 1, 25, 10, 0)
                ),
                new SettingColor("Gui Color", 0, 255, 255, false),
                new SettingColor("Text Color", 255, 255, 255, false),
                new SettingColor("Gui Text Color", 255, 255, 255, false),
                new SettingColor("Gui Bg Color", 48, 48, 48, false));
    }

    @Override
    public void onEnable() {
        setToggled(false);
    }
}