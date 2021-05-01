package me.pm.lemon.module;

import me.pm.lemon.event.EventManager;
import me.pm.lemon.friends.FriendManager;
import me.pm.lemon.gui.testScreen.settings.Setting;
import me.pm.lemon.utils.FileManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Module {
    private String name, displayName;
    private Category category;
    private String description;
    private int keyCode;
    private boolean toggled;
    private Color color;

    private List<Setting> settings = new ArrayList<>();

    public static FileManager fileManager;
    public static FriendManager friendManager;

    protected static MinecraftClient mc = MinecraftClient.getInstance();
    protected static TextRenderer font = mc.textRenderer;

    public void toggle() {
        this.toggled = !this.toggled;
        if(this.toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void setup() {}

    public void onEnable() {
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
    }

    public Module(String name, Category category, String description, int keyCode, Color color, Setting... settings) {
        this.name = name;
        this.displayName = name;
        this.category = category;
        this.description = description;
        this.keyCode = keyCode;
        this.color = color;
        this.settings = Arrays.asList(settings);

        setup();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public void setToggled(boolean toggled) {
        if(toggled && !this.isToggled()) {
            toggle();
        } else if(!toggled && this.isToggled()) {
            toggle();
        }
    }

    public Color getColor() {
        return this.color;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public boolean isCategory(Category s) {
        if (s == category) {
            return true;
        }
        return false;
    }

    public static final ClientPlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }

    public static FriendManager getFriendManager()
    {
        if(friendManager == null) friendManager = new FriendManager();

        return friendManager;
    }

    public static FileManager getFileManager()
    {
        if(fileManager == null) fileManager = new FileManager();

        return fileManager;
    }

    public static Module getModule(Class<? extends Module> clazz) {
        for (Module m : ModuleManager.getModules()) {
            if (m.getClass() == clazz) {
                return m;
            }
        }
        return null;
    }

    public static List<Module> getModulesInCat(Category cat) {
        return ModuleManager.modules.stream().filter(m -> m.getCategory().equals(cat)).collect(Collectors.toList());
    }

    public List<me.pm.lemon.gui.testScreen.settings.Setting> getSettings() {
        return settings;
    }

    public Setting getSetting(int s) {
        return settings.get(s);
    }

    public static Module getModuleByName(String name) {
        for (Module m : ModuleManager.getModules()) {
            if (name.equalsIgnoreCase(m.getName()))
                return m;
        }
        return null;
    }

    public static Module getModuleByDisplayName(String name) {
        for (Module m : ModuleManager.getModules()) {
            if (name.equalsIgnoreCase(m.getDisplayName()))
                return m;
        }
        return null;
    }

}
