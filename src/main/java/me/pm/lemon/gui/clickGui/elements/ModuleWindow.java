package me.pm.lemon.gui.clickGui.elements;

import com.google.common.collect.Lists;
import me.pm.lemon.Main;
import me.pm.lemon.gui.clickGui.settings.Setting;
import me.pm.lemon.gui.clickGui.settings.SettingColor;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.utils.generalUtils.LemonColors;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.awt.*;
import java.util.ArrayList;

public class ModuleWindow {
    public Category cat;
    public int x, y;

    public Panel parent;
    private MinecraftClient mc = MinecraftClient.getInstance();

    public boolean selected;

    public ModuleButton selectedButton;
    public ArrayList<ModuleButton> moduleList;

    public int mouseX;
    public int mouseY;

    public int keyDown = -1;
    public boolean lmDown = false;
    public boolean rmDown = false;
    public boolean mwDown = false;
    public boolean lmHeld = false;
    public int mwScroll = 0;

    public ModuleWindow(Category category, int x, int y, Panel parent) {
        this.cat = category;
        this.x = x;
        this.y = y;

        this.parent = parent;
        this.moduleList = Lists.newArrayList();
        if(category != Category.HELP) {

            int modX = parent.x+70;
            int modY = parent.y+30;
            int modWidth = 95;
            int modHeight = 14;

            int i = 0;
            for(Module module : Module.getModulesInCat(cat)) {
                moduleList.add(new ModuleButton(module, modX, modY, modWidth, modHeight, this));
                modX += modWidth+16;
                i++;
                if(i >=3) {
                    modX = parent.x+70;
                    modY += modHeight+3;
                    i = 0;
                }
            }
        }
    }

    public int counter = 9000;

    public void render(MatrixStack matrixStack, int mouseX, int mouseY) {

        int centerX = x-(mc.textRenderer.getWidth(cat.toString())/2)+2;
        Color bg2 = new Color(40, 40, 40);
        Color color1 = new Color(100, 100, 100);
        mc.textRenderer.draw(matrixStack, cat.toString(), centerX, y, -1);
        if(counter == 9000) {
            counter = mc.textRenderer.getWidth(cat.toString());
        }
        if(this.selected) {
            if(counter<=0) {
                counter = 0;
            } else {
                RenderUtils.drawRect(centerX,y+12, centerX+counter, y+13, LemonColors.cool.getRGB());
                counter--;
            }
            if(selectedButton == null) {
                RenderUtils.drawRect(parent.x+65, parent.y+5,
                        (parent.x)+(parent.width - 5), (parent.y)+(20), color1.getRGB());

                mc.textRenderer.draw(matrixStack, cat.toString(), parent.x+65+((parent.width-60)/2)-(mc.textRenderer.getWidth(cat.toString())/2), parent.y+9, -1);
                RenderUtils.drawRect(parent.x+65, parent.y+25,
                        (parent.x)+(parent.width - 5), (parent.y)+(parent.height-5), bg2.getRGB());
                if(cat == Category.HELP) {
                    ArrayList<String> list = Lists.newArrayList();
                    if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 0) {
                        list.add("Author: " + Main.ClientInfo.clientCreators);
                        list.add("To toggle a module you have to click it with 'LMB'.");
                        list.add("To bind a module you have to click it with your scroll wheel.");
                        list.add("To open a module's settings you have to click it with 'RMB'");
                        list.add("If there're any bugs or ideas you'd like to report,");
                        list.add("PM me on discord: ThatsNotLiam#2303");
                        list.add("");
                        list.add("To change the gui's position, simply hover and click over");
                        list.add("the yellow marked area.");
                        list.add("");
                        list.add("This utility mod is still in development,");
                        list.add("this means that I will add multiple features, patches");
                        list.add("and updates until the end of the project.");
                        list.add("");
                        list.add("Możesz zmienić język moda wchodząc w ClickGUI>Settings");
                    } else if(Module.getModule(ClickGui.class).getSetting(6).asMode().mode == 1) {
                        list.add("Autor: " + Main.ClientInfo.clientCreators);
                        list.add("Aby włączyć moduł musisz klinąć na niego lewym");
                        list.add("przyciskiem myszy. (LPM)");
                        list.add("Aby zbindować moduł należy kliknąć na niego scrollem.");
                        list.add("Aby otworzyć ustawienia modułu musisz kliknąć na niego prawym,");
                        list.add("przyciskiem myszy. (PPM)");
                        list.add("Jeśli masz jakieś pomysły lub błędy, które chciałbyś zgłosić,");
                        list.add("napisz do mnie na discordzie: ThatsNotLiam#2303");
                        list.add("");
                        list.add("Aby zmienić pozycję gui należy najechać i kliknąć,");
                        list.add("na żółto oznaczone miejsce i przeciągnąć myszką po ekranie.");
                        list.add("");
                        list.add("Ten mod jest w ciągłym rozwijaniu,");
                        list.add("to znaczy, że mod będzie otrzymywać nowe updaty/poprawki,");
                        list.add("oraz będzie aktualizowany do najnowszej wersji Minecrafta,");
                        list.add("aż do zakończenia projektu.");
                        list.add("");
                        list.add("You can change the mod language in ClickGUI>Settings");
                    }

                    int y1 = parent.y+30;
                    for(String string : list) {
                        mc.textRenderer.draw(matrixStack, string, parent.x+70, y1, -1);
                        y1 += 12;
                    }

                    return;
                }

                if(!moduleList.isEmpty() && moduleList != null) {
                    for(ModuleButton moduleButton : moduleList) {
                        moduleButton.render(matrixStack, mouseX, mouseY);
                        moduleButton.updateKeys(mouseX, mouseY, keyDown, lmDown, rmDown, mwDown, lmHeld, mwScroll);

                        if(lmDown && selectedButton == null && mouseOver(moduleButton.x, moduleButton.y,
                                moduleButton.x+moduleButton.width, moduleButton.y+moduleButton.height)) {
                            moduleButton.mod.toggle();
                        }
                        if(rmDown && mouseOver(moduleButton.x, moduleButton.y,
                                moduleButton.x+moduleButton.width, moduleButton.y+moduleButton.height)) {
                            selectedButton = moduleButton;
                        }
                        if(mwDown && mouseOver(moduleButton.x, moduleButton.y,
                                moduleButton.x+moduleButton.width, moduleButton.y+moduleButton.height)) {
                            moduleButton.listening = true;
                        }
                    }
                }
            } else {
                RenderUtils.drawRect(parent.x+65, parent.y+5,
                        (parent.x)+(parent.width - 5), (parent.y)+(20), color1.getRGB());

                mc.textRenderer.draw(matrixStack, selectedButton.mod.getName(), parent.x+65+((parent.width-60)/2)-(mc.textRenderer.getWidth(selectedButton.mod.getName())/2), parent.y+9, -1);
                RenderUtils.drawRect(parent.x+65, parent.y+25,
                        (parent.x)+(parent.width - 5), (parent.y)+(parent.height-5), bg2.getRGB());
                if(selectedButton.mod.getSettings().isEmpty() || selectedButton.mod.getSettings() == null) {
                    mc.textRenderer.draw(matrixStack, "No settings available for this module.", parent.x+(parent.width/3), parent.y+59, -1);
                } else {
                    int settingX = parent.x+70;
                    int settingY = parent.y+30;
                    int settingWidth = 100;
                    for(Setting setting : selectedButton.mod.getSettings()) {
                        setting.render(this, matrixStack, settingX, settingY, setting instanceof SettingColor ? settingWidth / 2 : settingWidth);
                        settingY += setting.getHeight(settingWidth);
                        if(settingY >= 225) {
                            settingY = parent.y+35;
                            settingX += 120;
                        }
                    }
                }
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

    public boolean mouseOver(int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY < maxY;
    }


    public void fillGrey(MatrixStack matrix, int x1, int y1, int x2, int y2) {
        DrawableHelper.fill(matrix, x1, y1, x2 - 1, y2 - 1, 0xffb0b0b0);
        DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2, y2, 0xff000000);
        DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2 - 1, y2 - 1, 0xff858585);
    }

    public void fillGradient(MatrixStack matrix, int x1, int y1, int x2, int y2, int color1, int color2) {
        float float_1 = (color1 >> 24 & 255) / 255.0F;
        float float_2 = (color1 >> 16 & 255) / 255.0F;
        float float_3 = (color1 >> 8 & 255) / 255.0F;
        float float_4 = (color1 & 255) / 255.0F;
        float float_5 = (color2 >> 24 & 255) / 255.0F;
        float float_6 = (color2 >> 16 & 255) / 255.0F;
        float float_7 = (color2 >> 8 & 255) / 255.0F;
        float float_8 = (color2 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glShadeModel(7425);
        Tessellator tessellator_1 = Tessellator.getInstance();
        BufferBuilder bufferBuilder_1 = tessellator_1.getBuffer();
        bufferBuilder_1.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder_1.vertex(x1, y1, 0).color(float_2, float_3, float_4, float_1).next();
        bufferBuilder_1.vertex(x1, y2, 0).color(float_2, float_3, float_4, float_1).next();
        bufferBuilder_1.vertex(x2, y2, 0).color(float_6, float_7, float_8, float_5).next();
        bufferBuilder_1.vertex(x2, y1, 0).color(float_6, float_7, float_8, float_5).next();
        tessellator_1.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void fillReverseGrey(MatrixStack matrix, int x1, int y1, int x2, int y2) {
        DrawableHelper.fill(matrix, x1, y1, x1 + 1, y2 - 1, 0x90000000);
        DrawableHelper.fill(matrix, x1 + 1, y1, x2 - 1, y1 + 1, 0x90000000);
        DrawableHelper.fill(matrix, x1 + 1, y2 - 1, x2, y2, 0x90b0b0b0);
        DrawableHelper.fill(matrix, x2 - 1, y1 + 1, x2, y2 - 1, 0x90b0b0b0);
        DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2 - 1, y2 - 1, 0xff505059);
    }
}