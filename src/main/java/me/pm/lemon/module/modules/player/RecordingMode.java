package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.DrawOverlayEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.FileHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecordingMode extends Module {
    public RecordingMode(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Save Toggled Modules", true),
                new SettingToggle("Recording Watermark", true));
    }

    private ArrayList<Module> toggledModules = new ArrayList<>();

    @Override
    public void onEnable() {
        if(getSetting(0).asToggle().state) {
            for(Module mod : ModuleManager.getModules()) {
                if(mod.isToggled()) {
                    if(mod.getCategory() == Category.RENDER || mod.getClass().equals(Hud.class)) {
                        mod.toggle();
                        toggledModules.add(mod);
                        FileHelper.createFile("recordingModules.txt");
                        List<String> lines = FileHelper.readFileLines("recordingModules.txt");
                        if(!lines.contains(mod.getName())) FileHelper.appendFile(mod.getName(), "recordingModules.txt");
                    }
                }
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(toggledModules.isEmpty()) {
            FileHelper.createFile("recordingModules.txt");
            List<String> lines = FileHelper.readFileLines("recordingModules.txt");
            for(String line : lines) {
                for(Module mod : ModuleManager.getModules()) {
                    if(mod.getName().equalsIgnoreCase(line)) mod.toggle();
                }
            }
        }
        for(Module mod : toggledModules) {
            mod.toggle();
        }
        toggledModules.clear();
        FileHelper.deleteFile("recordingModules.txt");
        super.onDisable();
    }

    @EventTarget
    public void onOverlayRender(DrawOverlayEvent event) {
        MatrixStack matrixStack = event.matrix;
        if(getSetting(1).asToggle().state) {
            mc.textRenderer.draw(matrixStack,"[Recording (Toggled)]", mc.getWindow().getScaledWidth()-mc.textRenderer.getWidth("[Recording (Toggled)]"), 0, -1);
        }
    }
}
