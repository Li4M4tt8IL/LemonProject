package me.pm.lemon.module.modules.hud;

import me.pm.lemon.gui.hud.ScreenPosition;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Colors;
import me.pm.lemon.module.ModuleDraggable;
import me.pm.lemon.module.modules.gui.Hud;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class EntityPlayerList extends ModuleDraggable {
    public EntityPlayerList() {
        super("Entity Player List", Category.HUD, "test", GLFW.GLFW_KEY_UNKNOWN, Colors.HUD);
    }

    @Override
    public int getWidth() {
        ArrayList<Entity> players = new ArrayList<>();
        assert mc.world != null;
        for(Entity e : mc.world.getEntities()) {
            if(e instanceof PlayerEntity) {
                if(!players.contains(e)) players.add(e);
            }
        }
        int offsetX = mc.textRenderer.getWidth("Players:") / 4;
        players.sort((a, b) ->
                Integer.compare(mc.textRenderer.getWidth(Util.capitalizeFirstLetter(b.getName().getString())),
                        mc.textRenderer.getWidth(Util.capitalizeFirstLetter(a.getName().getString()))));
        return (MinecraftClient.getInstance().textRenderer.getWidth(players.get(0).getName().getString()) + offsetX) + MinecraftClient.getInstance().textRenderer.getWidth("-00150");
    }

    @Override
    public int getHeight() {
        ArrayList<Entity> players = new ArrayList<>();
        assert mc.world != null;
        for(Entity e : mc.world.getEntities()) {
            if(e instanceof PlayerEntity) {
                if(!players.contains(e)) players.add(e);
            }
        }
        return players.size() * 12;
    }

    @Override
    public void render(ScreenPosition pos) {
        MatrixStack matrixStack = new MatrixStack();
        if(getModule(Hud.class).isToggled() && getModule(Hud.class).getSetting(5).asToggle().state) {
            if(getModule(Hud.class).getSetting(5).asToggle().getChild(0).asToggle().state) {
                ArrayList<Entity> players = new ArrayList<>();
                assert mc.world != null;
                for(Entity e : mc.world.getEntities()) {
                    if(e instanceof PlayerEntity) {
                        if(!players.contains(e)) players.add(e);
                    }
                }

                mc.textRenderer.drawWithShadow(matrixStack, "Players:", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, ColorUtils.textColor());
                int offset = 0;
                offset = offset + 10;
                for(Entity e : players) {
                    int offsetX = mc.textRenderer.getWidth("Players:") / 4;
                    mc.textRenderer.drawWithShadow(matrixStack, "\247f- " + e.getName().getString() + " \247a" + (int) mc.player.distanceTo(e),
                            pos.getAbsoluteX() + 1 + offsetX, pos.getAbsoluteY() + 1 + offset, 0);
                    offset = offset + 10;
                }
            }
        }
    }
}
