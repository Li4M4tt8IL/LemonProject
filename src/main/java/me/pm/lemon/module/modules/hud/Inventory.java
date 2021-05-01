package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Inventory extends ModuleDraggable {
    public Inventory() {
        super("Inventory", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        return 144;
    }

    @Override
    public int getHeight() {
        return 64;
    }

    @Override
    public void render(ScreenPosition pos) {
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(7).asToggle().state) {
            int x = 0;
            int y = 0;
            RenderUtils.drawRect(pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getAbsoluteX() + getWidth() + 1, pos.getAbsoluteY() + getHeight() + 1, new Color(0, 0, 0, 100).getRGB());
            assert mc.player != null;
            for(ItemStack is : mc.player.inventory.main) {
                if(x == 9) {
                    x = 0;
                    y++;
                }
                renderItemStack(pos, x, y, is);
                x++;
            }
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(7).asToggle().state) {
            ItemStack itemStack = new ItemStack(Items.STONE);
            renderItemStack(pos,0,3, itemStack);
            renderItemStack(pos,1,3, itemStack);
            renderItemStack(pos,2,3, itemStack);
            renderItemStack(pos,3,3, itemStack);
            renderItemStack(pos,4,3, itemStack);
            renderItemStack(pos,5,3, itemStack);
            renderItemStack(pos,6,3, itemStack);
            renderItemStack(pos,7,3, itemStack);
            renderItemStack(pos,8,3, itemStack);

            renderItemStack(pos,0,2, itemStack);
            renderItemStack(pos,1,2, itemStack);
            renderItemStack(pos,2,2, itemStack);
            renderItemStack(pos,3,2, itemStack);
            renderItemStack(pos,4,2, itemStack);
            renderItemStack(pos,5,2, itemStack);
            renderItemStack(pos,6,2, itemStack);
            renderItemStack(pos,7,2, itemStack);
            renderItemStack(pos,8,2, itemStack);

            renderItemStack(pos,0,1, itemStack);
            renderItemStack(pos,1,1, itemStack);
            renderItemStack(pos,2,1, itemStack);
            renderItemStack(pos,3,1, itemStack);
            renderItemStack(pos,4,1, itemStack);
            renderItemStack(pos,5,1, itemStack);
            renderItemStack(pos,6,1, itemStack);
            renderItemStack(pos,7,1, itemStack);
            renderItemStack(pos,8,1, itemStack);

            renderItemStack(pos,0,0, itemStack);
            renderItemStack(pos,1,0, itemStack);
            renderItemStack(pos,2,0, itemStack);
            renderItemStack(pos,3,0, itemStack);
            renderItemStack(pos,4,0, itemStack);
            renderItemStack(pos,5,0, itemStack);
            renderItemStack(pos,6,0, itemStack);
            renderItemStack(pos,7,0, itemStack);
            renderItemStack(pos,8,0, itemStack);

        }
    }

    private void renderItemStack(ScreenPosition pos, int x, int y, ItemStack is) {
        if(is == null) return;

        int yAdd = (-16 * y) + 48;
        int xAdd = (16 * x);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        mc.getItemRenderer().zOffset = 200F;

        mc.getItemRenderer().renderGuiItemIcon(is, pos.getAbsoluteX() + xAdd, pos.getAbsoluteY() + yAdd);
        mc.getItemRenderer().renderGuiItemOverlay(mc.textRenderer, is, pos.getAbsoluteX() + xAdd, pos.getAbsoluteY() + yAdd);

        mc.getItemRenderer().zOffset = 0F;
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }
}
