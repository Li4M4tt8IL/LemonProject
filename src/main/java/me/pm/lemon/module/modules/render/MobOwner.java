package me.pm.lemon.module.modules.render;

import com.google.gson.JsonParser;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.EntityRenderEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.text.Text;
import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MobOwner extends Module {
    public MobOwner(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        super(name, category, description, keyCode, color, settings);
    }

    private final Map<String, String> cachedUUIDs = new HashMap<>();

    public String getNameFromUUID(String uuid) {
        uuid = uuid.replace("-", "");
        for (Map.Entry<String, String> entries : cachedUUIDs.entrySet()) {
            if (entries.getKey().equalsIgnoreCase(uuid)) {
                return entries.getValue();
            }
        }
        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        System.out.println("Querying " + url + " for owner ID");
        try
        {
            final String nameJson = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            if (nameJson != null && nameJson.length() > 0)
            {
                JsonParser parser = new JsonParser();
                String name = parser.parse(nameJson).getAsJsonArray().get(parser.parse(nameJson).getAsJsonArray().size() - 1)
                        .getAsJsonObject().get("name").toString();
                cachedUUIDs.put(uuid, name);
                return name;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Run this again to reduce the amount of requests made to the Mojang API */
        for (Map.Entry<String, String> entries : cachedUUIDs.entrySet()) {
            if (entries.getKey().equalsIgnoreCase(uuid)) {
                return entries.getValue();
            }
        }
        cachedUUIDs.put(uuid, null);
        return null;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        cachedUUIDs.clear();
    }

    @EventTarget
    public void onLivingRender(EntityRenderEvent.Render entityRenderEvent) {
        if(mc.world == null){
            return;
        }
        for (final Entity e : mc.world.getEntities()) {
            if (e instanceof TameableEntity) {
                if (((TameableEntity) e).isTamed() && ((TameableEntity) e).getOwnerUuid() != null) {
                    String ownername = getNameFromUUID(Objects.requireNonNull(((TameableEntity) e).getOwnerUuid()).toString());
                    if (ownername != null) {
                        ownername = ownername.replace("\"", "");
                        String text = "Owner: " + ownername;
                        e.setCustomNameVisible(true);
                        e.setCustomName(Text.of(text));
                    }
                }
            }
            if (e instanceof HorseBaseEntity) {
                if (((HorseBaseEntity) e).isTame() && ((HorseBaseEntity) e).getOwnerUuid() != null) {
                    String ownername = getNameFromUUID(Objects.requireNonNull(((HorseBaseEntity) e).getOwnerUuid()).toString());
                    if (ownername != null) {
                        ownername = ownername.replace("\"", "");
                        String text = "Owner: " + ownername;
                        e.setCustomNameVisible(true);
                        e.setCustomName(Text.of(text));
                    }
                }
            }
        }
    }
}
