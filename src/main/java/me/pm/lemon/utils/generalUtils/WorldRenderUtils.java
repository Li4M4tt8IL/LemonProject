package me.pm.lemon.utils.generalUtils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.awt.*;
import java.util.Map.Entry;

public class WorldRenderUtils {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawText(String str, double x, double y, double z, double scale) {
        glSetup(x, y, z);

        GL11.glScaled(-0.025 * scale, -0.025 * scale, 0.025 * scale);

        int i = mc.textRenderer.getWidth(str) / 2;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, VertexFormats.POSITION_COLOR);
        float f = mc.options.getTextBackgroundOpacity(0.25F);
        bufferbuilder.vertex(-i - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, f).next();
        bufferbuilder.vertex(-i - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, f).next();
        bufferbuilder.vertex(i + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, f).next();
        bufferbuilder.vertex(i + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, f).next();
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        mc.textRenderer.draw(new MatrixStack(), str, -i, 0, 553648127);
        mc.textRenderer.draw(new MatrixStack(), str, -i, 0, -1);

        glCleanup();
    }

    public static void drawIcon(Identifier TEXTURE, String str, double x, double y, double z, double scale) {
        glSetup(x, y, z);

        GL11.glScaled(-0.025 * scale, -0.025 * scale, 0.025 * scale);

        int i = mc.textRenderer.getWidth(str) / 2;
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        RenderSystem.disableBlend();
        mc.getTextureManager().bindTexture(TEXTURE);
        Screen.drawTexture(new MatrixStack(), 0, 0, 0, 0, 32, 32, 32, 32);

        glCleanup();
    }

    public static void drawItem(MatrixStack matrixStack, double x, double y, double z, double offX, double offY, double scale, ItemStack item) {
        matrixStack.push();
        glSetup(x, y, z);
        GL11.glScaled(0.4 * scale, 0.4 * scale, 0);

        GL11.glTranslated(offX, offY, 0);
        if (item.getItem() instanceof BlockItem) GL11.glRotatef(180F, 1F, 180F, 10F);
        mc.getItemRenderer().renderItem(item, Mode.GUI, 0, 0, matrixStack, mc.getBufferBuilders().getEntityVertexConsumers());
        mc.getBufferBuilders().getEntityVertexConsumers().draw();

        if (item.getItem() instanceof BlockItem) GL11.glRotatef(-180F, -1F, -180F, -10F);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glScalef(-0.05F, -0.05F, 0);

        if (item.getCount() > 0) {
            int w = mc.textRenderer.getWidth("x" + item.getCount()) / 2;
            mc.textRenderer.drawWithShadow(matrixStack, "x" + item.getCount(), 7 - w, 5, 0xffffff);
        }

        GL11.glScalef(0.85F, 0.85F, 0.85F);

        int c = 0;
        for (Entry<Enchantment, Integer> m : EnchantmentHelper.get(item).entrySet()) {
            String text = I18n.translate(m.getKey().getName(3).getString());

            if (text.isEmpty()) continue;

            String subText = text.substring(0, Math.min(text.length(), 3)) + m.getValue();

            int w1 = mc.textRenderer.getWidth(subText) / 2;
            mc.textRenderer.drawWithShadow(new MatrixStack(),
                    subText, -4 - w1, c * 10 - 1,
                    m.getKey() == Enchantments.VANISHING_CURSE || m.getKey() == Enchantments.BINDING_CURSE
                            ? 0xff5050 : 0xffb0e0);
            c--;
        }

        GL11.glScalef(0.6F, 0.6F, 0.6F);
        if (item.isDamageable() && item.getMaxDamage() > 0) {
            String dur = item.getMaxDamage() - item.getDamage() + "";
            int color = MathHelper.hsvToRgb((float) MathHelper.clamp(item.getMaxDamage() - item.getDamage() / item.getMaxDamage(),
                    0f,
                    1f) / 3.0F, 1.0F, 1.0F);
            mc.textRenderer.drawWithShadow(new MatrixStack(), dur, -8 - dur.length() * 3, 15, new Color(color >> 16 & 255, color >> 8 & 255, color & 255).getRGB());
        }

        matrixStack.pop();
        glCleanup();
    }

    public static void glSetup(double x, double y, double z) {
        GL11.glPushMatrix();
        RenderUtils.offsetRender();
        GL11.glTranslated(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-mc.player.yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(mc.player.pitch, 1.0F, 0.0F, 0.0F);
        GL11.glDepthFunc(GL11.GL_ALWAYS);

        GL11.glEnable(GL11.GL_BLEND);
        GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
    }

    public static void glCleanup() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
}
