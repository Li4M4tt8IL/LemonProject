package me.pm.lemon.cosmetics.features;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.WingModelBase;
import me.pm.lemon.cosmetics.models.wings.DragonWingsModel;
import me.pm.lemon.utils.LemonLogger;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.awt.*;

public class WingFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
//    private WingsModel wingModel = new FeatheredWingModel(1);
    private WingModelBase wingModel = null;
    private PlayerEntityModel playerModel;

    public WingFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
        playerModel = featureRendererContext.getModel();
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        try {
//            if(Main.getDemonWings().contains(entity.getName().getString())) {
//                wingModel = new DemonWingModel(1F, playerModel);
//            } else if(Main.getAngelWings().contains(entity.getName().getString())) {
//                wingModel = new AngelWingsModel(1, playerModel);
//            } else {
//                wingModel = new NewWingModel(1, playerModel);
////                wingModel = null;
//            }
            Color color = null;
            if(Main.ClientInfo.wingsUUID.contains(entity.getUuidAsString()) || Main.debugMode) {
                wingModel = new DragonWingsModel(0.5f, playerModel);
                color = new Color(Util.rainbow(1));
            }
            if(wingModel == null) return;
            if(color == null) return;

            matrixStack.push();
            wingModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            renderWings(matrixStack, vertexConsumerProvider, wingModel.getTexture(), light, color.getRed(), color.getGreen(), color.getBlue());
            matrixStack.pop();
        } catch (Exception e) {
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
        }
    }

    public void renderWings(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Identifier identifier,int light, float r, float g, float b) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getText(identifier));
        this.wingModel.renderWings(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1);
    }
}
