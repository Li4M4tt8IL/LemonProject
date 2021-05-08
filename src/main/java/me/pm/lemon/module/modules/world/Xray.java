package me.pm.lemon.module.modules.world;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.BlockRenderEvent;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.FileHelper;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Xray extends Module {
    public Xray(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color, new SettingToggle("Anti Xray Bypass", false).withDesc("Not very efficient!"));
    }

    public static Set<Block> visibleBlocks = new HashSet<>();
    private double gamma;

    public boolean isVisible(Block block) {
        return !this.isToggled() || visibleBlocks.contains(block);
    }

    public static void setVisible(Block... blocks) {
        Collections.addAll(visibleBlocks, blocks);
    }

    public void setInvisible(Block... blocks) {
        visibleBlocks.removeAll(Arrays.asList(blocks));
    }

    public Set<Block> getVisibleBlocks() {
        return visibleBlocks;
    }

    @Override
    public void onEnable() {
        if (mc.world == null) return;
        visibleBlocks.clear();

        for (String s : FileHelper.readFileLines("xray.txt")) {
            setVisible(Registry.BLOCK.get(new Identifier(s)));
        }

        mc.worldRenderer.reload();

        gamma = mc.options.gamma;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.world != null) mc.worldRenderer.setWorld(mc.world);

        mc.options.gamma = gamma;

        mc.worldRenderer.reload();
        super.onDisable();
    }

    @EventTarget
    public void blockRender(BlockRenderEvent eventBlockRender) {
        if (this.isVisible(eventBlockRender.getBlockState().getBlock())) {
            eventBlockRender.setCancelled(true);
        }
    }

    @EventTarget
    public void onTick(TickEvent eventPreUpdate) {
        mc.options.gamma = 69.420;
    }
}
