package me.pm.lemon.gui.clickGui.elements;

import me.pm.lemon.module.Module;
import me.pm.lemon.utils.FileManager;
import me.pm.lemon.utils.LemonLogger;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModuleButton {
    public int x,y,width,height;

    private MinecraftClient mc = MinecraftClient.getInstance();

    private ModuleWindow parent;
    public Module mod;

    public boolean listening = false;

    public int mouseX;
    public int mouseY;

    public int keyDown = -1;
    public boolean lmDown = false;
    public boolean rmDown = false;
    public boolean mwDown = false;
    public boolean lmHeld = false;
    public int mwScroll = 0;

    public ModuleButton(Module mod, int x, int y, int width, int height, ModuleWindow parent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = parent;
        this.mod = mod;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
        Color color = new Color(50, 50, 50);
        Color color1 = new Color(100, 100, 100);

        RenderUtils.drawRect(x, y,
                x + width, y + height, mod.isToggled() ? color1.getRGB() : color.getRGB());

        mc.textRenderer.draw(matrixStack, mod.getName(), x + width/2 - (mc.textRenderer.getWidth(mod.getName())/2) , y + 3, -1);

        if (listening) {
            GL11.glPushMatrix();
            GL11.glTranslatef(mc.getWindow().getScaledWidth() / 2, mc.getWindow().getScaledHeight() / 2, 0.0F);
            GL11.glScalef(4.0F, 4.0F, 0F);
            mc.textRenderer.draw(matrixStack, "Listening...", 0 - mc.textRenderer.getWidth("Listening...") / 2, -7 - mc.textRenderer.fontHeight / 2F, 0xffffffff);
            GL11.glScalef(0.5F, 0.5F, 0F);
            mc.textRenderer.draw(matrixStack, "Press 'DEL' to unbind " + mod.getName() + (mod.getKeyCode() > -1 ? " (" + GLFW.glfwGetKeyName(mod.getKeyCode(),
                    GLFW.glfwGetKeyScancode(mod.getKeyCode())) + ")" : ""),
                    0 - mc.textRenderer.getWidth("Press 'DEL' to unbind " + mod.getName() + (mod.getKeyCode() > -1 ? " (" + GLFW.glfwGetKeyName(mod.getKeyCode(),
                    GLFW.glfwGetKeyScancode(mod.getKeyCode())) + ")" : "")) / 2, 7 - mc.textRenderer.fontHeight, 0xffffffff);

            if(keyDown != -1) {
                if (keyDown != GLFW.GLFW_KEY_DELETE) {
                    LemonLogger.infoMessage("Bound ","'" + mod.getName() + "'" + " to '" + GLFW.glfwGetKeyName(keyDown, GLFW.glfwGetKeyScancode(keyDown)) + "'");
                    mod.setKeyCode(keyDown);
                    FileManager.saveModules();
                } else {
                    LemonLogger.infoMessage("Unbound ","'" + mod.getName() + "'");
                    mod.setKeyCode(GLFW.GLFW_KEY_UNKNOWN);
                    FileManager.saveModules();
                }
                listening = false;
            }
            GL11.glPopMatrix();
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
}
