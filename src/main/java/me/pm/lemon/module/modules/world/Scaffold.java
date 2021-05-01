package me.pm.lemon.module.modules.world;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.event.events.WorldRenderEvent;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Scaffold extends Module {

    private final Set<BlockPos> renderBlocks = new LinkedHashSet<>();

    public Scaffold(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Range", 0, 1, 0.3, 1),
                new SettingMode("Mode", "Normal", "3x3", "5x5", "7x7"),
                new SettingRotate(false).withDesc("Rotates when placing blocks"),
                new SettingToggle("Tower", true).withDesc("Makes scaffolding straight up much easier"),
                new SettingToggle("SafeWalk", true).withDesc("Prevents you from walking of edges when scaffold is on"),
                new SettingToggle("Highlight", false).withDesc("Highlights the blocks you are placing").withChildren(
                        new SettingColor("Color", 0.3f, 0.2f, 1f, false).withDesc("Color for the block highlight"),
                        new SettingToggle("Placed", false).withDesc("Highlights blocks that are already placed")),
                new SettingSlider("BPT", 1, 10, 2, 0).withDesc("Blocks Per Tick, how many blocks to place per tick"),
                new SettingSlider("Down", 0, 3, 0, 0));
    }


    @EventTarget
    public void onTick(TickEvent event) {
        renderBlocks.clear();

        int slot = -1;
        int prevSlot = mc.player.inventory.selectedSlot;

        if (mc.player.inventory.getMainHandStack().getItem() instanceof BlockItem) {
            slot = mc.player.inventory.selectedSlot;
        } else for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStack(i).getItem() instanceof BlockItem) {
                slot = i;
                break;
            }
        }

        if (slot == -1) return;

        double range = getSetting(0).asSlider().getValue();
        int mode = getSetting(1).asMode().mode;

        Vec3d placeVec = mc.player.getPos().add(0, -0.85-getSetting(7).asSlider().getValue(), 0);
        Set<BlockPos> blocks = (mode == 0
                ? new LinkedHashSet<>(Arrays.asList(new BlockPos(placeVec), new BlockPos(placeVec.add(range, 0, 0)), new BlockPos(placeVec.add(-range, 0, 0)),
                new BlockPos(placeVec.add(0, 0, range)), new BlockPos(placeVec.add(0, 0, -range))))
                : getSpiral(mode, new BlockPos(placeVec)));

        // Don't bother doing anything if there aren't any blocks to place on
        boolean empty = true;
        for (BlockPos bp : blocks) {
            if (Util.canPlaceBlock(bp)) {
                empty = false;
                break;
            }
        }

        if (empty) return;

        if (getSetting(3).asToggle().state
                && Util.NONSOLID_BLOCKS.contains(mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock())
                && !Util.NONSOLID_BLOCKS.contains(mc.world.getBlockState(mc.player.getBlockPos().down(2)).getBlock())) {
            double toBlock = (int) mc.player.getY() - mc.player.getY();

            if (toBlock < 0.05 && InputUtil.isKeyPressed(
                    mc.getWindow().getHandle(), InputUtil.fromTranslationKey(mc.options.keyJump.getBoundKeyTranslationKey()).getCode())) {
                mc.player.setVelocity(mc.player.getVelocity().x, -toBlock, mc.player.getVelocity().z);
                mc.player.jump();
            }
        }

        if (getSetting(5).asToggle().state) {
            for (BlockPos bp : blocks) {
                if (getSetting(5).asToggle().getChild(1).asToggle().state || Util.isBlockEmpty(bp)) {
                    renderBlocks.add(bp);
                }
            }
        }

        int cap = 0;
        for (BlockPos bp : blocks) {
            mc.player.inventory.selectedSlot = slot;
            if (getSetting(2).asRotate().state) {
                Util.facePosAuto(bp.getX() + 0.5, bp.getY() + 0.5, bp.getZ() + 0.5, getSetting(2).asRotate());
            }

            if (
                    Util.placeBlock(bp, -1, false, false)
            ) {
                cap++;
                if (cap >= (int) getSetting(6).asSlider().getValue()) return;
            }
        }
        mc.player.inventory.selectedSlot = prevSlot;

    }

    @EventTarget
    public void onWorldRender(WorldRenderEvent event) {
        if (getSetting(5).asToggle().state) {
            float[] col = getSetting(5).asToggle().getChild(0).asColor().getRGBFloat();
            for (BlockPos bp : renderBlocks) {
                RenderUtils.drawOutlineBox(bp, col[0], col[1], col[2], 1f);
                RenderUtils.drawFilledBox(bp, col[0], col[1], col[2], 0.7f);

                col[0] = Math.max(0f, col[0] - 0.01f);
                col[2] = Math.min(1f, col[2] + 0.01f);
            }
        }
    }

    private Set<BlockPos> getSpiral(int size, BlockPos center) {
        Set<BlockPos> set = new LinkedHashSet<>(Arrays.asList(center));

        if (size == 0) return set;

        int step = 1;
        int neededSteps = size * 4;
        BlockPos currentPos = center;
        for (int i = 0; i <= neededSteps; i++) {
            // Do 1 less step on the last side to not overshoot the spiral
            if (i == neededSteps) step--;

            for (int j = 0; j < step; j++) {
                if (i % 4 == 0) currentPos = currentPos.add(-1, 0, 0);
                else if (i % 4 == 1) currentPos = currentPos.add(0, 0, -1);
                else if (i % 4 == 2) currentPos = currentPos.add(1, 0, 0);
                else currentPos = currentPos.add(0, 0, 1);

                set.add(currentPos);
            }

            if (i % 2 != 0) step++;
        }

        return set;
    }
}
