package me.pm.lemon.cosmetics.features;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.HatModelBase;
import me.pm.lemon.cosmetics.models.hats.CatEarModel;
import me.pm.lemon.cosmetics.models.hats.ChristmasHatModel;
import me.pm.lemon.cosmetics.models.hats.FoxEarModel;
import me.pm.lemon.cosmetics.models.hats.TopHatModel;
import me.pm.lemon.utils.LemonLogger;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;

public class HatFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private HatModelBase hatModel = null;
    private PlayerEntityModel playerModel;

    public HatFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
        playerModel = featureRendererContext.getModel();
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, AbstractClientPlayerEntity entity,float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        try {
            if(Main.getTopHats().contains(entity.getUuidAsString().replaceAll("-", ""))) {
                hatModel = new TopHatModel(1F, playerModel);
            } else if(Main.getDarkEars().contains(entity.getUuidAsString().replaceAll("-", ""))) {
                hatModel = new CatEarModel(1F, playerModel, "dark");
            } else if(Main.getLightEars().contains(entity.getUuidAsString().replaceAll("-", ""))) {
                hatModel = new CatEarModel(1F, playerModel, "light");
            } else if(Main.getChristmasHats().contains(entity.getUuidAsString().replaceAll("-", ""))) {
                hatModel = new ChristmasHatModel(1F, playerModel);
            } else if(Main.getOrangeFoxEars().contains(entity.getUuidAsString().replaceAll("-", ""))) {
                hatModel = new FoxEarModel(1F, playerModel, "orange");
            } else if(Main.getWhiteFoxEars().contains(entity.getUuidAsString().replaceAll("-", ""))) {
                hatModel = new FoxEarModel(1F, playerModel, "white");
            } else {
                hatModel = null;
            }

            if(hatModel == null) return;
            for(int n = 0; n < 2; ++n) {
                float o = MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw) - MathHelper.lerp(tickDelta, entity.prevBodyYaw, entity.bodyYaw);
                float p = MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch);
                matrixStack.push();
                matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(o));
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(p));
                if(entity.isSneaking()) {
                    matrixStack.translate(0, 0.225D, 0);
                }
                VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getText(hatModel.getTexture()));
                hatModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                hatModel.renderHat(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                matrixStack.pop();
            }
        } catch (Exception e) {
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
        }

    }
}