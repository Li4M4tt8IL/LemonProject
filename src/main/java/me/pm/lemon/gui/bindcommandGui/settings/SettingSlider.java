package me.pm.lemon.gui.bindcommandGui.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.pm.lemon.gui.bindcommandGui.elements.BindCommandWindow;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SettingSlider extends Setting {

    public double min;
    public double max;
    private double value;
    public int decimals;
    public String text;

    protected double defaultValue;

    public SettingSlider(String text, double min, double max, double value, int decimals) {
        this.min = min;
        this.max = max;
        this.value = value;
        this.decimals = decimals;
        this.text = text;

        defaultValue = value;
    }

    public double getValue() {
        return round(value, decimals);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double round(double value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getName() {
        return text;
    }

    public void render(BindCommandWindow window, MatrixStack matrix, int x, int y, int len) {
        int pixels = (int) Math.round(MathHelper.clamp((len - 2) * ((getValue() - min) / (max - min)), 0, len - 2));
        //TODO make it fill the slider w/ static selected color instead of pasting it twice into a gradient LMFAO also implement rainbow
        Color color  = new Color(0, 255, 255).darker();
        window.fillGradient(matrix, x + 1, y, x + pixels, y + 12, Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 0 ? color.getRGB() : ColorUtils.guiColor(),
                Module.getModule(ClickGui.class).getSetting(4).asMode().mode == 0 ? color.getRGB() : ColorUtils.guiColor());

        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix,
                text + ": " + (decimals == 0 && getValue() > 100 ? Integer.toString((int) getValue()) : getValue()),
                x + 2, y + 2, window.mouseOver(x, y, x + len, y + 12) ? 0xcfc3cf : 0xcfe0cf);

        if (window.mouseOver(x + 1, y, x + len - 2, y + 12)) {
            if (window.lmHeld) {
                int percent = ((window.mouseX - x) * 100) / (len - 2);
                if (percent == 1) // This is definitely a spaghetti fix but this way it fits in this code style lmao
                    percent = 0; // Someone needs to fix it so gui modules x and y pos can reach its min value

                setValue(round(percent * ((max - min) / 100) + min, decimals));
            }

            if (window.mwScroll != 0) {
                double units = 1 / (Math.pow(10, decimals));

                setValue(MathHelper.clamp(getValue() + units * window.mwScroll, min, max));
            }
        }
    }

    public SettingSlider withDesc(String desc) {
        description = desc;
        return this;
    }

    public int getHeight(int len) {
        return 12;
    }

    public void readSettings(JsonElement settings) {
        if (settings.isJsonPrimitive()) {
            setValue(settings.getAsDouble());
        }
    }

    public JsonElement saveSettings() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public boolean isDefault() {
        BigDecimal bd = new BigDecimal(defaultValue);
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);

        return bd.doubleValue() == getValue();
    }
}
