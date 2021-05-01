package me.pm.lemon.gui.testScreen.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.pm.lemon.gui.testScreen.elements.ModuleWindow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.commons.lang3.tuple.Triple;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SettingToggle extends Setting {

    public boolean state;
    public String text;

    protected boolean defaultState;

    protected List<Setting> children = new ArrayList<>();
    protected boolean expanded = false;

    public SettingToggle(String text, boolean state) {
        this.state = state;
        this.text = text;

        defaultState = state;
    }

    public String getName() {
        return text;
    }

    public void render(ModuleWindow window, MatrixStack matrix, int x, int y, int len) {
        String color2;

        if (state) {
            if (window.mouseOver(x, y, x + len, y + 12)) color2 = "\u00a72";
            else color2 = "\u00a7a";
        } else {
            if (window.mouseOver(x, y, x + len, y + 12)) color2 = "\u00a74";
            else color2 = "\u00a7c";
        }

        if (!children.isEmpty()) {
            if (window.rmDown && window.mouseOver(x, y, x + len, y + 12)) expanded = !expanded;

			/*if (expanded) {
				window.fillGreySides(x + 1, y, x + len - 2, y + 12);
				window.fillGreySides(x, y + 11, x + len - 1, y + getHeight(len));
				DrawableHelper.fill(x, y, x + len - 3, y + 1, 0x90000000);
				DrawableHelper.fill(x + 1, y + getHeight(len) - 2, x + 2, y + getHeight(len) - 1, 0x90000000);
				DrawableHelper.fill(x + 2, y + getHeight(len) - 1, x + len - 2, y + getHeight(len), 0x90b0b0b0);

				int h = y + 12;
				for (SettingBase s: children) {
					s.render(window, x + 1, h, len - 2);

					h += s.getHeight(len - 2);
				}
			}*/
            if (expanded) {
//                DrawableHelper.fill(matrix, x + 2, y + 12, x + 3, y + getHeight(len) - 1, 0x90b0b0b0);

                int h = y + 12;
                for (Setting s : children) {
                    s.render(window, matrix, x + 2, h, len - 2);

                    h += s.getHeight(len - 2);
                }
            }

            GL11.glPushMatrix();
            GL11.glScaled(0.65, 0.65, 1);
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix,
                    MinecraftClient.getInstance().textRenderer.trimToWidth(color2 + (expanded ? "[\u00a7lv" + color2 + "]" : "[\u00a7l>" + color2 + "]"), len), (int) ((x + len - 13) * 1 / 0.65), (int) ((y + 4) * 1 / 0.65), -1);
            GL11.glPopMatrix();
        }


        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix, MinecraftClient.getInstance().textRenderer.trimToWidth(color2 + text, len), x + 3, y + 2, 0xffffff);

        if (window.mouseOver(x, y, x + len, y + 12) && window.lmDown) state = !state;
    }

    public int getHeight(int len) {
        int h = 12;

        if (expanded) {
            h += 1;
            for (Setting s : children) h += s.getHeight(len - 2);
        }

        return h;
    }

    public Setting getChild(int c) {
        return children.get(c);
    }

    public SettingToggle withChildren(Setting... children) {
        this.children.addAll(Arrays.asList(children));
        return this;
    }

    public SettingToggle withDesc(String desc) {
        description = desc;
        return this;
    }

    public Triple<Integer, Integer, String> getGuiDesc(ModuleWindow window, int x, int y, int len) {
        if (!expanded || window.mouseY - y <= 12) return super.getGuiDesc(window, x, y, len);

        Triple<Integer, Integer, String> triple = null;

        int h = y + 12;
        for (Setting s : children) {
            if (window.mouseOver(x + 2, h, x + len, h + s.getHeight(len))) {
                triple = s.getGuiDesc(window, x + 2, h, len - 2);
            }

            h += s.getHeight(len - 2);
        }

        return triple;
    }

    public void readSettings(JsonElement settings) {
        if (settings.isJsonPrimitive()) {
            state = settings.getAsBoolean();
        } else if (settings.isJsonObject()) {
            JsonObject jo = settings.getAsJsonObject();
            if (!jo.has("toggled")) return;

            state = jo.get("toggled").getAsBoolean();

            for (Map.Entry<String, JsonElement> e : jo.get("children").getAsJsonObject().entrySet()) {
                for (Setting s : children) {
                    if (s.getName().equals(e.getKey())) {
                        s.readSettings(e.getValue());
                    }
                }
            }
        }
    }

    public JsonElement saveSettings() {
        if (children.isEmpty()) {
            return new JsonPrimitive(state);
        } else {
            JsonObject jo = new JsonObject();
            jo.add("toggled", new JsonPrimitive(state));

            JsonObject subJo = new JsonObject();
            for (Setting s : children) {
                subJo.add(s.getName(), s.saveSettings());
            }

            jo.add("children", subJo);
            return jo;
        }
    }

    @Override
    public boolean isDefault() {
        if (state != defaultState) return false;

        for (Setting s : children) {
            if (!s.isDefault()) return false;
        }

        return true;
    }
}
