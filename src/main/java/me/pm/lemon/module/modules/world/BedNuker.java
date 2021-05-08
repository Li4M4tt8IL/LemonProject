package me.pm.lemon.module.modules.world;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.SettingColor;
import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.gui.clickGui.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.block.Block;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.awt.*;

public class BedNuker extends Module {
    public BedNuker(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color, new SettingSlider("Range", 1, 5, 4, 0),
                new SettingToggle("Bed Highlight", true).withChildren(
                        new SettingColor("Color", 0f, 1.0f, 1.0f, false)
                ));
    }

    @EventTarget
    public void onTick(TickEvent event) {
        int radius = (int) getSetting(0).asSlider().getValue();
        int xPos;
        int yPos;
        int zPos;
        for(int x = -radius; x < radius; x++) {
            for(int y = -radius; y < radius; y++) {
                for(int z = -radius; z < radius; z++) {
                    assert mc.player != null;
                    xPos = (int)mc.player.getX() + x;
                    yPos = (int)mc.player.getY() + y;
                    zPos = (int)mc.player.getZ() + z;

                    BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
                    assert mc.world != null;
                    Block block = mc.world.getBlockState(blockPos).getBlock();

                    if (Util.isBed(block)) {
                        assert mc.interactionManager != null;
                        mc.interactionManager.updateBlockBreakingProgress(blockPos, Direction.NORTH);

                        mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        }
    }
}
