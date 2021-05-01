package me.pm.lemon.mixin;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public interface BlockAccessor {
    @Accessor("slipperiness")
    public void setSlipperiness(float slipperiness);
}
