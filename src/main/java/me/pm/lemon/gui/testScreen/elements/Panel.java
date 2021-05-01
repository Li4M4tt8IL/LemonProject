package me.pm.lemon.gui.testScreen.elements;

import me.pm.lemon.gui.testScreen.ClickGuiScreen;
import me.pm.lemon.module.Category;
import me.pm.lemon.utils.generalUtils.LemonColors;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel {
    public int width, height, x, y, x2, y2;
    public List<ModuleWindow> moduleWindowList;
    public ModuleWindow selectedModuleWindow;

    public int mouseX;
    public int mouseY;

    public int keyDown = -1;
    public boolean lmDown = false;
    public boolean rmDown = false;
    public boolean mwDown = false;
    public boolean lmHeld = false;
    public int mwScroll = 0;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public boolean dragging = false;
    private int dragOffX;
    private int dragOffY;

    public ClickGuiScreen parent;

    public Panel(ClickGuiScreen parent, int x, int y, int width, int height) {
        this.parent = parent;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.moduleWindowList = new ArrayList<>();

        int xPlus = x+28;
        int yPlus = y+100;
        this.moduleWindowList.add(new ModuleWindow(Category.MOVEMENT, xPlus, yPlus, this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.EXPLOITS, xPlus, yPlus,this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.COMBAT, xPlus, yPlus, this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.RENDER, xPlus, yPlus,this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.PLAYER, xPlus, yPlus, this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.WORLD, xPlus, yPlus,this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.CHAT, xPlus, yPlus, this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.MISC, xPlus, yPlus, this));
        yPlus += 16;
        this.moduleWindowList.add(new ModuleWindow(Category.GUI, xPlus, yPlus, this));
        yPlus += 18;
        this.moduleWindowList.add(new ModuleWindow(Category.HELP, xPlus, yPlus, this));

        for(ModuleWindow mw:moduleWindowList) {
            if(mw.cat == Category.HELP) {
                this.selectedModuleWindow = mw;
            }
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
        Color bg = new Color(30, 30, 30);
        Color bg1 = new Color(48, 48, 48);

//        if(lmHeld && mouseOver(x, y, x+width, y+height)) {
////            dragOffX = mouseX - x;
////            dragOffY = mouseY - y;
//        }

        if(lmHeld && mouseOver(x, y, x+5, y+5)) {
            x = Math.max(0, mouseX - dragOffX);
            y = Math.max(0, mouseY - dragOffY);

            int xPlus = x+28;
            int yPlus = y+100;
            for(ModuleWindow mw : moduleWindowList) {
                mw.x = xPlus;
                mw.y = yPlus;
                if(mw.cat == Category.GUI) {
                    yPlus += 18;
                } else {
                    yPlus += 16;
                }
                int modX = x+70;
                int modY = y+30;
                int modWidth = 95;
                int modHeight = 14;

                int i = 0;
                for(ModuleButton mb : mw.moduleList) {
                    mb.x = modX;
                    mb.y = modY;
                    modX += modWidth+16;
                    i++;
                    if(i >=3) {
                        modX = x+70;
                        modY += modHeight+3;
                        i = 0;
                    }
                }
            }
        }

        RenderUtils.drawRect(x, y, x+width, y+height, bg.getRGB());
        RenderUtils.drawRect(x, y, x+60, y+height, bg1.getRGB());
        RenderUtils.drawRect(x, y, x+5, y+5, LemonColors.cool.getRGB());

        for(ModuleWindow moduleWindow : moduleWindowList) {
            moduleWindow.render(matrixStack, mouseX, mouseY);
            moduleWindow.updateKeys(mouseX, mouseY, keyDown, lmDown, rmDown, mwDown, lmHeld, mwScroll);

            if(lmDown && mouseOver(x, moduleWindow.y, moduleWindow.x+mc.textRenderer.getWidth(moduleWindow.cat.toString()), moduleWindow.y+12)) {
                if (selectedModuleWindow != null) {
                    selectedModuleWindow.selected = false;
                    selectedModuleWindow.counter = mc.textRenderer.getWidth(selectedModuleWindow.cat.toString());
                    moduleWindow.selectedButton = null;
                }
                selectedModuleWindow = moduleWindow;
                moduleWindow.selected = true;
            }
        }

        lmDown = false;
        rmDown = false;
        mwDown = false;
        keyDown = -1;
        mwScroll = 0;
    }

    public void updateKeys(int mouseX, int mouseY, int keyDown, boolean lmDown, boolean rmDown, boolean mwDown, boolean lmHeld, int mwScroll) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.keyDown = keyDown;
        this.lmDown = lmDown;
        this.rmDown = rmDown;
        this.mwDown = mwDown;
        this.lmHeld = lmHeld;
        this.mwScroll = mwScroll;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean mouseOver(int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY < maxY;
    }
}