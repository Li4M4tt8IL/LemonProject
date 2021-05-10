package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class AutoWaterBucket extends Module {
    public AutoWaterBucket(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }
    int oldSlot = -1;
    @EventTarget
    public void onTick(TickEvent event) {
        assert mc.player != null;
        if(mc.player.fallDistance >= 2F) {
            oldSlot = Util.changeHotbarSlotToItem(Items.WATER_BUCKET);

            if (mc.player.getMainHandStack().getItem() != Items.WATER_BUCKET) {return;}

//            assert mc.world != null;
//            if(mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock() != Blocks.AIR)
//                mc.interactionManager.b

        }
    }
}
