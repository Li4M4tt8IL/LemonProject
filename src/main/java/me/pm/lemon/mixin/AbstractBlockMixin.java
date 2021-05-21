package me.pm.lemon.mixin;

import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.world.Xray;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "getAmbientOcclusionLightLevel", at = @At("HEAD"), cancellable = true)
    public void getAmbientOcclusionLightLevel(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (Module.getModule(Xray.class).isToggled()) {
            callbackInfoReturnable.setReturnValue(1.0F);
        }

    }
}
