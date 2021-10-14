package me.pm.lemon.module.modules.combat;

import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.entity.Entity;

import java.awt.*;

public class Hitboxes extends Module {
    public Hitboxes(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Expand", 0.1, 10, 0.5, 2));
    }

    public double getEntityValue(Entity entity) {
        if (!isToggled()) return 0;
        return getSetting(0).asSlider().getValue();
//        return 0;
    }
}