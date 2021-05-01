package me.pm.lemon.mixin;

import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.world.Xray;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldDrawSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> callback) {
        try {
            Xray xray = (Xray) Module.getModule(Xray.class);
            if (xray.isToggled()) {
                if(xray.getSetting(0).asToggle().state) {
                    BlockPos left = new BlockPos(blockPos_1.getX() + 1, blockPos_1.getY(), blockPos_1.getZ());
                    Block blockLeft = MinecraftClient.getInstance().world.getBlockState(left).getBlock();

                    BlockPos right = new BlockPos(blockPos_1.getX() - 1, blockPos_1.getY(), blockPos_1.getZ());
                    Block blockRight = MinecraftClient.getInstance().world.getBlockState(right).getBlock();

                    BlockPos top = new BlockPos(blockPos_1.getX(), blockPos_1.getY() + 1, blockPos_1.getZ());
                    Block blockTop = MinecraftClient.getInstance().world.getBlockState(top).getBlock();

                    BlockPos bottom = new BlockPos(blockPos_1.getX(), blockPos_1.getY() - 1, blockPos_1.getZ());
                    Block blockBottom = MinecraftClient.getInstance().world.getBlockState(bottom).getBlock();

                    BlockPos forward = new BlockPos(blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ() + 1);
                    Block blockForward = MinecraftClient.getInstance().world.getBlockState(forward).getBlock();

                    BlockPos back = new BlockPos(blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ() - 1);
                    Block blockBack = MinecraftClient.getInstance().world.getBlockState(back).getBlock();

                    if(Util.isFluidOrAir(blockLeft)
                            || Util.isFluidOrAir(blockRight)
                            || Util.isFluidOrAir(blockTop)
                            || Util.isFluidOrAir(blockBottom)
                            || Util.isFluidOrAir(blockForward)
                            || Util.isFluidOrAir(blockBack)) {
                        callback.setReturnValue(xray.isVisible(blockState_1.getBlock()));
                    }
                } else {
                    callback.setReturnValue(xray.isVisible(blockState_1.getBlock()));
                }
                callback.cancel();
            }
        } catch (Exception ignored) {
        }
    }

    @Inject(method = "isShapeFullCube", at = @At("HEAD"), cancellable = true)
    private static void isShapeFullCube(VoxelShape voxelShape_1, CallbackInfoReturnable<Boolean> callback) {
        if (Module.getModule(Xray.class).isToggled()) {
            callback.setReturnValue(false);
            callback.cancel();
        }
    }

	/*@Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(CallbackInfoReturnable<BlockRenderType> callback) {
        try {
            if (ModuleManager.getModule(Xray.class).isToggled()) {
                callback.setReturnValue(BlockRenderType.INVISIBLE);
                callback.cancel();
            }
        } catch (Exception ignored) {}
    }*/
}
