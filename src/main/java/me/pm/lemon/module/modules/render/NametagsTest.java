package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.DrawOverlayEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.friends.FriendManager;
import me.pm.lemon.gui.clickGui.settings.Setting;
import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.gui.clickGui.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.player.Freecam;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import me.pm.lemon.utils.generalUtils.NametagUtils;
import me.pm.lemon.utils.generalUtils.Util;
import me.pm.lemon.utils.generalUtils.Vec3;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameMode;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class NametagsTest extends Module {

    public NametagsTest(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Players", true),
                new SettingToggle("Mobs", false),
                new SettingToggle("Animals", false),
                new SettingToggle("Items", true),
                new SettingSlider("Scale", 0.1, 5, 1.5, 1),
                new SettingToggle("Show Ping", true),
                new SettingToggle("Show Distance", true),
                new SettingToggle("Display Items", true),
                new SettingToggle("Show Enchants", true));
    }

    public enum Position {
        Above,
        OnTop
    }

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
    private final List<Entity> entityList = new ArrayList<>();

    @EventTarget
    private void onTick(TickEvent event) {
        entityList.clear();

        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();

        mc.world.getEntities().forEach(entity -> {
            if((entity instanceof ItemEntity) && getSetting(3).asToggle().state) {
                entityList.add(entity);
            } else if((entity instanceof PlayerEntity) && getSetting(0).asToggle().state) {
                entityList.add(entity);
            } else if((entity instanceof Monster) && getSetting(1).asToggle().state) {
                entityList.add(entity);
            } else if((EntityUtil.isAnimal(entity)) && getSetting(2).asToggle().state) {
                entityList.add(entity);
            }
        });

        entityList.sort(Comparator.comparing(e -> e.squaredDistanceTo(cameraPos)));
    }

    @EventTarget
    private void onRender2D(DrawOverlayEvent event) {
        int count = getRenderCount();

        for (int i = count - 1; i > -1; i--) {
            Entity entity = entityList.get(i);

            pos.set(entity, 0);
            pos.add(0, getHeight(entity), 0);

            EntityType<?> type = entity.getType();

            if (NametagUtils.to2D(pos, getSetting(4).asSlider().getValue())) {
                if (type == EntityType.PLAYER) renderNametagPlayer((PlayerEntity) entity);
                else if (type == EntityType.ITEM) renderNametagItem(((ItemEntity) entity).getStack());
                else if (type == EntityType.ITEM_FRAME) renderNametagItem(((ItemFrameEntity) entity).getHeldItemStack());
                else if (type == EntityType.TNT) renderTntNametag((TntEntity) entity);
                else if (entity instanceof LivingEntity) renderGenericNametag((LivingEntity) entity);
            }
        }
    }

    private int getRenderCount() {
        int count = entityList.size();
        count = MathHelper.clamp(count, 0, entityList.size());

        return count;
    }

    private double getHeight(Entity entity) {
        double height = entity.getEyeHeight(entity.getPose());

        if (entity.getType() == EntityType.ITEM || entity.getType() == EntityType.ITEM_FRAME) height += 0.2;
        else height += 0.5;

        return height;
    }

    private void renderNametagPlayer(PlayerEntity player) {
        TextRenderer text = mc.textRenderer;
        NametagUtils.begin(pos);

        // Using Meteor
        String usingMeteor = "";

        // Gamemode
        GameMode gm = EntityUtil.getGameMode(player);
        String gmText = "BOT";
        if (gm != null) {
            switch (gm) {
                case SPECTATOR: gmText = "Sp"; break;
                case SURVIVAL:  gmText = "S"; break;
                case CREATIVE:  gmText = "C"; break;
                case ADVENTURE: gmText = "A"; break;
            }
        }

        gmText = "[" + gmText + "] ";

        // Name
        String name;
        Color nameColor = Module.getFriendManager().isFriend(player.getName().getString()) ? Color.CYAN : Color.WHITE;

        name = player.getEntityName();

        name = name + " ";

        // Health
        float absorption = player.getAbsorptionAmount();
        int health = Math.round(player.getHealth() + absorption);
        double healthPercentage = health / (player.getMaxHealth() + absorption);

        String healthText = String.valueOf(health);
        Color healthColor;

        if (healthPercentage <= 0.333) healthColor = RED;
        else if (healthPercentage <= 0.666) healthColor = AMBER;
        else healthColor = GREEN;

        // Ping
        int ping = EntityUtil.getPing(player);
        String pingText = " [" + ping + "ms]";

        // Distance
        double dist = Math.round(EntityUtil.distanceToCamera(player) * 10.0) / 10.0;
        String distText = " " + dist + "m";

        // Calc widths
        double usingMeteorWidth = text.getWidth(usingMeteor);
        double gmWidth = text.getWidth(gmText);
        double nameWidth = text.getWidth(name);
        double healthWidth = text.getWidth(healthText);
        double pingWidth = text.getWidth(pingText);
        double distWidth = text.getWidth(distText);
        double width = nameWidth + healthWidth;

        if (getSetting(5).asToggle().state) width += pingWidth;
        if (getSetting(6).asToggle().state) width += distWidth;

        double widthHalf = width / 2;
        double heightDown = 12;

        drawBg(-widthHalf, -heightDown, width, heightDown);

        // Render texts

        double hX = -widthHalf;
        double hY = -heightDown;

        MatrixStack matrix = new MatrixStack();

        hX = text.draw(matrix, name, (int) hX, (int) hY, nameColor.getRGB());

        hX = text.draw(matrix, healthText, (int) hX, (int) hY, nameColor.getRGB());
        if (getSetting(5).asToggle().state) hX = text.draw(matrix ,pingText, (int) hX, (int) hY, BLUE.getRGB());
        if (getSetting(6).asToggle().state) text.draw(matrix, distText, (int) hX, (int) hY, GREY.getRGB());


//        if (getSetting(7).asToggle().state) {
//            // Item calc
//            Arrays.fill(itemWidths, 0);
//            boolean hasItems = false;
//            int maxEnchantCount = 0;
//
//            for (int i = 0; i < 6; i++) {
//                ItemStack itemStack = getItem(player, i);
//
//                // Setting up widths
//                if (itemWidths[i] == 0 && (!true || !itemStack.isEmpty())) itemWidths[i] = 32 + 2;
//
//                if (!itemStack.isEmpty()) hasItems = true;
//
//                if (getSetting(8).asToggle().state) {
//                    Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
//                    enchantmentsToShowScale.clear();
//
//                    for (Enchantment enchantment : setDefaultList()) {
//                        if (enchantments.containsKey(enchantment)) {
//                            enchantmentsToShowScale.put(enchantment, enchantments.get(enchantment));
//                        }
//                    }
//
//                    for (Enchantment enchantment : enchantmentsToShowScale.keySet()) {
//                        String enchantName = Util.getEnchantSimpleName(enchantment, 3) + " " + enchantmentsToShowScale.get(enchantment);
//                        itemWidths[i] = Math.max(itemWidths[i], (text.getWidth(enchantName) / 2));
//                    }
//
//                    maxEnchantCount = Math.max(maxEnchantCount, enchantmentsToShowScale.size());
//                }
//            }
//
//            double itemsHeight = (hasItems ? 32 : 0);
//            double itemWidthTotal = 0;
//            for (double w : itemWidths) itemWidthTotal += w;
//            double itemWidthHalf = itemWidthTotal / 2;
//
//            double y = -heightDown - 7 - itemsHeight;
//            double x = -itemWidthHalf;
//
//            //Rendering items and enchants
//            for (int i = 0; i < 6; i++) {
//                ItemStack stack = getItem(player, i);
//
//                glPushMatrix();
//                glScaled(2, 2, 1);
//
//                mc.getItemRenderer().renderGuiItemIcon(stack, (int) (x / 2), (int) (y / 2));
//                mc.getItemRenderer().renderGuiItemOverlay(mc.textRenderer, stack, (int) (x / 2), (int) (y / 2));
//
//                glPopMatrix();
//
//                if (maxEnchantCount > 0 && getSetting(8).asToggle().state) {
//
//                    Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
//                    Map<Enchantment, Integer> enchantmentsToShow = new HashMap<>();
//                    for (Enchantment enchantment : setDefaultList()) {
//                        if (enchantments.containsKey(enchantment)) {
//                            enchantmentsToShow.put(enchantment, enchantments.get(enchantment));
//                        }
//                    }
//
//                    double aW = itemWidths[i];
//                    double enchantY = 0;
//
//                    double addY = 0;
//                    addY = -((enchantmentsToShow.size() + 1) * 12); break;
//
//                    double enchantX = x;
//
//                    for (Enchantment enchantment : enchantmentsToShow.keySet()) {
//                        String enchantName = Util.getEnchantSimpleName(enchantment, 3) + " " + enchantmentsToShow.get(enchantment);
//
//                        Color enchantColor = WHITE;
//                        if (enchantment.isCursed()) enchantColor = RED;
//                            enchantX = x + (aW / 2) - (text.getWidth(enchantName) / 2); break;
//
//                        text.draw(new MatrixStack(), enchantName, (int) enchantX, (float) ((int) y + addY + enchantY), enchantColor.getRGB());
//
//                        enchantY += 12;
//                    }
//
//                    text.end();
//                }
//
//                x += itemWidths[i];
//            }
//        } else if (displayItemEnchants.get()) displayItemEnchants.set(false);

        NametagUtils.end();
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

        drawBg(-widthHalf, -heightDown, width, heightDown);


        double hX = -widthHalf;
        double hY = -heightDown;

        hX = text.draw(new MatrixStack(), name, (int) hX, (int) hY, WHITE.getRGB());
        text.draw(new MatrixStack(), count, (int) hX, (int) hY, GOLD.getRGB());

        NametagUtils.end();
    }

    private void renderGenericNametag(LivingEntity entity) {
        TextRenderer text = mc.textRenderer;
        NametagUtils.begin(pos);

        //Name
        String nameText = entity.getType().getName().getString();
        nameText += " ";

        //Health
        float absorption = entity.getAbsorptionAmount();
        int health = Math.round(entity.getHealth() + absorption);
        double healthPercentage = health / (entity.getMaxHealth() + absorption);

        String healthText = String.valueOf(health);
        Color healthColor;

        if (healthPercentage <= 0.333) healthColor = RED;
        else if (healthPercentage <= 0.666) healthColor = AMBER;
        else healthColor = GREEN;

        double nameWidth = text.getWidth(nameText);
        double healthWidth = text.getWidth(healthText);
        double heightDown = 12;

        double width = nameWidth + healthWidth;
        double widthHalf = width / 2;

        drawBg(-widthHalf, -heightDown, width, heightDown);

        double hX = -widthHalf;
        double hY = -heightDown;

        hX = text.draw(new MatrixStack(), nameText, (int) hX, (int) hY, WHITE.getRGB());
        text.draw(new MatrixStack(), healthText, (int) hX, (int) hY, healthColor.getRGB());

        NametagUtils.end();
    }

    private void renderTntNametag(TntEntity entity) {
        TextRenderer text = mc.textRenderer;
        NametagUtils.begin(pos);

        String fuseText = ticksToTime(entity.getFuse());

        double width = text.getWidth(fuseText);
        double heightDown = 12;

        double widthHalf = width / 2;

        drawBg(-widthHalf, -heightDown, width, heightDown);

        double hX = -widthHalf;
        double hY = -heightDown;

        text.draw(new MatrixStack(), fuseText, (int) hX, (int) hY, WHITE.getRGB());

        NametagUtils.end();
    }

    private List<Enchantment> setDefaultList(){
        List<Enchantment> ench = new ArrayList<>();
        for (Enchantment enchantment : Registry.ENCHANTMENT) {
            ench.add(enchantment);
        }
        return ench;
    }

    private ItemStack getItem(PlayerEntity entity, int index) {
        if(index == 0) return entity.getMainHandStack();
        if(index == 1) return entity.inventory.armor.get(3);
        if(index == 2) return entity.inventory.armor.get(2);
        if(index == 3) return entity.inventory.armor.get(1);
        if(index == 4) return entity.inventory.armor.get(0);
        if(index == 5) return entity.getOffHandStack();
        return ItemStack.EMPTY;
    }

    private void drawBg(double x, double y, double width, double height) {
//        Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
//        Renderer.NORMAL.quad(x - 1, y - 1, width + 2, height + 2, background.get());
//        Renderer.NORMAL.end();
    }

    private static String ticksToTime(int ticks){
        if (ticks > 20 * 3600) {
            int h = ticks / 20 / 3600;
            return h + " h";
        } else if (ticks > 20 * 60) {
            int m = ticks / 20 / 60;
            return m + " m";
        } else {
            int s = ticks / 20;
            int ms = (ticks % 20) / 2;
            return s + "."  +ms + " s";
        }
    }
}