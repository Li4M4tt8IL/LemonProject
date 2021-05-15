package me.pm.lemon.cosmetics.models.hats;

import me.pm.lemon.Main;
import me.pm.lemon.cosmetics.models.base.HatModelBase;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TopHatModel extends HatModelBase {
    private ModelPart rim;
    private ModelPart pointy;

    public TopHatModel(float scale, PlayerEntityModel model) {
        super(scale, model);
        rim = new ModelPart(model, 0, 0);
        pointy = new ModelPart(model, 0, 13);
        rim.addCuboid(-5.5F, -10F, -5.5F, 11, 2, 11);
        pointy.addCuboid(-3.5F, -17F, -3.5F, 7, 8, 7);
    }

    @Override
    public void renderHat(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        rim.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        pointy.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, "textures/hat.png");
    }
}