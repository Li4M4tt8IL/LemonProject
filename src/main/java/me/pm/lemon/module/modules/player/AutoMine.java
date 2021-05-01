package me.pm.lemon.module.modules.player;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.testScreen.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.Arrays;

public class AutoMine extends Module {
    public AutoMine(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("BlockPerSecond", 1, 5, 1, 0));
    }

    private BlockPos currentBlock;

    @EventTarget
    public void onTick(TickEvent event) {
        setCurrentBlockFromHitResult();

        if(currentBlock != null) {
            breakCurrentBlock();
        }
    }

    private void setCurrentBlockFromHitResult()
    {
        if(mc.crosshairTarget == null || mc.crosshairTarget.getPos() == null
                || mc.crosshairTarget.getType() != HitResult.Type.BLOCK
                || !(mc.crosshairTarget instanceof BlockHitResult))
        {
            stopMiningAndResetProgress();
            return;
        }

        currentBlock = ((BlockHitResult)mc.crosshairTarget).getBlockPos();
    }

    private void breakCurrentBlock()
    {
        if(mc.player.abilities.creativeMode)
            Util.breakBlocksWithPacketSpam(Arrays.asList(currentBlock));
        else
            Util.breakOneBlock(currentBlock);
    }

    private void stopMiningAndResetProgress()
    {
        if(currentBlock == null)
            return;

//        mc.interactionManager.setBreakingBlock(true);
        mc.interactionManager.cancelBlockBreaking();
        currentBlock = null;
    }
}