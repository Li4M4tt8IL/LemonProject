package me.pm.lemon.cosmetics.models.wings;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.WingModelBase;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class DragonWingsModel extends WingModelBase {
    private final ModelPart rightWing;
    private final ModelPart rightWing1;
    private final ModelPart leftWing;
    private final ModelPart leftWing1;

    public DragonWingsModel(float scale, PlayerEntityModel model) {
        super(scale, model);
        textureWidth = 64;
        textureHeight = 64;
        rightWing = new ModelPart(this);
        rightWing.setPivot(-2.0F, 2.0F, 2.0F);
        setRotationAngle(rightWing, -1.5708F, 0.1309F, 0.4363F);
        rightWing.setTextureOffset(24, 10).addCuboid(-8.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, 0.2F, false);
        rightWing.setTextureOffset(16, 0).addCuboid(-8.0F, -0.5F, 1.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);

        rightWing1 = new ModelPart(this);
        rightWing1.setPivot(-7.5F, 0.0F, 4.0F);
        rightWing.addChild(rightWing1);
        setRotationAngle(rightWing1, 0.0F, 0.0F, -0.3491F);
        rightWing1.setTextureOffset(24, 8).addCuboid(-8.0F, -1.0F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        rightWing1.setTextureOffset(0, 16).addCuboid(-8.0F, -0.5F, -3.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);

        leftWing = new ModelPart(this);
        leftWing.setPivot(2.0F, 2.0F, 2.0F);
        setRotationAngle(leftWing, -1.5708F, -0.1309F, -0.4363F);
        leftWing.setTextureOffset(0, 24).addCuboid(0.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, 0.2F, false);
        leftWing.setTextureOffset(0, 8).addCuboid(0.0F, -0.5F, 1.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);

        leftWing1 = new ModelPart(this);
        leftWing1.setPivot(7.5F, 0.0F, 4.0F);
        leftWing.addChild(leftWing1);
        setRotationAngle(leftWing1, 0.0F, 0.0F, 0.3491F);
        leftWing1.setTextureOffset(23, 23).addCuboid(0.0F, -1.0F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        leftWing1.setTextureOffset(0, 0).addCuboid(0.0F, -0.5F, -3.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);
    }


    @Override
    public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float pivotX;
        float pivotY;
        float pivotZ;

        float pitch;
        float yaw;
        float roll;

        if(entity.isSneaking()) {
            pivotX = -2F;
            pivotY = 5F;
            pivotZ = 2F;

            pitch = (float) Math.toRadians(-65);
            yaw = (float) Math.toRadians(7.5);
            roll = (float) Math.toRadians(25);

        } else {
            pivotX = -2F;
            pivotY = 1F;
            pivotZ = 2F;

            pitch = (float) Math.toRadians(-90);
            yaw = (float) Math.toRadians(7.5);
            roll = (float) Math.toRadians(25);
        }

        for(int j = 0; j < 2; ++j) {
            float f11 = (float)(System.currentTimeMillis() % 1000L) / 1000.0F * 3.1415927F * 2.0F;
            pitch = (float)Math.toRadians(-80.0D) - (float)Math.cos((double)f11) * 0.2F;
            yaw = (float)Math.toRadians(20.0D) + (float)Math.sin((double)f11) * 0.4F;
        }

        this.rightWing.pivotX = pivotX;
        this.rightWing.pivotY = pivotY;
        this.rightWing.pivotZ = pivotZ;

        this.rightWing.pitch = pitch;
        this.rightWing.yaw = yaw;
        this.rightWing.roll = roll;

        this.leftWing.pivotX = -this.rightWing.pivotX;
        this.leftWing.pivotY = this.rightWing.pivotY;
        this.leftWing.pivotZ = this.rightWing.pivotZ;

        this.leftWing.pitch = this.rightWing.pitch;
        this.leftWing.yaw = -this.rightWing.yaw;
        this.leftWing.roll = -this.rightWing.roll;

        this.leftWing1.pitch = this.rightWing1.pitch;
        this.leftWing1.yaw = -this.rightWing1.yaw;
        this.leftWing1.roll = -this.rightWing1.roll;
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, "textures/dragon.png");
    }

    @Override
    public void renderWings(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        rightWing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leftWing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
