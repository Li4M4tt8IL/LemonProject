package me.pm.lemon.gui.bindcommandGui.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.pm.lemon.gui.bindcommandGui.elements.BindCommandWindow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class SettingMode extends Setting {

    public String[] modes;
    public int mode;
    public String text;

    public SettingMode(String text, String... modes) {
        this.modes = modes;
        this.text = text;
    }

    public int getNextMode() {
        if (mode + 1 >= modes.length) {
            return 0;
        }

        return mode + 1;
    }

    public String getName() {
        return text;
    }

    public void render(BindCommandWindow window, MatrixStack matrix, int x, int y, int len) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix, text + ": " + modes[mode], x + 2, y + 2,
                window.mouseOver(x, y, x + len, y + 12) ? 0xcfc3cf : 0xcfe0cf);

        if (window.mouseOver(x, y, x + len, y + 12) && window.lmDown) mode = getNextMode();
    }

    public SettingMode withDesc(String desc) {
        description = desc;
        return this;
    }

    public int getHeight(int len) {
        return 12;
    }

    public void readSettings(JsonElement settings) {
        if (settings.isJsonPrimitive()) {
            mode = MathHelper.clamp(settings.getAsInt(), 0, modes.length);
        }
    }

    public JsonElement saveSettings() {
        return new JsonPrimitive(MathHelper.clamp(mode, 0, modes.length));
    }

    @Override
    public boolean isDefault() {
        return mode == 0;
    }
}
