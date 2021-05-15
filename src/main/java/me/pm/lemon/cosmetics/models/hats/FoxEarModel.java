package me.pm.lemon.cosmetics.models.hats;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.HatModelBase;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FoxEarModel extends HatModelBase {
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private String skin;

    public FoxEarModel(float scale, PlayerEntityModel model, String string) {
        super(scale, model);
        skin = string;
        textureWidth = 16;
        textureHeight = 16;

        leftEar = new ModelPart(this);
        leftEar.setPivot(2.0F, 24.0F, 0.0F);
        leftEar.setTextureOffset(0, 6).addCuboid(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(4, 0).addCuboid(1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(0, 3).addCuboid(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        leftEar.setTextureOffset(4, 6).addCuboid(1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        rightEar = new ModelPart(this);
        rightEar.setPivot(-2.0F, 24.0F, 0.0F);
        rightEar.setTextureOffset(7, 5).addCuboid(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(7, 2).addCuboid(-2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(4, 3).addCuboid(-2.0F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        rightEar.setTextureOffset(0, 0).addCuboid(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        rightEar.setPivot( -1.75F, -8, -2.75f);
        leftEar.setPivot(1.75F, -8, -2.75f);
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
