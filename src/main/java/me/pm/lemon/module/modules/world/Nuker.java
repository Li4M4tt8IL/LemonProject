package me.pm.lemon.module.modules.world;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.testScreen.settings.SettingMode;
import me.pm.lemon.gui.testScreen.settings.SettingSlider;
import me.pm.lemon.gui.testScreen.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.WorldUtils;
import me.pm.lemon.utils.generalUtils.XRayUtil;
import net.minecraft.block.Blocks;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Nuker extends Module {
    public Nuker(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingMode("Mode", "Normal", "Multi", "Instant"), /* 0 */
                new SettingSlider("Range", 1, 4, 4, 0), /* 1 */
                new SettingToggle("All blocks", false), /* 2 */
                new SettingToggle("Flatten", true), /* 3 */
                new SettingToggle("Auto Rotate", false), /* 4 */
                new SettingToggle("No Particles", true), /* 5 */
                new SettingMode("Sort", "Normal", "Hardness"), /* 6 */
                new SettingSlider("Multi", 1, 10, 2, 0)); /* 7 */
    }

    @EventTarget
    public void onTick(TickEvent event) {
        double range = getSetting(1).asSlider().getValue();
        List<BlockPos> blocks = new ArrayList<>();

        /* Add blocks around player */
        for (int x = (int) range; x >= (int) -range; x--) {
            for (int y = (int) range; y >= (getSetting(3).asToggle().state ? 0 : (int) -range); y--) {
                for (int z = (int) range; z >= (int) -range; z--) {
                    BlockPos pos = new BlockPos(mc.player.getPos().add(x, y + 0.1, z));
                    if (!canSeeBlock(pos) || mc.world.getBlockState(pos).getBlock() == Blocks.AIR || WorldUtils.isFluid(pos))
                        continue;
                    if (!mc.player.abilities.creativeMode && mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK) {
                        return;
                    }
                    blocks.add(pos);
                }
            }
        }

        if (blocks.isEmpty()) return;

        if (getSetting(6).asMode().mode == 1) blocks.sort((a, b) -> Float.compare(
                mc.world.getBlockState(a).getHardness(null, a), mc.world.getBlockState(b).getHardness(null, b)));

        /* Move the block under the player to last so it doesn't mine itself down without clearing everything above first */
        if (blocks.contains(mc.player.getBlockPos().down())) {
            blocks.remove(mc.player.getBlockPos().down());
            blocks.add(mc.player.getBlockPos().down());
        }

        Vec3d eyePos = mc.player.getPos().add(0, mc.player.getEyeHeight(mc.player.getPose()), 0);

        int broken = 0;
        for (BlockPos pos : blocks) {
            if (!getSetting(2).asToggle().state && !XRayUtil.xrayBlocks.contains(mc.world.getBlockState(pos).getBlock()))
                continue;

            Vec3d vec = Vec3d.of(pos).add(0.5, 0.5, 0.5);

            if (eyePos.distanceTo(vec) > range + 0.5) continue;

            Direction dir = null;
            double dist = Double.MAX_VALUE;
            for (Direction d : Direction.values()) {
                double dist2 = eyePos.distanceTo(Vec3d.of(pos.offset(d)).add(0.5, 0.5, 0.5));
                if (dist2 > range || WorldUtils.NONSOLID_BLOCKS.contains(mc.world.getBlockState(pos.offset(d)).getBlock()) || dist2 > dist)
                    continue;
                dist = dist2;
                dir = d;
            }

            if (dir == null) continue;

            if (getSetting(4).asToggle().state) {
                WorldUtils.facePosAuto(vec.x, vec.y, vec.z, true);
            }

            assert mc.interactionManager != null;
            mc.interactionManager.updateBlockBreakingProgress(pos, dir);

            mc.player.swingHand(Hand.MAIN_HAND);

            broken++;
            if (getSetting(0).asMode().mode == 0
                    || (getSetting(0).asMode().mode == 1
                    && broken >= (int) getSetting(7).asSlider().getValue()))
                return;
        }
    }

    public boolean canSeeBlock(BlockPos pos) {
        double diffX = pos.getX() + 0.5 - mc.player.getCameraPosVec(mc.getTickDelta()).x;
        double diffY = pos.getY() + 0.5 - mc.player.getCameraPosVec(mc.getTickDelta()).y;
        double diffZ = pos.getZ() + 0.5 - mc.player.getCameraPosVec(mc.getTickDelta()).z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = mc.player.yaw + MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90 - mc.player.yaw);
        float pitch = mc.player.pitch + MathHelper.wrapDegrees((float) -Math.toDegrees(Math.atan2(diffY, diffXZ)) - mc.player.pitch);

        Vec3d rotation = new Vec3d(
                MathHelper.sin(-yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F),
                (-MathHelper.sin(pitch * 0.017453292F)),
                MathHelper.cos(-yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F));

        Vec3d rayVec = mc.player.getCameraPosVec(mc.getTickDelta()).add(rotation.x * 6, rotation.y * 6, rotation.z * 6);
        return mc.world.raycast(new RaycastContext(mc.player.getCameraPosVec(mc.getTickDelta()),
                rayVec, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player))
                .getBlockPos().equals(pos);
    }
}
