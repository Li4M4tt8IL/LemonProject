package me.pm.lemon.gui.testScreen.settings;

import com.google.gson.JsonElement;
import me.pm.lemon.gui.testScreen.elements.ModuleWindow;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.commons.lang3.tuple.Triple;

public abstract class Setting {
    protected String description = "";

    public SettingMode asMode() {
        try {
            return (SettingMode) this;
        } catch (Exception e) {
            throw new ClassCastException("Execption parsing setting: " + this);
        }
    }

    public SettingToggle asToggle() {
        try {
            return (SettingToggle) this;
        } catch (Exception e) {
            throw new ClassCastException("Execption parsing setting: " + this);
        }
    }

    public SettingSlider asSlider() {
        try {
            return (SettingSlider) this;
        } catch (Exception e) {
            throw new ClassCastException("Execption parsing setting: " + this);
        }
    }

    public SettingColor asColor() {
        try {
            return (SettingColor) this;
        } catch (Exception e) {
            throw new ClassCastException("Execption parsing setting: " + this);
        }
    }

    public SettingRotate asRotate() {
        try {
            return (SettingRotate) this;
        } catch (Exception e) {
            throw new ClassCastException("Execption parsing setting: " + this);
        }
    }

    public abstract String getName();

    public String getDesc() {
        return description;
    }

    public Triple<Integer, Integer, String> getGuiDesc(ModuleWindow moduleWindow, int x, int y, int len) {
        return Triple.of(x + len + 2, y, description);
    }

    public abstract void render(ModuleWindow moduleWindow, MatrixStack matrix, int x, int y, int len);

    public abstract int getHeight(int len);

    public abstract void readSettings(JsonElement settings);

    public abstract JsonElement saveSettings();

    public abstract boolean isDefault();
}
