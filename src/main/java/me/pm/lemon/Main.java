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
import me.pm.lemon.module.modules.client.ForcedStuff;
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

import java.util.*;


public class Main implements ModInitializer {
    public static class ClientInfo {
        public static String clientName = "Lemon";
        public static String clientVersion = "2.0.0";
        public static String clientCreators = "f9486cc86743414091454307e86de58a";
        public static String minecraftVersion = MinecraftClient.getInstance().getGame().getVersion().getName();
        public static List<String> clientAuth = Lists.newArrayList();
        public static Map<String, String> logins = new HashMap<>();
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

    private static List<String> names = new ArrayList<>();
    private static List<String> tophats = new ArrayList<>();
    private static List<String> christmasHats = new ArrayList<>();
    private static List<String> lightEars = new ArrayList<>();
    private static List<String> darkEars = new ArrayList<>();
    private static List<String> orangeFoxEars = new ArrayList<>();
    private static List<String> whiteFoxEars = new ArrayList<>();
    private static List<String> demonWings = new ArrayList<>();
    private static List<String> dragonWings = new ArrayList<>();
    private static List<String> angelWings = new ArrayList<>();

    public static List<String> getNames() {
        return names;
    }

    public static List<String> getTopHats() {
        return tophats;
    }

    public static List<String> getChristmasHats() {
        return christmasHats;
    }

    public static List<String> getLightEars() {
        return lightEars;
    }

    public static List<String> getDarkEars() {
        return darkEars;
    }

    public static List<String> getOrangeFoxEars() {
        return orangeFoxEars;
    }

    public static List<String> getWhiteFoxEars() {
        return whiteFoxEars;
    }

    public static List<String> getDemonWings() {
        return demonWings;
    }

    public static List<String> getAngelWings() {
        return angelWings;
    }

    public static List<String> getDragonWings() {
        return dragonWings;
    }

    public static void reloadApiStats() {
//        names = ApiV2.getInstance().getNames();
//        demonWings = ApiV2.getInstance().getDemonWings();
//        dragonWings = ApiV2.getInstance().getDragonWings();
//        angelWings = ApiV2.getInstance().getAngelWings();
//        tophats = ApiV2.getInstance().getTophats();
//        lightEars = ApiV2.getInstance().getLightEars();
//        darkEars = ApiV2.getInstance().getDarkEars();
//        orangeFoxEars = ApiV2.getInstance().getOrangeFoxEars();
//        whiteFoxEars = ApiV2.getInstance().getWhiteFoxEars();
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

        ClientScreens.bindClickGuiScreen = new BindCommandScreen();

        if(FileHelper.first_run) {

        } else {
            FileManager.readPrefix();
        }

        ClientScreens.hudManager = HUDManager.getInstance();

        ClientScreens.testScreen = new ClickGuiScreen();

        EventManager.register(ClientScreens.hudManager);
        ModuleManager.registerHud(ClientScreens.hudManager);

//        addToAuth();
//        reloadApiStats();
//        Module.getModule(ForcedStuff.class).toggle();

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
        FileManager.saveModules();
        FileManager.saveAlts();
//        ApiV2.getInstance().updatePlayer(ApiV2.getInstance().name, false);
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