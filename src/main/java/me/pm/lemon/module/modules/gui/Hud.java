package me.pm.lemon.module.modules.gui;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.DrawOverlayEvent;
import me.pm.lemon.gui.clickGui.settings.SettingMode;
import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.gui.clickGui.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.notifications.NotificationManager;

import java.awt.*;

public class Hud extends Module {
    public Hud(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Welcome Message", true),
                new SettingToggle("Module List", true).withDesc("Shows the toggled modules list").withChildren(
                        new SettingToggle("Rainbow", true).withDesc("RaInBoW cOlOrS"),
                        new SettingToggle("Background", true).withDesc("Draws a gray-ich background to the list"),
                        new SettingToggle("Separator", true).withDesc("Draws a separator"),
                        new SettingMode("Sorting", "Up-Down", "Down-Up", "Category"),
                        new SettingMode("Text Align", "Left", "Right")
                ),
                new SettingToggle("Info", true).withDesc("Shows the mc info").withChildren(
                        new SettingToggle("Client Name", true),
                        new SettingToggle("Version", true),
                        new SettingToggle("Mc Version", true),
                        new SettingToggle("Name", true),
                        new SettingToggle("FPS", true),
                        new SettingToggle("IP", true),
                        new SettingToggle("Ping", true)
                ),
                new SettingToggle("World Info", true).withDesc("Shows world info").withChildren(
                        new SettingToggle("Coordinates", true),
                        new SettingToggle("Facing", true)
                ),
                new SettingToggle("Armor", true).withDesc("Shows your armor").withChildren(
                        new SettingToggle("Background", true),
                        new SettingMode("Pos.", "Vertical", "Horizontal")
                ),
                new SettingToggle("Entity list", true).withDesc("Draws a entity list").withChildren(
                        new SettingToggle("Players", true)
                ),
                new SettingToggle("Keystrokes", true),
                new SettingToggle("Inventory", true),
                new SettingToggle("Module Notifications", false),
                new SettingToggle("Other Notifications", true));
    }

    @EventTarget
    public void onRender(DrawOverlayEvent event) {
        NotificationManager.render();
    }
}
