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

public class NewWingModel extends WingModelBase {
    private final ModelPart leftWing;
    private final ModelPart leftWingP1;
    private final ModelPart cube_r1;
    private final ModelPart leftWingP2;
    private final ModelPart cube_r2;
    private final ModelPart leftWingP3;
    private final ModelPart cube_r3;
    private final ModelPart leftWingP4;
    private final ModelPart cube_r4;
    private final ModelPart rightWing;
    private final ModelPart rightWingP1;
    private final ModelPart cube_r5;
    private final ModelPart rightWingP2;
    private final ModelPart cube_r6;
    private final ModelPart rightWingP3;
    private final ModelPart cube_r7;
    private final ModelPart rightWingP4;
    private final ModelPart cube_r8;

    public NewWingModel(float scale, PlayerEntityModel model) {
        super(scale, model);
        textureWidth = 112;
        textureHeight = 112;
        leftWing = new ModelPart(this);
        leftWing.setPivot(1.0F, 24.0F, 1.0F);


        leftWingP1 = new ModelPart(this);
        leftWingP1.setPivot(0.0F, 0.0F, 0.0F);
        leftWing.addChild(leftWingP1);


        cube_r1 = new ModelPart(this);
        cube_r1.setPivot(-1.0F, 0.0F, -1.0F);
        leftWingP1.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.3054F, -0.2618F, 0.1309F);
        cube_r1.setTextureOffset(36, 0).addCuboid(1.0F, -1.05F, 2.0F, 9.0F, 0.0F, 9.0F, 0.0F, false);
        cube_r1.setTextureOffset(0, 29).addCuboid(1.0F, -2.0F, 0.0F, 9.0F, 2.0F, 2.0F, 0.0F, false);

        leftWingP2 = new ModelPart(this);
        leftWingP2.setPivot(8.0F, 1.0F, 2.0F);
        leftWing.addChild(leftWingP2);


        cube_r2 = new ModelPart(this);
        cube_r2.setPivot(-9.0F, -1.0F, -3.0F);
        leftWingP2.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.3054F, -0.2618F, -0.2182F);
        cube_r2.setTextureOffset(0, 33).addCuboid(9.25F, 0.9F, 1.15F, 7.0F, 2.0F, 2.0F, 0.0F, false);
        cube_r2.setTextureOffset(68, 10).addCuboid(9.25F, 1.6F, 3.15F, 7.0F, 0.0F, 10.0F, 0.0F, false);

        leftWingP3 = new ModelPart(this);
        leftWingP3.setPivot(14.0F, 0.0F, 4.0F);
        leftWing.addChild(leftWingP3);


        cube_r3 = new ModelPart(this);
        cube_r3.setPivot(-15.0F, 0.0F, -5.0F);
        leftWingP3.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.3054F, -0.2618F, 0.0873F);
        cube_r3.setTextureOffset(34, 10).addCuboid(15.65F, -2.6F, 1.75F, 7.0F, 0.0F, 10.0F, 0.0F, false);
        cube_r3.setTextureOffset(58, 29).addCuboid(15.65F, -3.6F, -0.25F, 7.0F, 2.0F, 2.0F, 0.0F, false);

        leftWingP4 = new ModelPart(this);
        leftWingP4.setPivot(21.0F, 0.0F, 6.0F);
        leftWing.addChild(leftWingP4);


        cube_r4 = new ModelPart(this);
        cube_r4.setPivot(-22.0F, 0.0F, -7.0F);
        leftWingP4.addChild(cube_r4);
        setRotationAngle(cube_r4, -0.3054F, -0.5672F, -0.1309F);
        cube_r4.setTextureOffset(28, 20).addCuboid(21.65F, 3.8F, -3.45F, 5.0F, 0.0F, 9.0F, 0.0F, false);
        cube_r4.setTextureOffset(32, 33).addCuboid(21.65F, 2.9F, -5.45F, 5.0F, 2.0F, 2.0F, 0.0F, false);

        rightWing = new ModelPart(this);
        rightWing.setPivot(-1.0F, 24.0F, 1.0F);


        rightWingP1 = new ModelPart(this);
        rightWingP1.setPivot(0.0F, 0.0F, 0.0F);
        rightWing.addChild(rightWingP1);


        cube_r5 = new ModelPart(this);
        cube_r5.setPivot(1.0F, 0.0F, -1.0F);
        rightWingP1.addChild(cube_r5);
        setRotationAngle(cube_r5, -0.3054F, 0.2618F, -0.1309F);
        cube_r5.setTextureOffset(0, 0).addCuboid(-10.0F, -1.05F, 2.0F, 9.0F, 0.0F, 9.0F, 0.0F, false);
        cube_r5.setTextureOffset(56, 20).addCuboid(-10.0F, -2.0F, 0.0F, 9.0F, 2.0F, 2.0F, 0.0F, false);

        rightWingP2 = new ModelPart(this);
        rightWingP2.setPivot(-8.0F, 1.0F, 2.0F);
        rightWing.addChild(rightWingP2);


        cube_r6 = new ModelPart(this);
        cube_r6.setPivot(9.0F, -1.0F, -3.0F);
        rightWingP2.addChild(cube_r6);
        setRotationAngle(cube_r6, -0.3054F, 0.2618F, 0.2182F);
        cube_r6.setTextureOffset(40, 29).addCuboid(-16.25F, 0.9F, 1.15F, 7.0F, 2.0F, 2.0F, 0.0F, false);
        cube_r6.setTextureOffset(0, 10).addCuboid(-16.25F, 1.6F, 3.15F, 7.0F, 0.0F, 10.0F, 0.0F, false);

        rightWingP3 = new ModelPart(this);
        rightWingP3.setPivot(-14.0F, 0.0F, 4.0F);
        rightWing.addChild(rightWingP3);


        cube_r7 = new ModelPart(this);
        cube_r7.setPivot(15.0F, 0.0F, -5.0F);
        rightWingP3.addChild(cube_r7);
        setRotationAngle(cube_r7, -0.3054F, 0.2618F, -0.0873F);
        cube_r7.setTextureOffset(72, 0).addCuboid(-22.65F, -2.6F, 1.75F, 7.0F, 0.0F, 10.0F, 0.0F, false);
        cube_r7.setTextureOffset(22, 29).addCuboid(-22.65F, -3.6F, -0.25F, 7.0F, 2.0F, 2.0F, 0.0F, false);

        rightWingP4 = new ModelPart(this);
        rightWingP4.setPivot(-21.0F, 0.0F, 6.0F);
        rightWing.addChild(rightWingP4);


        cube_r8 = new ModelPart(this);
        cube_r8.setPivot(22.0F, 0.0F, -7.0F);
        rightWingP4.addChild(cube_r8);
        setRotationAngle(cube_r8, -0.3054F, 0.5672F, 0.1309F);
        cube_r8.setTextureOffset(0, 20).addCuboid(-26.65F, 3.8F, -3.45F, 5.0F, 0.0F, 9.0F, 0.0F, false);
        cube_r8.setTextureOffset(18, 33).addCuboid(-26.65F, 2.9F, -5.45F, 5.0F, 2.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void renderWings(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        leftWing.render(matrices, vertices, light, overlay);
        rightWing.render(matrices, vertices, light, overlay);
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, "textures/text.png");
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
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
