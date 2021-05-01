package me.pm.lemon.utils.generalUtils;

import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.render.Nametags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.awt.*;
import java.util.Map;

public class FeatureItemRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    public FeatureItemRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    private final MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, T e, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        double c = 0;
        double scale = Math.max(Module.getModule(Nametags.class).getSetting(0).asToggle().getChild(0).asSlider().getValue() * (mc.cameraEntity.distanceTo(e) / 20), 1);
        double x = e.prevX + (e.getX() - e.prevX) * mc.getTickDelta();
        double y = (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + ((0.75) * scale);
        double z = e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta();
        double offX = c+1.5;
        double offY = 0;
        for(ItemStack item : e.getArmorItems()) {
            glSetup(x, y, z);

            GL11.glScaled(0.4 * scale, 0.4 * scale, 0);

            GL11.glTranslated(offX, offY, 0);
            if (item.getItem() instanceof BlockItem) GL11.glRotatef(180F, 1F, 180F, 10F);
            mc.getItemRenderer().renderItem(item, ModelTransformation.Mode.GUI, 0, 0, matrixStack, mc.getBufferBuilders().getEntityVertexConsumers());
            mc.getBufferBuilders().getEntityVertexConsumers().draw();

            if (item.getItem() instanceof BlockItem) GL11.glRotatef(-180F, -1F, -180F, -10F);
            GL11.glDisable(GL11.GL_LIGHTING);

            GL11.glScalef(-0.05F, -0.05F, 0);

            if (item.getCount() > 0) {
                int w = mc.textRenderer.getWidth("x" + item.getCount()) / 2;
                mc.textRenderer.drawWithShadow(matrixStack, "x" + item.getCount(), 7 - w, 5, 0xffffff);
            }

            GL11.glScalef(0.85F, 0.85F, 0.85F);

            int c1 = 0;
            for (Map.Entry<Enchantment, Integer> m : EnchantmentHelper.get(item).entrySet()) {
                String text = I18n.translate(m.getKey().getName(3).getString());

                if (text.isEmpty()) continue;

                String subText = text.substring(0, Math.min(text.length(), 3)) + m.getValue();

                int w1 = mc.textRenderer.getWidth(subText) / 2;
                mc.textRenderer.drawWithShadow(new MatrixStack(),
                        subText, -4 - w1, c1 * 10 - 1,
                        m.getKey() == Enchantments.VANISHING_CURSE || m.getKey() == Enchantments.BINDING_CURSE
                                ? 0xff5050 : 0xffb0e0);
                c1--;
            }

            GL11.glScalef(0.6F, 0.6F, 0.6F);
            if (item.isDamageable() && item.getMaxDamage() > 0) {
                String dur = item.getMaxDamage() - item.getDamage() + "";
                int color = MathHelper.hsvToRgb((float) MathHelper.clamp(item.getMaxDamage() - item.getDamage() / item.getMaxDamage(),
                        0f,
                        1f) / 3.0F, 1.0F, 1.0F);
                mc.textRenderer.drawWithShadow(new MatrixStack(), dur, -8 - dur.length() * 3, 15, new Color(color >> 16 & 255, color >> 8 & 255, color & 255).getRGB());
            }

            glCleanup();
            c--;
        }
    }

    public void glSetup(double x, double y, double z) {
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

    public void glCleanup() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
}
