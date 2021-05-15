package me.pm.lemon.cosmetics.models.hats;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.HatModelBase;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ShortHatModel extends HatModelBase {
    private final ModelPart hat01;
    private final ModelPart cube_r1;
    private final ModelPart cube_r2;
    private final ModelPart cube_r3;

    public ShortHatModel(float scale, PlayerEntityModel model) {
        super(scale, model);
        textureWidth = 64;
        textureHeight = 64;
        hat01 = new ModelPart(this);
        hat01.setPivot(0.0F, 24.0F, 0.0F);
        hat01.setTextureOffset(0, 0).addCuboid(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);

        cube_r1 = new ModelPart(this);
        cube_r1.setPivot(0.0F, -3.25F, 0.0F);
        hat01.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.3491F, 0.0F, 0.0F);
        cube_r1.setTextureOffset(0, 0).addCuboid(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        cube_r2 = new ModelPart(this);
        cube_r2.setPivot(0.0F, -1.75F, 0.0F);
        hat01.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.2618F, 0.0F, 0.0F);
        cube_r2.setTextureOffset(0, 21).addCuboid(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);

        cube_r3 = new ModelPart(this);
        cube_r3.setPivot(0.0F, -0.5F, 0.0F);
        hat01.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.1309F, 0.0F, 0.0F);
        cube_r3.setTextureOffset(0, 11).addCuboid(-4.0F, -2.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void renderHat(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        hat01.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        hat01.pivotY = 10;
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, "textures/short.png");
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }
}
