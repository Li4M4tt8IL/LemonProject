package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.*;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ModuleList extends ModuleDraggable {
    public ModuleList() {
        super("ModuleList", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        List<String> moduleList = new ArrayList<>();
        String name;
        for (Module mod : ModuleManager.getModules()) {
            if (mod.isToggled() && !mod.isCategory(Category.GUI)) {
                name = mod.getDisplayName();
                if (name == null) {
                    name = mod.getName();
                }
                moduleList.add(name);
            }
        }
        moduleList.sort((mod1, mod2) ->
                Integer.compare(mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod2)), mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod1))));
        if(moduleList.isEmpty()) {
            return 10;
        }
        return MinecraftClient.getInstance().textRenderer.getWidth(moduleList.get(0)) + 6;
    }

    @Override
    public int getHeight() {
        int yCount = 2;
        List<String> moduleList = new ArrayList<>();
        String name;
        for (Module mod : ModuleManager.getModules()) {
            if (mod.isToggled() && !mod.isCategory(Category.GUI)) {
                name = mod.getDisplayName();
                if (name == null) {
                    name = mod.getName();
                }
                moduleList.add(name);
            }
        }
        for (String s : moduleList) {
            yCount += 10;
        }
        return yCount;
    }

    @Override
    public void render(ScreenPosition pos) {
        if(getModule(Hud.class).isToggled()) {
            if(getModule(Hud.class).getSetting(1).asToggle().state) {
                MatrixStack matrixStack = new MatrixStack();
                List<String> moduleList = new ArrayList<>();
                String name;
                for (Module mod : ModuleManager.getModules()) {
                    if (mod.isToggled() && !mod.isCategory(Category.GUI)) {
                        name = mod.getDisplayName();
                        if (name == null) {
                            name = mod.getName();
                        }
                        moduleList.add(name);
                    }
                }

                int yCount = pos.getAbsoluteY() + 1;
                if (getModule(Hud.class).getSetting(1).asToggle().getChild(3).asMode().mode == 0) {
                    moduleList.sort((mod1, mod2) ->
                            Integer.compare(mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod2)), mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod1))));
                }
                if (getModule(Hud.class).getSetting(1).asToggle().getChild(3).asMode().mode == 1) {
                    moduleList.sort(Comparator.comparingInt(mod -> mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod))));
                }
                final int[] counter = {1};
                for (String s : moduleList) {
                    boolean rainbow = getModule(Hud.class).getSetting(1).asToggle().getChild(0).asToggle().state;
                    Color moduleColor = Objects.requireNonNull(Module.getModuleByDisplayName(s)).getColor();
                    if(getModule(Hud.class).getSetting(1).asToggle().getChild(4).asMode().mode == 1) {
                        int right = pos.getAbsoluteX() + 5;
                        if (getModule(Hud.class).getSetting(1).asToggle().getChild(1).asToggle().state) {
                            RenderUtils.drawRect(right + mc.textRenderer.getWidth(s) + 2, yCount + 9, right  - 2, yCount - 1,
                                    new Color(0, 0, 0, 100).getRGB());
                        }
                        if (getModule(Hud.class).getSetting(1).asToggle().getChild(2).asToggle().state) {
                            RenderUtils.drawRect(right + mc.textRenderer.getWidth(s) + 3, yCount + 9, right + mc.textRenderer.getWidth(s) + 2, yCount - 1,
                                    rainbow ? Util.rainbow(counter[0] * 500) : moduleColor.getRGB());
                        }
                        mc.textRenderer.drawWithShadow(matrixStack, s, right, yCount, rainbow ? Util.rainbow(counter[0] * 500) : moduleColor.getRGB());
                        yCount += 10;
                    } else if(getModule(Hud.class).getSetting(1).asToggle().getChild(4).asMode().mode == 0) {
                        int right = pos.getAbsoluteX() + 5 + MinecraftClient.getInstance().textRenderer.getWidth(moduleList.get(0));
                        if (getModule(Hud.class).getSetting(1).asToggle().getChild(1).asToggle().state) {
                            RenderUtils.drawRect(right + 2, yCount + 9, right - mc.textRenderer.getWidth(s) - 2, yCount - 1, new Color(0, 0, 0, 100).getRGB());
                        }
                        if (getModule(Hud.class).getSetting(1).asToggle().getChild(2).asToggle().state) {
                            RenderUtils.drawRect(right - mc.textRenderer.getWidth(s) - 2, yCount + 9, right - mc.textRenderer.getWidth(s) - 3, yCount - 1,
                                    rainbow ? Util.rainbow(counter[0] * 500) : moduleColor.getRGB());
                        }
                        mc.textRenderer.drawWithShadow(matrixStack, s, right - mc.textRenderer.getWidth(s), yCount, rainbow ? Util.rainbow(counter[0] * 500) : moduleColor.getRGB());
                        yCount += 10;
                        counter[0]++;
                    }
                }
            }
        }
    }
}
