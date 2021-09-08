package me.pm.lemon.module;

import me.pm.lemon.Main;
import me.pm.lemon.command.Command;
import me.pm.lemon.event.EventManager;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.*;
import me.pm.lemon.gui.hud.HUDManager;
import me.pm.lemon.module.modules.chat.BindCommands;
import me.pm.lemon.module.modules.chat.NameMention;
import me.pm.lemon.module.modules.client.ForcedStuff;
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
import me.pm.lemon.utils.ApiV2;
import me.pm.lemon.utils.Timer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ModuleManager {
    public static ArrayList<Module> modules;
    public static ArrayList<Module> legitModules;
    public static ArrayList<Module> nonLegitModules;
    private boolean loaded = false;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public static class Instances {
        public static AntiFriendHit antiFriendHit = null;
        public static AntiVillagerHit antiVillagerHit = null;
        public static AntiZombiePigmanHit antiZombiePigmanHit = null;
        public static AutoTotem autoTotem = null;
        public static Criticals criticals = null;
        public static CrystalAura crystalAura = null;
        public static KillAura killAura = null;
        public static TriggerBot triggerBot = null;
        public static Reach reach = null;

        public static OutlineBlock outlineBlock = null;
        public static Chams chams = null;
        public static OG og = null;
        public static EntityESP entityESP = null;
        public static Fullbright fullbright = null;
        public static MobOwner mobOwner = null;
        public static Nametags nametags = null;
        public static StorageESP storageESP = null;
        public static Trajectories trajectories = null;
        public static Tracers tracers = null;

        public static Blink blink = null;
        public static BunnyHop bunnyHop = null;
        public static ElytraFly elytraFly = null;
        public static Fly fly = null;
        public static Jesus jesus = null;
        public static NoFall noFall = null;
        public static NoSlowdown noSlowdown = null;
        public static Sprint sprint = null;
        public static Step step = null;
        public static Sneak sneak = null;

        public static BindCommandEditor bindCommandEditor = null;
        public static Colours colours = null;
        public static Hud hud = null;
        public static HudEditor hudEditor = null;
        public static ClickGui clickGui = null;
        public static Panic panic = null;

        public static AutoFish autoFish = null;
        public static AutoMine autoMine = null;
        public static AutoRespawn autoRespawn = null;
        public static AutoWaterBucket autoWaterBucket = null;
        public static DeathCoords deathCoords = null;
        public static Freecam freecam = null;
        public static RecordingMode recordingMode = null;

        public static BedNuker bedNuker = null;
        public static Nuker nuker = null;
        public static Scaffold scaffold = null;
        public static Xray xray = null;

        public static AntiChunkBan antiChunkBan = null;
        public static AntiLevitation antiLevitation = null;
        public static MiddleClickFriend middleClickFriend = null;

        public static BindCommands bindCommands = null;
        public static NameMention nameMention = null;

        public static BlockSpeed blockSpeed = null;
        public static BookCrash bookCrash = null;
        public static FastUse fastUse = null;
        public static OffhandCrash offhandCrash = null;
        public static PlayerCrash playerCrash = null;
        public static SpeedMine speedMine = null;
    }

    public void initCombat() {
        Instances.antiFriendHit =
                new AntiFriendHit("Anti Friend Hit", Category.COMBAT, "Nie bije przyjaciol.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.antiVillagerHit =
                new AntiVillagerHit("Anti Villager Hit", Category.COMBAT, "Nie bije wiesniakow.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.antiZombiePigmanHit =
                new AntiZombiePigmanHit("Anti Pigman Hit", Category.COMBAT, "Nie bije pigmanow.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.autoTotem =
                new AutoTotem("Auto Totem", Category.COMBAT, "Dobiera Ci totem do 2 reki.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.criticals =
                new Criticals("Criticals", Category.COMBAT, "Does criticals with all hits", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.crystalAura =
                new CrystalAura("Crystal Aura", Category.COMBAT, "Auto Crystal", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.killAura =
                new KillAura("Kill Aura", Category.COMBAT, "Automatycznie zabija obiekt", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.triggerBot =
                new TriggerBot("Trigger Bot", Category.COMBAT, "Atakuje obiekt po najechaniu na niego.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);
        Instances.reach =
                new Reach("Reach", Category.COMBAT, "Powieksza reacha.", GLFW.GLFW_KEY_UNKNOWN, Colors.COMBAT);

        registerModule(Instances.antiFriendHit, true);
        registerModule(Instances.antiVillagerHit, true);
        registerModule(Instances.antiZombiePigmanHit, true);
        registerModule(Instances.autoTotem, false);
        registerModule(Instances.criticals, false);
        registerModule(Instances.crystalAura, false);
        registerModule(Instances.killAura, false);
        registerModule(Instances.triggerBot, false);
        registerModule(Instances.reach, false);
    }

    public void initRender() {
        Instances.outlineBlock =
                new OutlineBlock("Block Outline", Category.RENDER, "Rysuje szescian do okola bloku.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.chams =
                new Chams("Chams", Category.RENDER, "Renderuje byt przez sciane.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.og =
                new OG("OG", Category.RENDER, "OG DLA POVOO.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.entityESP =
                new EntityESP("ESP", Category.RENDER, "Rysuje szescian do okola obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.fullbright =
                new Fullbright("Fullbright", Category.RENDER, "Zwieksza gamme.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.mobOwner =
                new MobOwner("Mob Owner", Category.RENDER, "Rysuje nazwe wlasciciela obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.nametags =
                new Nametags("Nametags", Category.RENDER, "Rysuje nazwe obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.storageESP =
                new StorageESP("Storage ESP", Category.RENDER, "Rysuje szescian do okola obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.trajectories =
                new Trajectories("Trajectories", Category.RENDER, "Rysuje linie gdzie dany obiekt leci.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);
        Instances.tracers =
                new Tracers("Tracers", Category.RENDER, "Rysuje linie do obiektu.", GLFW.GLFW_KEY_UNKNOWN, Colors.RENDER);

        registerModule(Instances.outlineBlock, true);
        registerModule(Instances.chams, false);
        registerModule(Instances.og, true);
        registerModule(Instances.entityESP, false);
        registerModule(Instances.fullbright, true);
        registerModule(Instances.mobOwner, false);
        registerModule(Instances.nametags, false);
        registerModule(Instances.storageESP, false);
        registerModule(Instances.trajectories, false);
        registerModule(Instances.tracers, false);
    }

    public void initMovement() {
        Instances.blink =
                new Blink("Blink", Category.MOVEMENT, "Taki fake lag.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.bunnyHop =
                new BunnyHop("Bunnyhop", Category.MOVEMENT, "Skacze lol.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.elytraFly =
                new ElytraFly("Elytra Fly", Category.MOVEMENT, "Lata elytra.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.fly =
                new Fly("Fly", Category.MOVEMENT, "Pozwala latac.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.jesus =
                new Jesus("Jesus", Category.MOVEMENT, "Mesjasz", -1, Colors.MOVEMENT);
        Instances.noFall =
                new NoFall("No Fall", Category.MOVEMENT, "Nie dostajesz obrazen od upadku.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.noSlowdown =
                new NoSlowdown("No Slowdown", Category.MOVEMENT, "Nie dostajesz obrazen od upadku.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.sprint =
                new Sprint("Sprint", Category.MOVEMENT, "Auto sprint.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.step =
                new Step("Step", Category.MOVEMENT, "Lepszy autojump.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);
        Instances.sneak =
                new Sneak("Toggle Sneak", Category.MOVEMENT, "Kuca non stop.", GLFW.GLFW_KEY_UNKNOWN, Colors.MOVEMENT);

        registerModule(Instances.blink, false);
        registerModule(Instances.bunnyHop, false);
        registerModule(Instances.elytraFly, false);
        registerModule(Instances.fly, false);
        registerModule(Instances.jesus, false);
        registerModule(Instances.noFall, false);
        registerModule(Instances.noSlowdown, false);
        registerModule(Instances.sprint, true);
        registerModule(Instances.step, false);
        registerModule(Instances.sneak, true);
    }

    public void initGui() {
        Instances.bindCommandEditor = new BindCommandEditor("Bind Cmd Editor", Category.GUI, "Allows you to edit the bind commands.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI);
        Instances.colours = new Colours();
        Instances.hud = new Hud("Hud", Category.GUI, "User interface.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI);
        Instances.hudEditor = new HudEditor("Hud Editor", Category.GUI, "Allows you to edit the ui position.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI);
        Instances.clickGui = new ClickGui("Click Gui", Category.GUI, "Displays the new ClickGUI.", GLFW.GLFW_KEY_RIGHT_SHIFT, Colors.GUI);
        Instances.panic = new Panic("Panic!", Category.GUI, "Wylacza wszystkie funkcje moda.", GLFW.GLFW_KEY_UNKNOWN, Colors.GUI);

        registerModule(Instances.bindCommandEditor, true);
        registerModule(Instances.colours, true);
        registerModule(Instances.hud, true);
        registerModule(Instances.hudEditor, true);
        registerModule(Instances.clickGui, true);
        registerModule(Instances.panic, false);
    }

    public void initPlayer() {
        Instances.autoFish =
                new AutoFish("Auto Fish", Category.PLAYER, "Lowi rypki.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER);
        Instances.autoMine =
                new AutoMine("Auto Mine", Category.PLAYER, "Kopiesz blok który masz przed sobą.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER);
        Instances.autoRespawn =
                new AutoRespawn("Auto Respawn", Category.PLAYER, "Odradza gracza po smierci.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER);
        Instances.autoWaterBucket =
                new AutoWaterBucket("Auto Water Bucket", Category.PLAYER, "Mlg water bucket.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER);
        Instances.deathCoords =
                new DeathCoords("Death Coords", Category.PLAYER, "Wysyla kordy smierci na chacie.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER);
        Instances.freecam =
                new Freecam("Freecam", Category.PLAYER, "Umozliwia wyjscie z twojego ciala.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER);
        Instances.recordingMode =
                new RecordingMode("Recording Mode", Category.PLAYER, "Tryb nagrywania.", GLFW.GLFW_KEY_UNKNOWN, Colors.PLAYER);

        registerModule(Instances.autoFish, false);
        registerModule(Instances.autoMine, false);
        registerModule(Instances.autoRespawn, true);
        registerModule(Instances.autoWaterBucket, false);
        registerModule(Instances.deathCoords, true);
        registerModule(Instances.freecam, false);
        registerModule(Instances.recordingMode, true);
    }

    public void initWorld() {
        Instances.bedNuker =
                new BedNuker("Bed Nuker", Category.WORLD, "Niszczy lozka dookola ciebie.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD);
        Instances.nuker =
                new Nuker("Nuker", Category.WORLD, "Kopie bloki do okola ciebie.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD);
        Instances.scaffold =
                new Scaffold("Scaffold", Category.WORLD, "Kladzie bloki pod toba.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD);
        Instances.xray =
                new Xray("Xray", Category.WORLD, "Laduje wybrane bloki do okola ciebie.", GLFW.GLFW_KEY_UNKNOWN, Colors.WORLD);

        registerModule(Instances.bedNuker, false);
        registerModule(Instances.nuker, false);
        registerModule(Instances.scaffold, false);
        registerModule(Instances.xray, false);
    }

    public void initMisc() {
        Instances.antiChunkBan =
                new AntiChunkBan("Anti Chunk Ban", Category.MISC, "Taki antypacketkick.", GLFW.GLFW_KEY_UNKNOWN, Colors.MISC);
        Instances.antiLevitation =
                new AntiLevitation("Anti Levitation", Category.MISC, "Niszczy te magiczne kule od shulkerow.", GLFW.GLFW_KEY_UNKNOWN, Colors.MISC);
        Instances.middleClickFriend =
                new MiddleClickFriend("MCF", Category.MISC, "Dodaje gracza do znajomych.", GLFW.GLFW_KEY_UNKNOWN, Colors.MISC);

        registerModule(Instances.antiChunkBan, true);
        registerModule(Instances.antiLevitation, false);
        registerModule(Instances.middleClickFriend, true);
    }

    public void initChat() {
        Instances.bindCommands =
                new BindCommands("Bind Commands", Category.CHAT, "Zarzadzasz komendami.", GLFW.GLFW_KEY_UNKNOWN, Colors.CHAT);
        Instances.nameMention =
                new NameMention("Name Mention", Category.CHAT, "Gra dzwiek jak ktos napisze twoj nick na chacie.", GLFW.GLFW_KEY_UNKNOWN, Colors.CHAT);

        registerModule(Instances.bindCommands, true);
        registerModule(Instances.nameMention, true);
    }

    public void initExploits() {
        Instances.blockSpeed =
                new BlockSpeed("Block Speed", Category.EXPLOITS, "Biegasz szybciej na niektórych blokach.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS);
        Instances.bookCrash =
                new BookCrash("Book Crash", Category.EXPLOITS, "Wywala serwer.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS);
        Instances.fastUse =
                new FastUse("Fast Use", Category.EXPLOITS, "Szybko.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS);
        Instances.offhandCrash =
                new OffhandCrash("Offhand Crash", Category.EXPLOITS, "Wywala serwer.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS);
        Instances.playerCrash =
                new PlayerCrash("Player Crash", Category.EXPLOITS, "Wywala serwer.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS);
        Instances.speedMine =
                new SpeedMine("Speed Mine", Category.EXPLOITS, "Szybciej niszczysz.", GLFW.GLFW_KEY_UNKNOWN, Colors.EXPLOITS);

        registerModule(Instances.blockSpeed, false);
        registerModule(Instances.bookCrash, false);
        registerModule(Instances.fastUse, false);
        registerModule(Instances.offhandCrash, false);
        registerModule(Instances.playerCrash, false);
        registerModule(Instances.speedMine, false);
    }

    public void init() {
        modules = new ArrayList<Module>();
        legitModules = new ArrayList<Module>();
        nonLegitModules = new ArrayList<Module>();
        EventManager.register(this);

        initCombat();
        initRender();
        initMovement();
        initGui();
        initPlayer();
        initWorld();
        initMisc();
        initChat();
        initExploits();

        registerModule(new ServerCrash(), false);
        registerModule(new TestGui("TestGui", Category.EXPERIMENTAL, "test gui", -1, Colors.EXPERIMENTAL), false);

        /* CLIENT */
        registerModule(new ForcedStuff(), true);

    }

    public static void registerModule(Module module, boolean state) {
        if(state) {
            legitModules.add(module);
        } else {
            nonLegitModules.add(module);
        }
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
//        if(!Main.isPanic && !Main.isReloading && !Main.debugMode) {
//            Main.checkIfValid();
//        }
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

    private Timer timer = new Timer();

//    @EventTarget
//    public void onTick(TickEvent event) {
//        assert mc.player != null;
//        if(!loaded) {
//            ApiV2.getInstance().updatePlayer(mc.player.getUuidAsString(), true);
//            Main.reloadApiStats();
//            loaded = true;
//        }
//        if(timer.getSecondsPassed() >= 60) {
//            Thread thread = new Thread(Main::reloadApiStats);
//            thread.start();
//            timer = new Timer();
//        }
//
//    }

    public static ArrayList<Module> getModules() {
        return modules;
    }
}
