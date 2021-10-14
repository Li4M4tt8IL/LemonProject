package me.pm.lemon.module.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.EntityRenderEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import me.pm.lemon.utils.generalUtils.NametagUtils;
import me.pm.lemon.utils.generalUtils.Vec3;
import me.pm.lemon.utils.generalUtils.WorldRenderUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Nametags extends Module {

    private final Vec3 pos = new Vec3();
    private final double[] itemWidths = new double[6];
    private final Color WHITE = new Color(255, 255, 255);
    private final Color RED = new Color(255, 25, 25);
    private final Color AMBER = new Color(255, 105, 25);
    private final Color GREEN = new Color(25, 252, 25);
    private final Color GOLD = new Color(232, 185, 35);
    private final Color GREY = new Color(150, 150, 150);
    private final Color METEOR = new Color(135, 0, 255);
    private final Color BLUE = new Color(20, 170, 170);
    private final Map<Enchantment, Integer> enchantmentsToShowScale = new HashMap<>();

    public Nametags(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Players", true).withChildren(
                        new SettingSlider("Size", 0.5, 5, 1, 1)),
                new SettingToggle("Mobs", true).withChildren(
                        new SettingSlider("Size", 0.5, 5, 1, 1)),
                new SettingToggle("Items", true).withChildren(
                        new SettingSlider("Size", 0.5, 5, 1, 1),
                        new SettingToggle("Custom Name", true)),
                new SettingToggle("Armor", false));
    }

    @EventTarget
    public void onLivingLabelRender(EntityRenderEvent.Label event) {
        if (((event.getEntity() instanceof Monster || EntityUtil.isAnimal(event.getEntity())) && getSetting(1).asToggle().state)
                || (event.getEntity() instanceof PlayerEntity && getSetting(0).asToggle().state)
                || (event.getEntity() instanceof ItemEntity && getSetting(2).asToggle().state))
            event.setCancelled(true);
    }

    @EventTarget
    public void onLivingRender(EntityRenderEvent.Render event) {
        if (event.getEntity() instanceof ItemEntity && getSetting(2).asToggle().state) {
            ItemEntity e = (ItemEntity) event.getEntity();

            double scale = Math.max(getSetting(2).asToggle().getChild(0).asSlider().getValue() * (mc.cameraEntity.distanceTo(e) / 20), 1);
            if (!e.getName().getString().equals(e.getStack().getName().getString()) && getSetting(2).asToggle().getChild(1).asToggle().state) {
                WorldRenderUtils.drawText("\u00a76\"" + e.getStack().getName().getString() + "\"",
                        e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                        (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.75f * scale),
                        e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
            }

            WorldRenderUtils.drawText("\u00A79" + e.getStack().getCount() + "x " + e.getName().getString(),
                    e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                    (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
                    e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
        } else if (event.getEntity() instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) event.getEntity();
            if(e instanceof PlayerEntity && getSetting(0).asToggle().state) {
                double scale = Math.max(getSetting(0).asToggle().getChild(0).asSlider().getValue() * (mc.cameraEntity.distanceTo(e) / 20), 1);

                int ping = 0;
                try {
                    ping = Objects.requireNonNull(mc.player.networkHandler.getPlayerListEntry(e.getName().getString())).getLatency();
                } catch (Exception ignored) { }
                String aaa = "";
//                if(e.getName().getString().equals(Main.ClientInfo.clientCreators)) {
//                    aaa = Main.getOnlinePlayers().contains(e.getName().getString()) ? "\u00A78[\u00A7bPM DEV\u00A78]\u00A7f " : "";
//                } else {
//                    aaa = Main.getOnlinePlayers().contains(e.getName().getString()) ? "\u00A78[\u00A7bPM\u00A78]\u00A7f " : "";
//                }
                if (getFriendManager().isFriend(e.getName().getString())) {
                    WorldRenderUtils.drawText("\u00A7b" + aaa + e.getName().getString() + " " + getPingColor(ping) + ping + "ms " + getHealthColor(e) +
                                    (int) (e.getHealth() + e.getAbsorptionAmount()),
                            e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
                } else {
                    WorldRenderUtils.drawText("\u00A7c"+ aaa + e.getName().getString() + " " + getPingColor(ping) + ping + "ms " + getHealthColor(e) +
                                    (int) (e.getHealth() + e.getAbsorptionAmount()),
                            e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
                }
                if(getSetting(3).asToggle().state) {
                    double c = 0;
                    MatrixStack matrixStack = new MatrixStack();
//                    WorldRenderUtils.drawItem(matrixStack,e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + ((0.75) * scale),
//                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), -2.5, 0, scale, e.getEquippedStack(EquipmentSlot.MAINHAND));
//                    WorldRenderUtils.drawItem(matrixStack,e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + ((0.75) * scale),
//                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), 2.5, 0, scale, e.getEquippedStack(EquipmentSlot.OFFHAND));
                    for (ItemStack i: ((PlayerEntity) e).inventory.armor) {
//                        WorldRenderUtils.drawItem(matrixStack,e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                                (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + ((0.75) * scale),
//                                e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), c+1.5, 0, scale, i);
                        c--;
                    }
                }
            } else if((e instanceof Monster || EntityUtil.isAnimal(e)) && getSetting(1).asToggle().state) {
                double scale = Math.max(getSetting(1).asToggle().getChild(0).asSlider().getValue() * (mc.cameraEntity.distanceTo(e) / 20), 1);
                WorldRenderUtils.drawText("\u00A79" + e.getName().getString() + " " + getHealthColor(e) + (int) (e.getHealth() + e.getAbsorptionAmount()),
                        e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                        (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
                        e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
            }

            event.setCancelled(true);
        }
    }

//    @EventTarget
//    public void onLivingRender(EntityRenderEvent.Render event) {
//        Vec3d rPos = getRenderPos(event.getEntity());
//        List<String> lines = new ArrayList<>();
//        double scale = 0;
//
//        if (event.getEntity() instanceof ItemEntity && getSetting(2).asToggle().state) {
//            ItemEntity e = (ItemEntity) event.getEntity();
//
//            double scale = Math.max(getSetting(2).asToggle().getChild(0).asSlider().getValue() * (mc.cameraEntity.distanceTo(e) / 20), 1);
//            if (!e.getName().getString().equals(e.getStack().getName().getString()) && getSetting(2).asToggle().getChild(1).asToggle().state) {
//                WorldRenderUtils.drawText("\u00a76\"" + e.getStack().getName().getString() + "\"",
//                        e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                        (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.75f * scale),
//                        e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
//            }
//
//            WorldRenderUtils.drawText("\u00A79" + e.getStack().getCount() + "x " + e.getName().getString(),
//                    e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                    (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
//                    e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
//        } else if (event.getEntity() instanceof LivingEntity) {
//            LivingEntity e = (LivingEntity) event.getEntity();
//            if(e instanceof PlayerEntity && getSetting(0).asToggle().state) {
//                double scale = Math.max(getSetting(0).asToggle().getChild(0).asSlider().getValue() * (mc.cameraEntity.distanceTo(e) / 20), 1);
//
//                int ping = 0;
//                try {
//                    ping = Objects.requireNonNull(mc.player.networkHandler.getPlayerListEntry(e.getName().getString())).getLatency();
//                } catch (Exception ignored) { }
//                String aaa = "";
////                if(e.getName().getString().equals(Main.ClientInfo.clientCreators)) {
////                    aaa = Main.getOnlinePlayers().contains(e.getName().getString()) ? "\u00A78[\u00A7bPM DEV\u00A78]\u00A7f " : "";
////                } else {
////                    aaa = Main.getOnlinePlayers().contains(e.getName().getString()) ? "\u00A78[\u00A7bPM\u00A78]\u00A7f " : "";
////                }
//                if (getFriendManager().isFriend(e.getName().getString())) {
//                    WorldRenderUtils.drawText("\u00A7b" + aaa + e.getName().getString() + " " + getPingColor(ping) + ping + "ms " + getHealthColor(e) +
//                                    (int) (e.getHealth() + e.getAbsorptionAmount()),
//                            e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
//                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
//                } else {
//                    WorldRenderUtils.drawText("\u00A7c"+ aaa + e.getName().getString() + " " + getPingColor(ping) + ping + "ms " + getHealthColor(e) +
//                                    (int) (e.getHealth() + e.getAbsorptionAmount()),
//                            e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
//                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
//                }
//                double c = 0;
//                double lscale = scale * 0.4;
//                double up = ((0.3 + lines.size() * 0.25) * scale) + lscale / 2;
//
//                if (getSetting(3).asMode().mode == 0) {
//                    drawItem(rPos.x, rPos.y + up, rPos.z, -2.5, 0, lscale, livingEntity.getEquippedStack(EquipmentSlot.MAINHAND));
//                    drawItem(rPos.x, rPos.y + up, rPos.z, 2.5, 0, lscale, livingEntity.getEquippedStack(EquipmentSlot.OFFHAND));
//
//                    for (ItemStack i : livingEntity.getArmorItems()) {
//                        drawItem(rPos.x, rPos.y + up, rPos.z, c + 1.5, 0, lscale, i);
//                        c--;
//                    }
//                } else if (getSetting(0).asMode().mode == 1) {
//                    drawItem(rPos.x, rPos.y + up, rPos.z, -1.25, 0, lscale, livingEntity.getEquippedStack(EquipmentSlot.MAINHAND));
//                    drawItem(rPos.x, rPos.y + up, rPos.z, 1.25, 0, lscale, livingEntity.getEquippedStack(EquipmentSlot.OFFHAND));
//
//                    for (ItemStack i : livingEntity.getArmorItems()) {
//                        drawItem(rPos.x, rPos.y + up, rPos.z, 0, c, lscale, i);
//                        c++;
//                    }
//                }
//            } else if((e instanceof Monster || EntityUtil.isAnimal(e)) && getSetting(1).asToggle().state) {
//                double scale = Math.max(getSetting(1).asToggle().getChild(0).asSlider().getValue() * (mc.cameraEntity.distanceTo(e) / 20), 1);
//                WorldRenderUtils.drawText("\u00A79" + e.getName().getString() + " " + getHealthColor(e) + (int) (e.getHealth() + e.getAbsorptionAmount()),
//                        e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
//                        (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + (0.5f * scale),
//                        e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), scale);
//            }
//
//            event.setCancelled(true);
//        }
//    }

    public static String getPingColor(int ping) {
        if (ping < 100) {
            return "\u00a7a";
        } else if (ping < 150) {
            return "\u00a7c";
        } else {
            return "\u00a74";
        }
    }
    public static String getHealthColor(LivingEntity entity) {
        if (entity.getHealth() + entity.getAbsorptionAmount() >= entity.getMaxHealth() * 0.7) {
            return "\u00a7a";
        } else if (entity.getHealth() + entity.getAbsorptionAmount() >= entity.getMaxHealth() * 0.1) {
            return "\u00a7c";
        } else {
            return "\u00a74";
        }
    }

    /** Draws a 2D gui items somewhere in the world. **/
    public static void drawGuiItem(double x, double y, double z, double offX, double offY, double scale, ItemStack item) {
        if (item.isEmpty()) {
            return;
        }

        MatrixStack matrix = matrixFrom(x, y, z);

        Camera camera = mc.gameRenderer.getCamera();
        matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-camera.getYaw()));
        matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));

        matrix.translate(offX, offY, 0);
        matrix.scale((float) scale, (float) scale, 0.001f);

        matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f));

        mc.getBufferBuilders().getEntityVertexConsumers().draw();

        Vector3f[] currentLight = getCurrentLight();
        DiffuseLighting.disableGuiDepthLighting();

        GL11.glDepthFunc(GL11.GL_ALWAYS);
        mc.getItemRenderer().renderItem(item, ModelTransformation.Mode.GUI, 0xF000F0,
                OverlayTexture.DEFAULT_UV, matrix, mc.getBufferBuilders().getEntityVertexConsumers());

        mc.getBufferBuilders().getEntityVertexConsumers().draw();
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        RenderSystem.setupLevelDiffuseLighting(currentLight[0], currentLight[1], Matrix4f.translate(0f, 0f, 0f));
    }

    private void drawItem(double x, double y, double z, double offX, double offY, double scale, ItemStack item) {
        drawGuiItem(x, y, z, offX * scale, offY * scale, scale, item);

        if (!item.isEmpty()) {
            double w = mc.textRenderer.getWidth("x" + item.getCount()) / 52d;
//            WorldRenderUtils.drawText(new LiteralText("x" + item.getCount()),
//                    x, y, z, (offX - w) * scale, (offY - 0.07) * scale, scale * 1.75, false);
        }

        int c = 0;
        for (Map.Entry<Enchantment, Integer> m : EnchantmentHelper.get(item).entrySet()) {
            String text = I18n.translate(m.getKey().getName(2).getString());

            if (text.isEmpty())
                continue;

            text = WordUtils.capitalizeFully(text.replaceFirst("Curse of (.)", "C$1"));

            String subText = text.substring(0, Math.min(text.length(), 2)) + m.getValue();

//            WorldRenderUtils.drawText(new LiteralText(subText).styled(s-> s.withColor(TextColor.fromRgb(m.getKey().isCursed() ? 0xff5050 : 0xffb0e0))),
//                    x, y, z, offX * scale, (offY + 0.7 - c * 0.3) * scale, scale * 1.25, false);
            c--;
        }
    }

    private Vec3d getRenderPos(Entity e) {
        return new Vec3d(
                e.lastRenderX + (e.getX() - e.lastRenderX) * mc.getTickDelta(),
                (e.lastRenderY + (e.getY() - e.lastRenderY) * mc.getTickDelta()) + e.getHeight(),
                e.lastRenderZ + (e.getZ() - e.lastRenderZ) * mc.getTickDelta());
    }


    public static MatrixStack matrixFrom(double x, double y, double z) {
        MatrixStack matrix = new MatrixStack();

        Camera camera = mc.gameRenderer.getCamera();
        matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
        matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw() + 180.0F));

        matrix.translate(x - camera.getPos().x, y - camera.getPos().y, z - camera.getPos().z);

        return matrix;
    }

    public static Vector3f[] getCurrentLight() {
        float[] light1 = new float[4];
        float[] light2 = new float[4];
        GL11.glGetLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, light1);
        GL11.glGetLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, light2);

        return new Vector3f[] { new Vector3f(light1[0], light1[1], light1[2]), new Vector3f(light2[0], light2[1], light2[2]) };
    }
}
