package me.pm.lemon.module.modules.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import me.pm.lemon.Main;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.DrawOverlayEvent;
import me.pm.lemon.gui.testScreen.settings.SettingMode;
import me.pm.lemon.gui.testScreen.settings.SettingSlider;
import me.pm.lemon.gui.testScreen.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;
import me.pm.lemon.utils.generalUtils.FabricReflect;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

public class UI extends Module {
    public UI(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Module List", true).withDesc("Shows the toggled modules list").withChildren(
                        new SettingToggle("Rainbow", true).withDesc("RaInBoW cOlOrS").withChildren(
                                new SettingSlider("Saturation", 0.01, 1, 0.5f, 2),
                                new SettingSlider("Brightness", 0.01, 1, 0.5f, 2),
                                new SettingSlider("Speed", 1, 25, 10, 0)
                        ),
                        new SettingToggle("Background", true).withDesc("Draws a gray-ich background to the list"),
                        new SettingToggle("Separator", true).withDesc("Draws a separator"),
                        new SettingMode("Sorting", "Up-Down", "Down-Up", "Category")
                ),
                new SettingToggle("Info", true).withDesc("Shows the mc info").withChildren(
                        new SettingToggle("Client Name", true),
                        new SettingToggle("Version", true),
                        new SettingToggle("Mc Version", true),
                        new SettingToggle("Name", true),
                        new SettingToggle("FPS", true),
                        new SettingToggle("IP", true),
                        new SettingToggle("Ping", true)
                ),
                new SettingToggle("World Info", true).withDesc("Shows world info").withChildren(
                        new SettingToggle("Coordinates", true),
                        new SettingToggle("Facing", true)
                ),
                new SettingToggle("Armor", true).withDesc("Shows your armor"),
                new SettingToggle("Entity list", true).withDesc("Draws a entity list").withChildren(
                        new SettingToggle("Players", true)
                ));
    }

    private static String name;

    @EventTarget
    public void renderUI(DrawOverlayEvent event) {
        MatrixStack matrixStack = event.matrix;
        if (!mc.options.debugEnabled && !Main.isPanic && Module.getModule(Hud.class).isToggled()) {
            GlStateManager.enableBlend();

            int scaledWidth = mc.getWindow().getScaledWidth();
            int scaledHeight = mc.getWindow().getScaledHeight();

            int x = (int) mc.player.getX();
            int y = (int) mc.player.getY();
            int z = (int) mc.player.getZ();

            int yCount = 2;
            int right = scaledWidth - 2;
            int left = scaledWidth - right;
            int bottom = scaledHeight - 14;
            int top = scaledHeight - bottom - 12;

            int infoPosx = left;
            int infoPosy = top;

            //00F3FF

            List<String> moduleList = new ArrayList<>();
            for (Module mod : ModuleManager.modules) {
                if (mod.isToggled() && !mod.isCategory(Category.GUI)) {
                    name = mod.getDisplayName();
                    if (name == null) {
                        name = mod.getName();
                    }
                    moduleList.add(name);
                }
            }
            if (getSetting(0).asToggle().state) {
                if (getSetting(0).asToggle().getChild(3).asMode().mode == 0) {
                    moduleList.sort((mod1, mod2) ->
                            Integer.compare(mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod2)), mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod1))));
                }
                if (getSetting(0).asToggle().getChild(3).asMode().mode == 1) {
                    moduleList.sort(Comparator.comparingInt(mod -> mc.textRenderer.getWidth(Util.capitalizeFirstLetter(mod))));
                }
                for (String s : moduleList) {
                    boolean rainbow = getSetting(0).asToggle().getChild(0).asToggle().state;
                    Color moduleColor = Objects.requireNonNull(Module.getModuleByDisplayName(s)).getColor();
                    if (getSetting(0).asToggle().getChild(1).asToggle().state) {
                        RenderUtils.drawRect(right + 5, yCount + 9, right - mc.textRenderer.getWidth(s) - 3, yCount - 1, new Color(0, 0, 0, 100).getRGB());
                    }
                    if (getSetting(0).asToggle().getChild(2).asToggle().state) {
                        RenderUtils.drawRect(right - mc.textRenderer.getWidth(s) - 2, yCount + 9, right - mc.textRenderer.getWidth(s) - 3, yCount - 1, rainbow ? Util.getRainbow((float) getSetting(0).asToggle().getChild(0).asToggle().getChild(0).asSlider().getValue(),
                                (float) getSetting(0).asToggle().getChild(0).asToggle().getChild(1).asSlider().getValue(),
                                getSetting(0).asToggle().getChild(0).asToggle().getChild(2).asSlider().getValue(), 0) : moduleColor.getRGB());
                    }
                    mc.textRenderer.drawWithShadow(matrixStack, s, right - mc.textRenderer.getWidth(s), yCount, rainbow ? Util.getRainbow((float) getSetting(0).asToggle().getChild(0).asToggle().getChild(0).asSlider().getValue(),
                            (float) getSetting(0).asToggle().getChild(0).asToggle().getChild(1).asSlider().getValue(),
                            getSetting(0).asToggle().getChild(0).asToggle().getChild(2).asSlider().getValue(), 0) : moduleColor.getRGB());
                    yCount += 10;
                }
            }
            if (getSetting(1).asToggle().state) {
                int fps = (int) FabricReflect.getFieldValue(MinecraftClient.getInstance(), "field_1738", "currentFps");
                int offset = 0;


                if (getSetting(1).asToggle().getChild(0).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bClient name:\247f " + Main.ClientInfo.clientName, infoPosx, infoPosy, 0);
                    offset = offset + 10;
                }
                if (getSetting(1).asToggle().getChild(1).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bVersion:\247f " + Main.ClientInfo.clientVersion, infoPosx, infoPosy + offset, 0);
                    offset = offset + 10;
                }
                if (getSetting(1).asToggle().getChild(2).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bMC version: \247f" + Main.ClientInfo.minecraftVersion, infoPosx, infoPosy + offset, 0);
                    offset = offset + 10;
                }
                if (getSetting(1).asToggle().getChild(3).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bName: \247f" + mc.player.getName().getString(), infoPosx, infoPosy + offset, 0);
                    offset = offset + 10;
                }
                if (getSetting(1).asToggle().getChild(4).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bFPS: \247f" + fps, infoPosx, infoPosy + offset, 0);
                    offset = offset + 10;
                }
                if (getSetting(1).asToggle().getChild(5).asToggle().state) {
                    String server = mc.getCurrentServerEntry() == null ? "Singleplayer" : mc.getCurrentServerEntry().address;
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bIP: \247f" + server, infoPosx, infoPosy + offset, 0);
                    offset = offset + 10;
                }
                if (getSetting(1).asToggle().getChild(6).asToggle().state) {
                    PlayerListEntry playerEntry = mc.player.networkHandler.getPlayerListEntry(mc.player.getGameProfile().getId());
                    int ping = playerEntry == null ? 0 : playerEntry.getLatency();
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bPing: \247f" + ping, infoPosx, infoPosy + offset, 0);
                }
            }
            if (getSetting(2).asToggle().state) {
                int offset = 0;
                String facing = "default";
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("NORTH")) {
                    facing = "(-Z)";
                }
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("SOUTH")) {
                    facing = "(+Z)";
                }
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("WEST")) {
                    facing = "(-X)";
                }
                if (mc.player.getHorizontalFacing().toString().toUpperCase().equalsIgnoreCase("EAST")) {
                    facing = "(+X)";
                }

                if (getSetting(2).asToggle().getChild(0).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bCoordinates:\247f " + x + " " + y + " " + z + " ", right - mc.textRenderer.getWidth("Coordinates: " + x + " " + y + " " + z + " "), bottom, 0);
                    offset = offset + 10;
                }
                if (getSetting(2).asToggle().getChild(1).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bFacing:\247f " + mc.player.getHorizontalFacing().toString().toUpperCase() + " " + facing, right - mc.textRenderer.getWidth("Facing: " + mc.player.getHorizontalFacing().toString().toUpperCase() + " " + facing), bottom - offset, 0);
                }
            }
            if (getSetting(3).asToggle().state) {
                GL11.glPushMatrix();

                int count = 0;
                int x1 = mc.getWindow().getScaledWidth() / 2;
                int y2 = mc.getWindow().getScaledHeight() -
                        (mc.player.isSubmergedInWater() || mc.player.getAir() < mc.player.getMaxAir() ? 64 : 55);
                for (ItemStack is : mc.player.inventory.armor) {
                    count++;
                    if (is.isEmpty()) continue;
                    int x2 = x1 - 90 + (9 - count) * 20 + 2;

                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    mc.getItemRenderer().zOffset = 200F;
                    mc.getItemRenderer().renderGuiItemIcon(is, x2, y2);

                    mc.getItemRenderer().renderGuiItemOverlay(mc.textRenderer, is, x2, y2);

                    mc.getItemRenderer().zOffset = 0F;
                    GL11.glDisable(GL11.GL_DEPTH_TEST);


                }

                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glPopMatrix();
            }
            if(getSetting(4).asToggle().state) {
                int offset = 80;
                ArrayList<Entity> players = new ArrayList<>();
                assert mc.world != null;
                for(Entity e : mc.world.getEntities()) {
                    if(e instanceof PlayerEntity) {
                        if(!players.contains(e)) players.add(e);
                    }
                }
                if(getSetting(4).asToggle().getChild(0).asToggle().state) {
                    mc.textRenderer.drawWithShadow(matrixStack, "\247bPlayers:", infoPosx,infoPosy + offset, 0);
                    offset = offset + 10;
                    for(Entity e : players) {
                        int offsetX = mc.textRenderer.getWidth("Players:") / 4;
                        mc.textRenderer.drawWithShadow(matrixStack, "\247f- " + e.getName().getString() + " \247a" + (int) mc.player.distanceTo(e), infoPosx+offsetX, infoPosy+offset, 0);
                        offset = offset + 10;
                    }
                }
            }
        }
    }
}