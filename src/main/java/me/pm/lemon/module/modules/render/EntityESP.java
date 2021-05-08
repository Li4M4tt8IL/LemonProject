package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.WorldRenderEntityEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.EntityUtil;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import me.pm.lemon.utils.generalUtils.Util;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;

import java.awt.*;
import java.util.ArrayList;

public class EntityESP extends Module {
    public EntityESP(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Players", true).withDesc("Show Players").withChildren(
                    new SettingToggle("Rainbow Players", false),
                        new SettingColor("Player Color", 1f, 0.3f, 0.3f, false).withDesc("Color for players"),
                    new SettingToggle("Rainbow Friends", false),
                        new SettingColor("Friend Color", 0f, 1f, 1f, false).withDesc("Color for friends")), /* 1 */
                new SettingToggle("Mobs", false).withDesc("Show Mobs").withChildren(
                        new SettingToggle("Rainbow", false),
                        new SettingColor("Color", 0.5f, 0.1f, 0.5f, false).withDesc("Color for mobs")), /* 0 */
                new SettingToggle("Animals", false).withDesc("Show Animals").withChildren( /* 2 */
                        new SettingToggle("Rainbow", false),
                        new SettingColor("Color", 0.3f, 1f, 0.3f, false).withDesc("Color for animals")), /* 0 */
                new SettingToggle("Items", true).withDesc("Show Items").withChildren( /* 3 */
                        new SettingToggle("Rainbow", false),
                        new SettingColor("Color", 85, 85, 255, false).withDesc("Color for items")), /* 0 */
                new SettingToggle("Crystals", true).withDesc("Show End Crystals").withChildren( /* 4 */
                        new SettingToggle("Rainbow", false),
                        new SettingColor("Color", 1f, 0.2f, 1f, false).withDesc("Color for crystals")), /* 0 */
                new SettingToggle("Vehicles", false).withDesc("Show Vehicles").withChildren( /* 5 */
                        new SettingToggle("Rainbow", false),
                        new SettingColor("Color", 0.6f, 0.6f, 0.6f, false).withDesc("Color for vehicles (minecarts/boats)")), /* 0 */
                new SettingMode("Mode", "Box", "Outline", "2D")); /* 6 */
    }

    private ArrayList<Entity> glowingEntites = new ArrayList<>();

    @EventTarget
    public void onWorldEntityRender(WorldRenderEntityEvent event) {
        if(getSetting(6).asMode().mode == 0) {
            float[] col = null;
            this.setDisplayName("ESP \2477Box");
            if(event.entity instanceof PlayerEntity && getSetting(0).asToggle().state) {
                if(getFriendManager().isFriend(event.entity.getName().getString())) {
                    event.vertex = getOutline(event.buffers, 0.1F, 7.0F, 1.0F);
                    if(getSetting(0).asToggle().getChild(2).asToggle().state) {
                        col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                    } else {
                        col = getSetting(0).asToggle().getChild(3).asColor().getRGBFloat();
                    }
                } else {
                    event.vertex = getOutline(event.buffers, 1.0F, 0.0F, 0.0F);
                    if(getSetting(0).asToggle().getChild(0).asToggle().state) {
                        col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                    } else {
                        col = getSetting(0).asToggle().getChild(1).asColor().getRGBFloat();
                    }
                }
            } else if(event.entity instanceof Monster && getSetting(1).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.5f, 0.1f, 0.5f);
                if(getSetting(1).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(1).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if(EntityUtil.isAnimal(event.entity) && getSetting(2).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.3f, 1f, 0.3f);
                if(getSetting(2).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(2).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if(event.entity instanceof ItemEntity && getSetting(3).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.3f, 1f, 0.3f);
                if(getSetting(3).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(3).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if(event.entity instanceof EndCrystalEntity && getSetting(4).asToggle().state) {
                event.vertex = getOutline(event.buffers, 1f, 0.2f, 1f);
                if(getSetting(4).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(4).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if((event.entity instanceof BoatEntity || event.entity instanceof AbstractMinecartEntity) && getSetting(5).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.6f, 0.6f, 0.6f);
                if(getSetting(5).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(5).asToggle().getChild(1).asColor().getRGBFloat();
                }
            }
            if(col != null) {
                RenderUtils.drawOutlineBox(event.entity.getBoundingBox(), col[0], col[1], col[2], 1f);
                RenderUtils.drawFilledBox(event.entity.getBoundingBox(), col[0], col[1], col[2], 0.7f);
            }
        } else if(getSetting(6).asMode().mode == 1) {
            float[] col = null;
            this.setDisplayName("ESP \2477Outline");
            if(event.entity instanceof PlayerEntity && getSetting(0).asToggle().state) {
                if(getFriendManager().isFriend(event.entity.getName().getString())) {
                    event.vertex = getOutline(event.buffers, 0.1F, 7.0F, 1.0F);
                    if(getSetting(0).asToggle().getChild(2).asToggle().state) {
                        col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                    } else {
                        col = getSetting(0).asToggle().getChild(3).asColor().getRGBFloat();
                    }
                } else {
                    event.vertex = getOutline(event.buffers, 1.0F, 0.0F, 0.0F);
                    if(getSetting(0).asToggle().getChild(0).asToggle().state) {
                        col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                    } else {
                        col = getSetting(0).asToggle().getChild(1).asColor().getRGBFloat();
                    }
                }
            } else if(event.entity instanceof Monster && getSetting(1).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.5f, 0.1f, 0.5f);
                if(getSetting(1).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(1).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if(EntityUtil.isAnimal(event.entity) && getSetting(2).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.3f, 1f, 0.3f);
                if(getSetting(2).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(2).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if(event.entity instanceof ItemEntity && getSetting(3).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.3f, 1f, 0.3f);
                if(getSetting(3).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(3).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if(event.entity instanceof EndCrystalEntity && getSetting(4).asToggle().state) {
                event.vertex = getOutline(event.buffers, 1f, 0.2f, 1f);
                if(getSetting(4).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(4).asToggle().getChild(1).asColor().getRGBFloat();
                }
            } else if((event.entity instanceof BoatEntity || event.entity instanceof AbstractMinecartEntity) && getSetting(5).asToggle().state) {
                event.vertex = getOutline(event.buffers, 0.6f, 0.6f, 0.6f);
                if(getSetting(5).asToggle().getChild(0).asToggle().state) {
                    col = Util.intToFloat(Util.getRainbow(1.0f, 1.0f, 20, 0));
                } else {
                    col = getSetting(5).asToggle().getChild(1).asColor().getRGBFloat();
                }
            }
            if(col != null) {
                RenderUtils.drawOutlineBox(event.entity.getBoundingBox(), col[0], col[1], col[2], 1f);
            }
        } else if(getSetting(6).asMode().mode == 2) {
            float[] col = null;
            this.setDisplayName("ESP \2477Test");
//            glowingEntites.add(event.entity);
//            event.entity.setGlowing(true);
//            System.out.println(box.minX + " " + box.minY + " " + box.maxX + " " + box.maxY);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


    private VertexConsumerProvider getOutline(BufferBuilderStorage buffers, float r, float g, float b) {
        OutlineVertexConsumerProvider ovsp = buffers.getOutlineVertexConsumers();
        ovsp.setColor((int) (r * 255), (int) (g * 255), (int) (b * 255), 255);
        return ovsp;
    }

}
