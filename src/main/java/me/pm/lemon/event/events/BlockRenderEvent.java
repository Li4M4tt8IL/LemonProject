package me.pm.lemon.event.events;

import me.pm.lemon.event.Event;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockRenderEvent extends Event {
    private final BlockState blockState;
    private final BlockPos blockPos;

    public BlockRenderEvent(BlockState blockState, BlockPos blockPos) {
        this.blockState = blockState;
        this.blockPos = blockPos;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
