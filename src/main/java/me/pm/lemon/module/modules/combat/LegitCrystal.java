package me.pm.lemon.module.modules.combat;

import com.google.common.collect.Streams;
import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.gui.clickGui.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LegitCrystal extends Module {
    private final int delay = 0;
    public Entity target;
    ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
    ArrayList<BlockPos> blocksRange;
    int oldSlot = -1;
    int counter = 0;
    private BlockPos targetBlock;

    public LegitCrystal(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("MinEnemyDMG", 0, 20, 6, 0),
                new SettingToggle("Anti Suicide", true),
                new SettingSlider("Range", 0, 6, 4, 2));

        ArrayList<BlockPos> blocksRangeAdder = new ArrayList<BlockPos>();
        int range = (int) getSetting(10).asSlider().getValue();
        for(int x = range; x >= -range; x--)
        {
            for(int y = range; y >= -range; y--)
            {
                for(int z = range; z >= -range; z--)
                {
                    blocksRangeAdder.add(new BlockPos(x, y, z));
                }
            }
        }
        blocksRange = new ArrayList<>(blocksRangeAdder);
    }

    @Override
    public void onEnable() {
        target = Streams.stream(mc.world.getEntities())
                .filter(e ->
                        (e instanceof PlayerEntity))
                .filter(e -> !(getFriendManager().isFriend(e.getName().asString())) && e != mc.player)
                .filter(e -> mc.player.distanceTo(e) < 13)
                .filter(e -> !((LivingEntity) e).isDead())
                .sorted((a, b) -> Float.compare(a.distanceTo(mc.player), b.distanceTo(mc.player)))
                .findFirst()
                .orElse(null);
        if(target == null)
            return;

        blocks.clear();
        blocksRange.parallelStream()
                .filter(pos -> Util.canPlace(pos.add(mc.player.getX(), mc.player.getY(), mc.player.getZ())))
                .forEach(pos -> blocks.add(pos.add(mc.player.getX(), mc.player.getY(), mc.player.getZ())));

        //calculate the block that does the most damage to the target
        targetBlock = blocks.parallelStream()
                .filter(block -> Util.getCrystalDamage(mc.player, block) < getSetting(1).asSlider().getValue())
                //.filter(block -> Utils.getCrystalDamage(mc.player, block) < mc.player.getHealth())
                .filter(block -> {
                    LivingEntity livingEntity = (LivingEntity) target;
                    return Util.getCrystalDamage(livingEntity, block) > getSetting(5).asSlider().getValue();
                })
                .collect(Collectors.toList())
                .stream()
                .sorted((block1, block2) -> Double.compare(Util.getCrystalDamage((LivingEntity) target, block2), Util.getCrystalDamage((LivingEntity) target, block1)))
                .findFirst()
                .orElse(null);

        if(targetBlock == null)
            return;
        toggle();
    }
}
