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

public class AngelWingsModel extends WingModelBase {
    private final ModelPart leftWing;
    private final ModelPart outline2;
    private final ModelPart preoutline2;
    private final ModelPart fill2;
    private final ModelPart rightWing;
    private final ModelPart outline;
    private final ModelPart preoutline;
    private final ModelPart fill;

    public AngelWingsModel(float scale, PlayerEntityModel model) {
        super(scale, model);
        textureWidth = 64;
        textureHeight = 64;
        leftWing = new ModelPart(this);
        leftWing.setPivot(2.0F, 23.0F, 0.0F);


        outline2 = new ModelPart(this);
        outline2.setPivot(-2.0F, 1.0F, 0.0F);
        leftWing.addChild(outline2);
        outline2.setTextureOffset(37, 31).addCuboid(18.0F, -8.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(27, 37).addCuboid(2.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(37, 29).addCuboid(3.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(37, 15).addCuboid(5.0F, -4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(37, 13).addCuboid(7.0F, -5.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(48, 54).addCuboid(9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(36, 54).addCuboid(10.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(47, 18).addCuboid(11.0F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(15, 37).addCuboid(12.0F, -12.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(37, 11).addCuboid(13.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(36, 37).addCuboid(15.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(21, 37).addCuboid(17.0F, -15.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(31, 36).addCuboid(19.0F, -16.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(10, 36).addCuboid(20.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(4, 36).addCuboid(18.0F, -12.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(35, 19).addCuboid(16.0F, -11.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(22, 54).addCuboid(21.0F, -17.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(8, 54).addCuboid(22.0F, -18.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(4, 54).addCuboid(23.0F, -19.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(0, 54).addCuboid(24.0F, -19.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(0, 36).addCuboid(24.0F, -18.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(53, 49).addCuboid(23.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(35, 1).addCuboid(22.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(47, 3).addCuboid(24.0F, -13.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(53, 47).addCuboid(23.0F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(53, 24).addCuboid(23.0F, -10.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(8, 25).addCuboid(22.0F, -10.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(4, 25).addCuboid(21.0F, -6.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(53, 1).addCuboid(20.0F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(22, 35).addCuboid(18.0F, -4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(34, 9).addCuboid(16.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(52, 53).addCuboid(15.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(18, 34).addCuboid(20.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(45, 53).addCuboid(19.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(15, 10).addCuboid(16.0F, 2.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(34, 6).addCuboid(14.0F, 3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(34, 3).addCuboid(12.0F, 2.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(41, 53).addCuboid(11.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(33, 53).addCuboid(10.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(0, 2).addCuboid(11.0F, 5.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(32, 34).addCuboid(9.0F, 4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(9, 47).addCuboid(9.0F, 5.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(29, 53).addCuboid(8.0F, 6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(25, 53).addCuboid(7.0F, 5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(19, 53).addCuboid(6.0F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(15, 53).addCuboid(5.0F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(11, 53).addCuboid(4.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(52, 51).addCuboid(3.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(46, 8).addCuboid(8.0F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(52, 45).addCuboid(19.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(12, 34).addCuboid(17.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(6, 34).addCuboid(15.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(52, 43).addCuboid(14.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(52, 29).addCuboid(13.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline2.setTextureOffset(0, 34).addCuboid(20.0F, -9.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        preoutline2 = new ModelPart(this);
        preoutline2.setPivot(-2.0F, 1.0F, 0.0F);
        leftWing.addChild(preoutline2);
        preoutline2.setTextureOffset(46, 0).addCuboid(3.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(39, 46).addCuboid(9.0F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(33, 46).addCuboid(8.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(25, 46).addCuboid(10.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(0, 46).addCuboid(12.0F, -4.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(45, 41).addCuboid(15.0F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(24, 24).addCuboid(13.0F, -6.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(28, 33).addCuboid(11.0F, -5.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(0, 24).addCuboid(14.0F, -8.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(52, 15).addCuboid(4.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(52, 6).addCuboid(9.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(49, 52).addCuboid(7.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(38, 52).addCuboid(5.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(22, 52).addCuboid(5.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(8, 52).addCuboid(6.0F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(4, 52).addCuboid(7.0F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(0, 52).addCuboid(8.0F, 5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(0, 0).addCuboid(11.0F, 4.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(33, 27).addCuboid(6.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(33, 25).addCuboid(14.0F, 2.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(6, 15).addCuboid(15.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(33, 22).addCuboid(12.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(22, 33).addCuboid(15.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(32, 30).addCuboid(17.0F, -2.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(32, 17).addCuboid(16.0F, -4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(32, 14).addCuboid(18.0F, -5.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 41).addCuboid(19.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 38).addCuboid(20.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 34).addCuboid(21.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 31).addCuboid(22.0F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 27).addCuboid(23.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 22).addCuboid(23.0F, -16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 19).addCuboid(22.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 13).addCuboid(19.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 10).addCuboid(17.0F, -14.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(51, 4).addCuboid(13.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(46, 51).addCuboid(12.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(42, 51).addCuboid(11.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(35, 51).addCuboid(10.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(31, 51).addCuboid(9.0F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(27, 51).addCuboid(11.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(19, 51).addCuboid(20.0F, -3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(15, 51).addCuboid(19.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(13, 14).addCuboid(16.0F, 1.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(11, 51).addCuboid(15.0F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(50, 48).addCuboid(7.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(32, 12).addCuboid(20.0F, -10.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(32, 32).addCuboid(20.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(4, 32).addCuboid(18.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline2.setTextureOffset(50, 36).addCuboid(17.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        fill2 = new ModelPart(this);
        fill2.setPivot(-2.0F, 1.0F, 0.0F);
        leftWing.addChild(fill2);
        fill2.setTextureOffset(27, 31).addCuboid(3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(8, 8).addCuboid(4.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(6, 13).addCuboid(5.0F, -3.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(50, 25).addCuboid(7.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(50, 17).addCuboid(5.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(50, 8).addCuboid(8.0F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(50, 2).addCuboid(19.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(50, 0).addCuboid(12.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(49, 50).addCuboid(16.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(39, 50).addCuboid(20.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(30, 20).addCuboid(20.0F, -8.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(23, 9).addCuboid(18.0F, -7.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(18, 23).addCuboid(16.0F, -6.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(0, 13).addCuboid(16.0F, -10.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(12, 23).addCuboid(18.0F, -11.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(6, 22).addCuboid(20.0F, -12.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(24, 50).addCuboid(18.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(8, 50).addCuboid(22.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(30, 0).addCuboid(22.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(4, 30).addCuboid(18.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(8, 11).addCuboid(15.0F, -13.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(0, 11).addCuboid(14.0F, -12.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(10, 6).addCuboid(13.0F, -11.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(0, 8).addCuboid(13.0F, -10.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(29, 10).addCuboid(13.0F, -9.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(21, 17).addCuboid(12.0F, -8.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(45, 45).addCuboid(12.0F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(49, 46).addCuboid(11.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(32, 49).addCuboid(14.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(21, 14).addCuboid(14.0F, -3.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(0, 21).addCuboid(11.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(48, 40).addCuboid(14.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(29, 45).addCuboid(13.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(21, 45).addCuboid(7.0F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(48, 23).addCuboid(6.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(29, 8).addCuboid(6.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(29, 5).addCuboid(9.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(29, 2).addCuboid(12.0F, 3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(18, 20).addCuboid(10.0F, 2.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(17, 45).addCuboid(23.0F, -18.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(13, 45).addCuboid(22.0F, -17.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(44, 5).addCuboid(21.0F, -16.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(48, 21).addCuboid(20.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(48, 6).addCuboid(20.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(47, 35).addCuboid(15.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(28, 26).addCuboid(18.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(27, 18).addCuboid(17.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(18, 0).addCuboid(14.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(12, 20).addCuboid(8.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(6, 19).addCuboid(8.0F, -4.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill2.setTextureOffset(23, 29).addCuboid(10.0F, -5.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        rightWing = new ModelPart(this);
        rightWing.setPivot(-2.0F, 23.0F, 0.0F);


        outline = new ModelPart(this);
        outline.setPivot(2.0F, 1.0F, 0.0F);
        rightWing.addChild(outline);
        outline.setTextureOffset(42, 30).addCuboid(-20.0F, -8.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(5, 45).addCuboid(-3.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(40, 44).addCuboid(-5.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, true);
        outline.setTextureOffset(24, 44).addCuboid(-7.0F, -4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(0, 44).addCuboid(-9.0F, -5.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(58, 20).addCuboid(-10.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(58, 11).addCuboid(-11.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(4, 49).addCuboid(-12.0F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(32, 42).addCuboid(-13.0F, -12.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(43, 24).addCuboid(-15.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(43, 22).addCuboid(-17.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(43, 16).addCuboid(-19.0F, -15.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(41, 19).addCuboid(-21.0F, -16.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(13, 43).addCuboid(-22.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(42, 36).addCuboid(-20.0F, -12.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(42, 34).addCuboid(-18.0F, -11.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(58, 4).addCuboid(-22.0F, -17.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(46, 58).addCuboid(-23.0F, -18.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(36, 58).addCuboid(-24.0F, -19.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(22, 58).addCuboid(-25.0F, -19.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(36, 43).addCuboid(-25.0F, -18.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(8, 58).addCuboid(-24.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(19, 43).addCuboid(-24.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(0, 49).addCuboid(-25.0F, -13.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(4, 58).addCuboid(-24.0F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(0, 58).addCuboid(-24.0F, -10.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(19, 29).addCuboid(-23.0F, -10.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(15, 29).addCuboid(-22.0F, -6.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(53, 57).addCuboid(-21.0F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(26, 42).addCuboid(-20.0F, -4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(4, 42).addCuboid(-18.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(49, 57).addCuboid(-16.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(9, 43).addCuboid(-21.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 24).addCuboid(-20.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(13, 18).addCuboid(-19.0F, 2.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(42, 12).addCuboid(-16.0F, 3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(41, 38).addCuboid(-14.0F, 2.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(43, 57).addCuboid(-12.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(39, 57).addCuboid(-11.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(0, 6).addCuboid(-15.0F, 5.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(39, 42).addCuboid(-11.0F, 4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(48, 32).addCuboid(-10.0F, 5.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 18).addCuboid(-9.0F, 6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 9).addCuboid(-8.0F, 5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 7).addCuboid(-7.0F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 2).addCuboid(-6.0F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 57).addCuboid(-5.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(33, 57).addCuboid(-4.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(48, 43).addCuboid(-9.0F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 38).addCuboid(-20.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(42, 28).addCuboid(-19.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(42, 14).addCuboid(-17.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 36).addCuboid(-15.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(57, 33).addCuboid(-14.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(42, 32).addCuboid(-22.0F, -9.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        preoutline = new ModelPart(this);
        preoutline.setPivot(2.0F, 1.0F, 0.0F);
        rightWing.addChild(preoutline);
        preoutline.setTextureOffset(48, 29).addCuboid(-4.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(48, 14).addCuboid(-10.0F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(48, 11).addCuboid(-9.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(46, 48).addCuboid(-11.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(42, 48).addCuboid(-13.0F, -4.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(36, 48).addCuboid(-16.0F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(11, 29).addCuboid(-14.0F, -6.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(0, 40).addCuboid(-12.0F, -5.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(0, 29).addCuboid(-15.0F, -8.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(29, 57).addCuboid(-5.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(56, 50).addCuboid(-10.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(56, 48).addCuboid(-8.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(56, 46).addCuboid(-6.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(25, 57).addCuboid(-6.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(19, 57).addCuboid(-7.0F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(15, 57).addCuboid(-8.0F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(11, 57).addCuboid(-9.0F, 5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(0, 4).addCuboid(-15.0F, 4.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(21, 41).addCuboid(-8.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(15, 41).addCuboid(-16.0F, 2.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(17, 5).addCuboid(-18.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(9, 41).addCuboid(-14.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(40, 9).addCuboid(-17.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(40, 2).addCuboid(-19.0F, -2.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(40, 0).addCuboid(-18.0F, -4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(40, 40).addCuboid(-20.0F, -5.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(56, 15).addCuboid(-20.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(46, 56).addCuboid(-21.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(36, 56).addCuboid(-22.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(22, 56).addCuboid(-23.0F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(8, 56).addCuboid(-24.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(4, 56).addCuboid(-24.0F, -16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(0, 56).addCuboid(-23.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 52).addCuboid(-20.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 44).addCuboid(-18.0F, -14.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 41).addCuboid(-14.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 30).addCuboid(-13.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 28).addCuboid(-12.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 22).addCuboid(-11.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 13).addCuboid(-10.0F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 55).addCuboid(-12.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(43, 55).addCuboid(-21.0F, -3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(39, 55).addCuboid(-20.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(17, 2).addCuboid(-19.0F, 1.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(51, 55).addCuboid(-16.0F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(55, 5).addCuboid(-8.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(30, 40).addCuboid(-22.0F, -10.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(4, 40).addCuboid(-22.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(39, 7).addCuboid(-20.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        preoutline.setTextureOffset(56, 0).addCuboid(-18.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        fill = new ModelPart(this);
        fill.setPivot(2.0F, 1.0F, 0.0F);
        rightWing.addChild(fill);
        fill.setTextureOffset(39, 4).addCuboid(-5.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(10, 3).addCuboid(-7.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(6, 17).addCuboid(-8.0F, -3.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(33, 55).addCuboid(-8.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(29, 55).addCuboid(-6.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(25, 55).addCuboid(-9.0F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(19, 55).addCuboid(-20.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(11, 55).addCuboid(-13.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 39).addCuboid(-17.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 37).addCuboid(-21.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(38, 23).addCuboid(-22.0F, -8.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(27, 15).addCuboid(-20.0F, -7.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(26, 12).addCuboid(-18.0F, -6.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(0, 17).addCuboid(-18.0F, -10.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(18, 26).addCuboid(-20.0F, -11.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(12, 26).addCuboid(-22.0F, -12.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 32).addCuboid(-19.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 26).addCuboid(-23.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(38, 21).addCuboid(-24.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(38, 17).addCuboid(-20.0F, -14.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(16, 8).addCuboid(-18.0F, -13.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(13, 16).addCuboid(-17.0F, -12.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(15, 12).addCuboid(-16.0F, -11.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(10, 0).addCuboid(-16.0F, -10.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(31, 38).addCuboid(-15.0F, -9.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(24, 20).addCuboid(-14.0F, -8.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(13, 48).addCuboid(-13.0F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 17).addCuboid(-12.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 11).addCuboid(-15.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(24, 6).addCuboid(-16.0F, -3.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(24, 3).addCuboid(-13.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 8).addCuboid(-15.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(47, 37).addCuboid(-14.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(47, 26).addCuboid(-8.0F, 2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 3).addCuboid(-7.0F, 2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(4, 38).addCuboid(-8.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(37, 35).addCuboid(-11.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(37, 33).addCuboid(-14.0F, 3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(24, 0).addCuboid(-12.0F, 2.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(28, 48).addCuboid(-24.0F, -18.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(21, 48).addCuboid(-23.0F, -17.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(17, 48).addCuboid(-22.0F, -16.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 20).addCuboid(-21.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(15, 55).addCuboid(-21.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(54, 35).addCuboid(-16.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(19, 39).addCuboid(-20.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(9, 39).addCuboid(-19.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(38, 26).addCuboid(-16.0F, 1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(28, 23).addCuboid(-10.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(27, 28).addCuboid(-10.0F, -4.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        fill.setTextureOffset(36, 39).addCuboid(-11.0F, -5.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void renderWings(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        leftWing.render(matrices, vertices, light, overlay);
        rightWing.render(matrices, vertices, light, overlay);
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(Main.MOD_ID, "textures/angel-pink.png");
    }

    @Override
    public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float yaw = 0.3F;
        float pitch = 0F;
        float pivotX = 3F;
        float pivotY = 1F;
        float pivotZ = 2F;
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
                pivotY = 5F;
            }
        }
        else if(entity.isInSneakingPose()) {
            pitch = (float) Math.toRadians(20);
            pivotY = 5F;
        } else {
            pitch = 0;
        }

        yaw += (MathHelper.cos(entity.age * a) * b);

        this.rightWing.pitch = pitch;
        this.rightWing.yaw = yaw;
        this.rightWing.pivotX = -pivotX;
        this.rightWing.pivotY = pivotY;
        this.rightWing.pivotZ = pivotZ;

        this.leftWing.pivotX = -this.rightWing.pivotX;
        this.leftWing.pivotY = this.rightWing.pivotY;
        this.leftWing.pivotZ = this.rightWing.pivotZ;
        this.leftWing.yaw = -this.rightWing.yaw;
        this.leftWing.pitch = this.rightWing.pitch;
    }
}
