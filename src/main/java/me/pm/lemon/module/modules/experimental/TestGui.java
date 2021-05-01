package me.pm.lemon.module.modules.experimental;

import me.pm.lemon.Main;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;

import java.awt.*;

public class TestGui extends Module {
    public TestGui(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.openScreen(Main.ClientScreens.testScreen);
        toggle();
    }
}
