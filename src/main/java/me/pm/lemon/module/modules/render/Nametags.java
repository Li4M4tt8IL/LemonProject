package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.EntityRenderEvent;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import me.pm.lemon.utils.generalUtils.WorldRenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Objects;

public class Nametags extends Module {
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
                    WorldRenderUtils.drawItem(matrixStack,e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + ((0.75) * scale),
                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), -2.5, 0, scale, e.getEquippedStack(EquipmentSlot.MAINHAND));
                    WorldRenderUtils.drawItem(matrixStack,e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                            (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + ((0.75) * scale),
                            e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), 2.5, 0, scale, e.getEquippedStack(EquipmentSlot.OFFHAND));
                    for (ItemStack i: ((PlayerEntity) e).inventory.armor) {
                        WorldRenderUtils.drawItem(matrixStack,e.prevX + (e.getX() - e.prevX) * mc.getTickDelta(),
                                (e.prevY + (e.getY() - e.prevY) * mc.getTickDelta()) + e.getHeight() + ((0.75) * scale),
                                e.prevZ + (e.getZ() - e.prevZ) * mc.getTickDelta(), c+1.5, 0, scale, i);
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
}
