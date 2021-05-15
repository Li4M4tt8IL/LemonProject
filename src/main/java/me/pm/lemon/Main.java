package me.pm.lemon;

import com.google.common.collect.Lists;
import me.pm.lemon.bindcommand.BindCommandManager;
import me.pm.lemon.command.CommandManager;
import me.pm.lemon.event.EventManager;
import me.pm.lemon.friends.FriendManager;
import me.pm.lemon.gui.bindcommandGui.BindCommandScreen;
import me.pm.lemon.gui.hud.HUDManager;
import me.pm.lemon.gui.clickGui.ClickGuiScreen;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;
import me.pm.lemon.module.modules.gui.BindCommandEditor;
import me.pm.lemon.module.modules.gui.HudEditor;
import me.pm.lemon.module.modules.gui.ClickGui;
import me.pm.lemon.module.modules.gui.Panic;
import me.pm.lemon.module.modules.movement.Blink;
import me.pm.lemon.module.modules.player.RecordingMode;
import me.pm.lemon.utils.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Objects;


public class Main implements ModInitializer {
    public static class ClientInfo {
        public static String clientName = "Lemon";
        public static String clientVersion = "1.2.2";
        public static String clientCreators = "f9486cc8-6743-4140-9145-4307e86de58a";
        public static String minecraftVersion = MinecraftClient.getInstance().getGame().getVersion().getName();
        public static List<String> clientAuth = Lists.newArrayList();
        public static List<String> wingsUUID = Lists.newArrayList();
    }

    public static boolean loggedIn = false;

    public static class ClientScreens {
//        public static ClickGuiScreen clickGuiScreen;
        public static BindCommandScreen bindClickGuiScreen;
        public static HUDManager hudManager;
        public static ClickGuiScreen testScreen;
    }

    public static class ClientKeys {
        public static KeyBinding zoomKey;
        public static KeyBinding freelookKey;
    }

    public static final String MOD_ID = "lemon";

    public static boolean isPanic = false;
    public static boolean legitMode = false;
    public static boolean isReloading = false;
    public static boolean debugMode = false;

    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static BindCommandManager bindCommandManager;

    @Override
    public void onInitialize() {
        ClientInfo.wingsUUID.add(ClientInfo.clientCreators);
        ClientInfo.wingsUUID.add("04731cb9-5c8a-4e40-a827-8efca7634129");
        ClientInfo.wingsUUID.add("de987d75-b315-4920-ab93-ca148b19115d");

        ClientKeys.zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("Zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "Misc"));
        ClientKeys.freelookKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("Free Look", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "Misc"));
        FileHelper.init();

        bindCommandManager = new BindCommandManager();
        bindCommandManager.init();

        moduleManager = new ModuleManager();
        moduleManager.init();
        Module.fileManager = new FileManager();

//        UpdateChecker.checkOwner();

        commandManager = new CommandManager();
        Module.friendManager = new FriendManager();

        FileManager.readModules();
        FileManager.setLegit();
//        ClientScreens.clickGuiScreen = new ClickGuiScreen();
//        ClientScreens.clickGuiScreen.initWindows();

        ClientScreens.bindClickGuiScreen = new BindCommandScreen();

        if(FileHelper.first_run) {
//            ClientScreens.clickGuiScreen.resetGui();
//            Module.getModule(ForcedStuff.class).toggle();
        } else {
            FileManager.readPrefix();
        }

//        FileManager.readClickGui();

        ClientScreens.hudManager = HUDManager.getInstance();

        ClientScreens.testScreen = new ClickGuiScreen();

        EventManager.register(ClientScreens.hudManager);
        ModuleManager.registerHud(ClientScreens.hudManager);

        addToAuth();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    private void shutdown() {
        if(Objects.requireNonNull(Module.getModule(RecordingMode.class)).isToggled()) {
            Objects.requireNonNull(Module.getModule(RecordingMode.class)).toggle();
        }
        if(Objects.requireNonNull(Module.getModule(Blink.class)).isToggled()) {
            Objects.requireNonNull(Module.getModule(Blink.class)).toggle();
        }
        if(Objects.requireNonNull(Module.getModule(HudEditor.class)).isToggled()) {
            Objects.requireNonNull(Module.getModule(HudEditor.class)).toggle();
        }
        if(Objects.requireNonNull(Module.getModule(ClickGui.class)).isToggled()) {
            Objects.requireNonNull(Module.getModule(ClickGui.class)).toggle();
        }
        if(Objects.requireNonNull(Module.getModule(BindCommandEditor.class)).isToggled()) {
            Objects.requireNonNull(Module.getModule(BindCommandEditor.class)).toggle();
        }
        FileManager.updateFriendList();
        FileManager.updateBindCommands();
//        FileManager.saveAlts();
        FileManager.saveModules();
        FileManager.saveAlts();
//        FileManager.saveClickGui();
//        ApiManager.getInstance().deletePlayerUUID(ApiManager.getInstance().uuid);
    }

    public static void addToAuth() {
        FileManager.loadAuthenticatedUsers();
    }

    public static void reloadAuthUsers() {
        isReloading = true;
        ClientInfo.clientAuth.clear();
        addToAuth();
        isReloading = false;
    }


    public static void checkIfValid() {
        assert MinecraftClient.getInstance().player != null;
        if(!ClientInfo.clientAuth.contains(MinecraftClient.getInstance().player.getName().getString())) {
            LemonLogger.errorMessage("Auth Error","You are not permitted to use this mod");
            Objects.requireNonNull(Module.getModule(Panic.class)).toggle();
        }
    }
}