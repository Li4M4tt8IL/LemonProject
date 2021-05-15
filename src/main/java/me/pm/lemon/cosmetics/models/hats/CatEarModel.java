package me.pm.lemon.cosmetics.models.hats;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.HatModelBase;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CatEarModel extends HatModelBase {
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private String skin;

    public CatEarModel(float scale, PlayerEntityModel model, String string) {
        super(scale, model);
        skin = string;
        textureWidth = 32;
        textureHeight = 32;
        rightEar = new ModelPart(this);
        rightEar.setPivot(-2.0F, 24.0F, 0.0F);
        rightEar.setTextureOffset(13, 10).addCuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(3, 3).addCuboid(-2.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(0, 6).addCuboid(-3.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(13, 8).addCuboid(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(13, 6).addCuboid(-1.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(10, 13).addCuboid(-2.0F, -4.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(0, 13).addCuboid(-3.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(10, 9).addCuboid(-4.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(3, 12).addCuboid(-4.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(0, 11).addCuboid(-2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(10, 11).addCuboid(-3.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(11, 1).addCuboid(-4.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        leftEar = new ModelPart(this);
        leftEar.setPivot(2.0F, 24.0F, 0.0F);
        leftEar.setTextureOffset(10, 7).addCuboid(3.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(10, 5).addCuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(0, 0).addCuboid(1.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(4, 0).addCuboid(2.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(10, 3).addCuboid(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(7, 10).addCuboid(0.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(8, 0).addCuboid(1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(7, 8).addCuboid(2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(3, 8).addCuboid(3.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(7, 4).addCuboid(3.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(7, 2).addCuboid(1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(6, 6).addCuboid(2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        rightEar.setPivot( 5, -7, -2);
        leftEar.setPivot(-5, -7, -2);
    }

    @Override
    public void renderHat(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        leftEar.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rightEar.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }


    @Override
    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, String.format("textures/%s.png", skin));
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }
}
