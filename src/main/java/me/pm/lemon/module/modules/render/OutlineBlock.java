package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.WorldRenderEvent;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.awt.*;

public class OutlineBlock extends Module {

    public OutlineBlock(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color, new SettingColor("Color", 0.0f, 1.0f, 1.0f, false));
    }

    @EventTarget
    public void onRender(WorldRenderEvent event) {
        if(mc.crosshairTarget == null) return;

        BlockPos pos = mc.crosshairTarget.getType() == HitResult.Type.BLOCK ?
                ((BlockHitResult) mc.crosshairTarget).getBlockPos() : null;

        if(pos != null) {
            float[] col = getSetting(0).asColor().getRGBFloat();
            Block block = mc.world.getBlockState(pos).getBlock();
            BlockView blockView = mc.world;
            VoxelShape voxelShape = block.getOutlineShape(mc.world.getBlockState(pos), blockView, pos, ShapeContext.of(mc.player));
            Box box = voxelShape.getBoundingBox().offset(pos);
            RenderUtils.drawOutlineBox(box, col[0], col[1], col[2], 1f);
            RenderUtils.drawFilledBox(box, col[0], col[1], col[2], 0.7f);
        }
    }
}
