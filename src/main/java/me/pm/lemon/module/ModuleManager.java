package me.pm.lemon.module;

import me.pm.lemon.Main;
import me.pm.lemon.command.Command;
import me.pm.lemon.event.EventManager;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.*;
import me.pm.lemon.gui.hud.HUDManager;
import me.pm.lemon.module.modules.chat.BindCommands;
import me.pm.lemon.module.modules.chat.NameMention;
import me.pm.lemon.module.modules.combat.*;
import me.pm.lemon.module.modules.movement.Jesus;
import me.pm.lemon.module.modules.experimental.ServerCrash;
import me.pm.lemon.module.modules.experimental.TestGui;
import me.pm.lemon.module.modules.exploits.*;
import me.pm.lemon.module.modules.misc.AntiChunkBan;
import me.pm.lemon.module.modules.gui.*;
import me.pm.lemon.module.modules.hud.*;
import me.pm.lemon.module.modules.misc.AntiLevitation;
import me.pm.lemon.module.modules.misc.MiddleClickFriend;
import me.pm.lemon.module.modules.movement.*;
import me.pm.lemon.module.modules.player.*;
import me.pm.lemon.module.modules.render.*;
import me.pm.lemon.module.modules.world.BedNuker;
import me.pm.lemon.module.modules.world.Nuker;
import me.pm.lemon.module.modules.world.Scaffold;
import me.pm.lemon.module.modules.world.Xray;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ModuleManager {
    public static ArrayList<Module> modules;
    private int delaySeconds = 500;
    private int tickCounter = 0;
    private boolean loaded = false;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public void init() {
        modules = new ArrayList<Module>();
        EventManager.register(this);

        // TODO:
        /* COMBAT */
        registerModule(new AntiFriendHit("Anti Friend Hit", Category.COMBAT, "Nie bije przyjaciol.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));
        registerModule(new AntiVillagerHit("Anti Villager Hit", Category.COMBAT, "Nie bije wiesniakow.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));
        registerModule(new AntiZombiePigmanHit("Anti Pigman Hit", Category.COMBAT, "Nie bije pigmanow.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));
        registerModule(new AutoTotem("Auto Totem", Category.COMBAT, "Dobiera Ci totem do 2 reki.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));
        registerModule(new Criticals("Criticals", Category.COMBAT, "Does criticals with all hits", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));
        registerModule(new CrystalAura("Crystal Aura", Category.COMBAT, "Auto Crystal", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));
        registerModule(new KillAura("Kill Aura", Category.COMBAT, "Automatycznie zabija obiekt", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));
        registerModule(new TriggerBot("Trigger Bot", Category.COMBAT, "Atakuje obiekt po najechaniu na niego.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT));

        /* RENDER */
        registerModule(new OutlineBlock("Block Outline", Category.RENDER, "Rysuje szescian do okola bloku.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new Chams("Chams", Category.RENDER, "Renderuje byt przez sciane.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new OG("OG", Category.RENDER, "OG DLA POVOO.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new EntityESP("ESP", Category.RENDER, "Rysuje szescian do okola obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new Fullbright("Fullbright", Category.RENDER, "Zwieksza gamme.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new MobOwner("Mob Owner", Category.RENDER, "Rysuje nazwe wlasciciela obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new Nametags("Nametags", Category.RENDER, "Rysuje nazwe obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new StorageESP("Storage ESP", Category.RENDER, "Rysuje szescian do okola obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new Trajectories("Trajectories", Category.RENDER, "Rysuje linie gdzie dany obiekt leci.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
        registerModule(new Tracers("Tracers", Category.RENDER, "Rysuje linie do obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));
//        registerModule(new Zoom("Zoom", Category.RENDER, "Optifine zoom.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER));

        /* MOVEMENT */
        registerModule(new Blink("Blink", Category.MOVEMENT, "Taki fake lag.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new BunnyHop("Bunnyhop", Category.MOVEMENT, "Skacze lol.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new ElytraFly("Elytra Fly", Category.MOVEMENT, "Lata elytra.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new Fly("Fly", Category.MOVEMENT, "Pozwala latac.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new Jesus("Jesus", Category.MOVEMENT, "Mesjasz", -1, Colors.MOVEMENT));
        registerModule(new NoFall("No Fall", Category.MOVEMENT, "Nie dostajesz obrazen od upadku.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new NoSlowdown("No Slowdown", Category.MOVEMENT, "Nie dostajesz obrazen od upadku.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new Sprint("Sprint", Category.MOVEMENT, "Auto sprint.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new Step("Step", Category.MOVEMENT, "Lepszy autojump.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));
        registerModule(new Sneak("Toggle Sneak", Category.MOVEMENT, "Kuca non stop.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT));

        /* GUI */
        registerModule(new BindCommandEditor("Bind Cmd Editor", Category.GUI, "Allows you to edit the bind commands.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI));
        registerModule(new Colours());
        registerModule(new Hud("Hud", Category.GUI, "User interface.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI));
        registerModule(new HudEditor("Hud Editor", Category.GUI, "Allows you to edit the ui position.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI));
        registerModule(new ClickGui("Click Gui", Category.GUI, "Displays the new ClickGUI.", GLFW.GLFW_KEY_RIGHT_SHIFT, Colors.GUI));
        registerModule(new Panic("Panic!", Category.GUI, "Wylacza wszystkie funkcje moda.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI));
        registerModule(new ResetGuiPos("Reset Gui Pos", Category.GUI, "Resetuje pozycje okien.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI));

        /* PLAYER */
        registerModule(new AutoFish("Auto Fish", Category.PLAYER, "Lowi rypki.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER));
        registerModule(new AutoMine("Auto Mine", Category.PLAYER, "Kopiesz blok który masz przed sobą.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER));
        registerModule(new AutoRespawn("Auto Respawn", Category.PLAYER, "Odradza gracza po smierci.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER));
        registerModule(new AutoWaterBucket("Auto Water Bucket", Category.PLAYER, "Mlg water bucket.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER));
        registerModule(new DeathCoords("Death Coords", Category.PLAYER, "Wysyla kordy smierci na chacie.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER));
        registerModule(new Freecam("Freecam", Category.PLAYER, "Umozliwia wyjscie z twojego ciala.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER));
        registerModule(new RecordingMode("Recording Mode", Category.PLAYER, "Tryb nagrywania.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER));

        /* WORLD */
        registerModule(new BedNuker("Bed Nuker", Category.WORLD, "Niszczy lozka dookola ciebie.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD));
        registerModule(new Nuker("Nuker", Category.WORLD, "Kopie bloki do okola ciebie.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD));
        registerModule(new Scaffold("Scaffold", Category.WORLD, "Kladzie bloki pod toba.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD));
        registerModule(new Xray("Xray", Category.WORLD, "Laduje wybrane bloki do okola ciebie.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD));

        /* MISC */
        registerModule(new AntiChunkBan("Anti Chunk Ban", Category.MISC, "Taki antypacketkick.", GLFW.GLFW_KEY_UNKNOWN, Colors.MISC));
        registerModule(new AntiLevitation("Anti Levitation", Category.MISC, "Niszczy te magiczne kule od shulkerow.", GLFW.GLFW_KEY_UNKNOWN, Colors.MISC));
        registerModule(new MiddleClickFriend("MCF", Category.MISC, "Dodaje gracza do znajomych.", GLFW.GLFW_KEY_UNKNOWN, Colors.MISC));

        /* CHAT */
        registerModule(new BindCommands("Bind Commands", Category.CHAT, "Zarzadzasz komendami.", GLFW.GLFW_KEY_UNKNOWN, Colors.CHAT));
        registerModule(new NameMention("Name Mention", Category.CHAT, "Gra dzwiek jak ktos napisze twoj nick na chacie.", GLFW.GLFW_KEY_UNKNOWN, Colors.CHAT));

        /* EXPLOITS */
        registerModule(new BlockSpeed("Block Speed", Category.EXPLOITS, "Biegasz szybciej na niektórych blokach.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS));
        registerModule(new BookCrash("Book Crash", Category.EXPLOITS, "Wywala serwer.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS));
        registerModule(new FastUse("Fast Use", Category.EXPLOITS, "Szybko.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS));
        registerModule(new OffhandCrash("Offhand Crash", Category.EXPLOITS, "Wywala serwer.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS));
        registerModule(new PlayerCrash("Player Crash", Category.EXPLOITS, "Wywala serwer.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS));
        registerModule(new SpeedMine("Speed Mine", Category.EXPLOITS, "Szybciej niszczysz.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS));

        /* EXPERIMENTAL */
        registerModule(new ServerCrash());
        registerModule(new TestGui("TestGui", Category.EXPERIMENTAL, "test gui", -1, Colors.EXPERIMENTAL));

        /* CLIENT */
//        registerModule(new ForcedStuff());

    }

    public static void registerModule(Module module) {
        modules.add(module);
    }

    public static void registerHud(HUDManager api) {
        api.register(new WelcomeMessage());
        api.register(new ModuleList());
        api.register(new InfoClientName());
        api.register(new InfoClientVersion());
        api.register(new InfoMinecraftVersion());
        api.register(new InfoPlayerName());
        api.register(new InfoPlayerPing());
        api.register(new InfoFPS());
        api.register(new InfoServerIP());
        api.register(new WorldInfoCoordinates());
        api.register(new WorldInfoFacing());
        api.register(new Armor());
        api.register(new EntityPlayerList());
        api.register(new Keystrokes());
        api.register(new Inventory());
        api.register(new PerspectiveMod());
        EventManager.register(new PerspectiveMod());
    }

    @EventTarget
    public void onKey(KeyPressEvent event) {
        if(!Main.isPanic && !Main.isReloading && !Main.debugMode) {
            Main.checkIfValid();
        }
        for(Module module : ModuleManager.getModules()) {
            if(module.getKeyCode() == event.getKey()) {
                if(!Main.isPanic) {
                    module.toggle();
                }
            }
        }
        try {
            if(GLFW.glfwGetKeyName(event.getKey(), GLFW.glfwGetKeyScancode(event.getKey())).equalsIgnoreCase(Command.PREFIX)) {
                mc.openScreen(new ChatScreen(""));
            }
        } catch (NullPointerException e) {

        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        assert mc.player != null;
        if(!loaded) {
            if(mc.player.getName().getString().equals(Main.ClientInfo.clientCreators) || Main.debugMode) {

            }
            loaded = true;
        }
        if(tickCounter > (delaySeconds * 20)) {
            Thread thread = new Thread(Main::reloadAuthUsers);
            thread.start();
            tickCounter = 0;
        }
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

}
