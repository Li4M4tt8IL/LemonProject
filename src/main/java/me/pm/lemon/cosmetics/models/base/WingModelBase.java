package me.pm.lemon.cosmetics.models.base;

import me.pm.lemon.Main;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class WingModelBase<T extends LivingEntity> extends BipedEntityModel<T> {

    public WingModelBase(float scale, PlayerEntityModel model) {
        super(scale);
    }

    public void renderWings(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
    }

    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, "textures/wing.png");
    }
}
