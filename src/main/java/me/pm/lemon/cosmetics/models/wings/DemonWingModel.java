package me.pm.lemon.cosmetics.models.wings;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.WingModelBase;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DemonWingModel extends WingModelBase {
    public final ModelPart rightWing;
    public final ModelPart leftWing;

    public DemonWingModel(float scale, PlayerEntityModel model) {
        super(scale, model);
        textureWidth = 112;
        textureHeight = 112;
        rightWing = new ModelPart(this);
        rightWing.setPivot(0.0F, 23.0F, 2.0F);
        rightWing.setTextureOffset(78, 6).addCuboid(-13.0F, 0.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 14).addCuboid(-13.0F, -1.0F, 1.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(28, 14).addCuboid(-13.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(60, 16).addCuboid(-12.0F, -3.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(10, 16).addCuboid(-13.0F, -4.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(52, 16).addCuboid(-13.0F, -5.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(24, 18).addCuboid(-12.0F, -6.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(4, 28).addCuboid(-11.0F, -7.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 28).addCuboid(-11.0F, -8.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(52, 6).addCuboid(-14.0F, 1.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(26, 6).addCuboid(-14.0F, 2.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(56, 2).addCuboid(-15.0F, 3.0F, 1.0F, 13.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(28, 2).addCuboid(-15.0F, 4.0F, 1.0F, 13.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 2).addCuboid(-16.0F, 5.0F, 1.0F, 13.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 6).addCuboid(-16.0F, 6.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(24, 8).addCuboid(-16.0F, 7.0F, 1.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(22, 10).addCuboid(-16.0F, 8.0F, 1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 10).addCuboid(-16.0F, 9.0F, 1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(20, 12).addCuboid(-16.0F, 10.0F, 1.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 12).addCuboid(-16.0F, 11.0F, 1.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(58, 12).addCuboid(-16.0F, 12.0F, 1.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 16).addCuboid(-15.0F, 13.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(70, 14).addCuboid(-15.0F, 14.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(44, 16).addCuboid(-15.0F, 15.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(18, 18).addCuboid(-15.0F, 16.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(56, 26).addCuboid(-15.0F, 17.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(52, 26).addCuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(48, 26).addCuboid(-2.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(44, 26).addCuboid(-2.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(40, 26).addCuboid(-16.0F, 16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(36, 26).addCuboid(-16.0F, 15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(32, 26).addCuboid(-16.0F, 14.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(28, 26).addCuboid(-16.0F, 13.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(24, 26).addCuboid(-17.0F, 13.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(20, 26).addCuboid(-17.0F, 12.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(16, 26).addCuboid(-17.0F, 11.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(12, 26).addCuboid(-17.0F, 10.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(8, 26).addCuboid(-17.0F, 9.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(4, 26).addCuboid(-17.0F, 8.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 26).addCuboid(-17.0F, 7.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(56, 24).addCuboid(-17.0F, 6.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(52, 24).addCuboid(-17.0F, 5.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(48, 24).addCuboid(-17.0F, 4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(44, 24).addCuboid(-16.0F, 4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(40, 24).addCuboid(-16.0F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(36, 24).addCuboid(-16.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(32, 24).addCuboid(-15.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(28, 24).addCuboid(-15.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(24, 24).addCuboid(-15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(20, 24).addCuboid(-14.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(16, 24).addCuboid(-14.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(12, 24).addCuboid(-14.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(8, 24).addCuboid(-14.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(4, 24).addCuboid(-13.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(0, 24).addCuboid(-13.0F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(52, 22).addCuboid(-13.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(12, 18).addCuboid(-10.0F, 13.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing.setTextureOffset(48, 22).addCuboid(-9.0F, 14.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        leftWing = new ModelPart(this);
        leftWing.setPivot(2.0F, 23.0F, 2.0F);
        leftWing.setTextureOffset(78, 4).addCuboid(1.0F, 0.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(76, 12).addCuboid(6.0F, -1.0F, 1.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(16, 14).addCuboid(8.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(36, 16).addCuboid(9.0F, -3.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(60, 14).addCuboid(9.0F, -4.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(28, 16).addCuboid(10.0F, -5.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(6, 18).addCuboid(10.0F, -6.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(44, 22).addCuboid(10.0F, -7.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(40, 22).addCuboid(10.0F, -8.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(52, 4).addCuboid(2.0F, 1.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(26, 4).addCuboid(2.0F, 2.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(56, 0).addCuboid(2.0F, 3.0F, 1.0F, 13.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(28, 0).addCuboid(2.0F, 4.0F, 1.0F, 13.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(0, 0).addCuboid(3.0F, 5.0F, 1.0F, 13.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(0, 4).addCuboid(4.0F, 6.0F, 1.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(0, 8).addCuboid(5.0F, 7.0F, 1.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(70, 8).addCuboid(6.0F, 8.0F, 1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(48, 8).addCuboid(6.0F, 9.0F, 1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(64, 10).addCuboid(7.0F, 10.0F, 1.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(44, 10).addCuboid(7.0F, 11.0F, 1.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(40, 12).addCuboid(8.0F, 12.0F, 1.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(50, 14).addCuboid(11.0F, 13.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(40, 14).addCuboid(11.0F, 14.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(20, 16).addCuboid(12.0F, 15.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(0, 18).addCuboid(13.0F, 16.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(36, 22).addCuboid(14.0F, 17.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(32, 22).addCuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(28, 22).addCuboid(1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(24, 22).addCuboid(1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(8, 28).addCuboid(2.0F, 2.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(20, 22).addCuboid(15.0F, 16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(16, 22).addCuboid(15.0F, 15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(12, 22).addCuboid(15.0F, 14.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(8, 22).addCuboid(15.0F, 13.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(4, 22).addCuboid(16.0F, 13.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(0, 22).addCuboid(16.0F, 12.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(56, 20).addCuboid(16.0F, 11.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(52, 20).addCuboid(16.0F, 10.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(48, 20).addCuboid(16.0F, 9.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(44, 20).addCuboid(16.0F, 8.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(40, 20).addCuboid(16.0F, 7.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(36, 20).addCuboid(16.0F, 6.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(32, 20).addCuboid(16.0F, 5.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(28, 20).addCuboid(16.0F, 4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(24, 20).addCuboid(15.0F, 4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(20, 20).addCuboid(15.0F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(16, 20).addCuboid(15.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(12, 20).addCuboid(14.0F, 2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(8, 20).addCuboid(14.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(4, 20).addCuboid(14.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(0, 20).addCuboid(13.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(58, 18).addCuboid(13.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(54, 18).addCuboid(13.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(50, 18).addCuboid(13.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(46, 18).addCuboid(12.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(42, 18).addCuboid(12.0F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(34, 18).addCuboid(12.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(68, 16).addCuboid(8.0F, 13.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing.setTextureOffset(30, 18).addCuboid(8.0F, 14.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void renderWings(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        leftWing.render(matrices, vertices, light, overlay);
        rightWing.render(matrices, vertices, light, overlay);
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, "textures/demon-dark.png");
    }

    @Override
    public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float yaw = 0.3F;
        float pitch = 0F;
        float pivotX = 3F;
        float pivotY = 0F;
        float a = 0.125F;
        float b = 0.1F;

        if(entity.isFallFlying()) {
            float o = 1.0F;
            Vec3d vec3d = entity.getVelocity();

            if(vec3d.y < 0.0D) {
                Vec3d vec3d2 = vec3d.normalize();
                o = 1.0F - (float) Math.pow(-vec3d2.y, 1.5D);
            }

            yaw = o * 0.35F + (1.0F - o) * yaw;

            a = 0.4F;
            b = 1.0F;
        } else if(entity.forwardSpeed > 0) {
            a = 0.2F;
            b = 0.2F;
            if(entity.isInSneakingPose()) {
                pitch = (float) Math.toRadians(20);
                pivotY = 4F;
            }
        }
        else if(entity.isInSneakingPose()) {
            pitch = (float) Math.toRadians(20);
            pivotY = 4F;
        } else {
            pitch = 0;
        }

        yaw += (MathHelper.cos(entity.age * a) * b);

        this.rightWing.pitch = pitch;
        this.rightWing.yaw = yaw;
        this.rightWing.pivotX = -pivotX;
        this.rightWing.pivotY = pivotY;


        this.leftWing.pivotX = -this.rightWing.pivotX;
        this.leftWing.pivotY = this.rightWing.pivotY;
        this.leftWing.pivotZ = this.rightWing.pivotZ;
        this.leftWing.yaw = -this.rightWing.yaw;
        this.leftWing.pitch = this.rightWing.pitch;
    }
}
