package me.pm.lemon.module;

import me.pm.lemon.gui.hud.IRenderer;
import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.gui.testScreen.settings.Setting;
import me.pm.lemon.utils.FileHelper;
import net.minecraft.client.MinecraftClient;

import java.awt.*;
import java.io.File;

public class ModuleDraggable extends Module implements IRenderer {

    public ModuleDraggable(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);

        pos = loadPositionFromFile();
    }

    private File getFolder() {
        File folder = new File(FileHelper.getHudDir().toFile(), this.getClass().getSimpleName());
        folder.mkdir();
        return folder;
    }

    private ScreenPosition loadPositionFromFile() {
        ScreenPosition loaded = FileHelper.readFromJson(new File(getFolder(), "pos.json"), ScreenPosition.class);
        if(loaded == null) {
            loaded = ScreenPosition.fromRelativePosition(0.5, 0.5);
            this.pos = loaded;
            savePositionToFile();
        }

        return loaded;
    }

    private void savePositionToFile() {
        FileHelper.writeJsonToFile(new File(getFolder(), "pos.json"), pos);
    }

    @Override
    public void save(ScreenPosition pos) {
        this.pos = pos;
        savePositionToFile();
    }

    @Override
    public ScreenPosition load() {
        return pos;
    }

    protected ScreenPosition pos;

    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return (MinecraftClient.getInstance().textRenderer.fontHeight + 3) * lineNum;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(ScreenPosition pos) { }

}
