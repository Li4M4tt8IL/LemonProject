package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


public class Armor extends ModuleDraggable {
    public Armor() {
        super("Armor", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 1) return 64;
        if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 0) return 96;
        return 6;
    }

    @Override
    public int getHeight() {
        if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 1) return 96;
        if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 0) return 16;
        return 6;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(4).asToggle().state) {
            if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 1) {
                for(int i = 0; i < mc.player.inventory.armor.size(); i++) {
                    ItemStack itemStack = mc.player.inventory.armor.get(i);
                    renderItemStack(pos,0, i - 1, itemStack);
                }
                renderItemStack(pos,0,3, mc.player.getMainHandStack());
                renderItemStack(pos,0,-2, mc.player.getOffHandStack());
            } else if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 0) {
                for(int i = 0; i < mc.player.inventory.armor.size(); i++) {
                    ItemStack itemStack = mc.player.inventory.armor.get(i);
                    renderItemStack(pos, i + 1, 3, itemStack);
                }
                renderItemStack(pos,5,3, mc.player.getMainHandStack());
                renderItemStack(pos,0,3, mc.player.getOffHandStack());
            }
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(4).asToggle().state) {
            if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 1) {
                renderItemStack(pos,0,2, new ItemStack(Items.DIAMOND_HELMET));
                renderItemStack(pos,0,1, new ItemStack(Items.DIAMOND_CHESTPLATE));
                renderItemStack(pos,0,0, new ItemStack(Items.DIAMOND_LEGGINGS));
                renderItemStack(pos,0,-1, new ItemStack(Items.DIAMOND_BOOTS));

                renderItemStack(pos,0,3, new ItemStack(Items.DIAMOND_SWORD));
                renderItemStack(pos,0,-2, new ItemStack(Items.TOTEM_OF_UNDYING));
            } else if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 0) {
                renderItemStack(pos,4,3, new ItemStack(Items.DIAMOND_HELMET));
                renderItemStack(pos,3,3, new ItemStack(Items.DIAMOND_CHESTPLATE));
                renderItemStack(pos,2,3, new ItemStack(Items.DIAMOND_LEGGINGS));
                renderItemStack(pos,1,3, new ItemStack(Items.DIAMOND_BOOTS));

                renderItemStack(pos,5,3, new ItemStack(Items.DIAMOND_SWORD));
                renderItemStack(pos,0,3, new ItemStack(Items.TOTEM_OF_UNDYING));
            }
        }
    }

    private void renderItemStack(ScreenPosition pos, int x, int y, ItemStack is) {
        if(is == null) return;

        int yAdd = (-16 * y) + 48;
        int xAdd = (16 * x);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        mc.getItemRenderer().zOffset = 200F;

        mc.getItemRenderer().renderGuiItemIcon(is, pos.getAbsoluteX() + xAdd, pos.getAbsoluteY() + yAdd);
        mc.getItemRenderer().renderGuiItemOverlay(mc.textRenderer, is, pos.getAbsoluteX() + xAdd, pos.getAbsoluteY() + yAdd);
        if(getModule(Hud.class).getSetting(4).asToggle().getChild(1).asMode().mode == 1) {
            if(is.getMaxDamage() != 0) {
                mc.textRenderer.drawWithShadow(new MatrixStack(), (is.getMaxDamage()-is.getDamage()) + "/" + is.getMaxDamage(), pos.getAbsoluteX() + xAdd + 20, pos.getAbsoluteY() + (yAdd + 6), -1);
            }
        }

        mc.getItemRenderer().zOffset = 0F;
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }
}
