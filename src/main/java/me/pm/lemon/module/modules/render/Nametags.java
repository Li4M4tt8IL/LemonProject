package me.pm.lemon.module.modules.render;

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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.awt.*;
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
                    renderNametagItem(e.getEquippedStack(EquipmentSlot.MAINHAND));
                    renderNametagItem(e.getEquippedStack(EquipmentSlot.OFFHAND));
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
                        renderNametagItem(i);
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

    private void renderNametagItem(ItemStack stack) {
        TextRenderer text = mc.textRenderer;
        NametagUtils.begin(pos);

        String name = stack.getName().getString();
        String count = " x" + stack.getCount();

        double nameWidth = text.getWidth(name);
        double countWidth = text.getWidth(count);
        double heightDown = 12;

        double width = nameWidth;
        width += countWidth;
        double widthHalf = width / 2;

        double hX = -widthHalf;
        double hY = -heightDown;

        hX = text.draw(new MatrixStack(), name, (int) hX, (int) hY, WHITE.getRGB());
        text.draw(new MatrixStack(), count, (int) hX, (int) hY, GOLD.getRGB());

        NametagUtils.end();
    }
}
