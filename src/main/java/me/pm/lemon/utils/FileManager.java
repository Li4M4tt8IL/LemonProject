package me.pm.lemon.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.pm.lemon.Main;
import me.pm.lemon.bindcommand.BindCommand;
import me.pm.lemon.bindcommand.BindCommandManager;
import me.pm.lemon.command.Command;
import me.pm.lemon.friends.Friend;
import me.pm.lemon.friends.FriendManager;
import me.pm.lemon.gui.clickGui.settings.Setting;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.ModuleManager;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    public FileManager()
    {
        loadFriends();
        loadBindCommands();
//        loadAlts();
    }

    public static void saveModules() {
        System.out.println("PMTEST!@#");
        FileHelper.createEmptyFile("modules.json");
        JsonObject jo = new JsonObject();

        for (Module m : ModuleManager.getModules()) {
            JsonObject mo = new JsonObject();

            if (m.isToggled() && !m.getName().equals("ClickGui") && !m.getName().equals("Freecam")) {
                mo.add("toggled", new JsonPrimitive(true));
            }

            if (m.getKeyCode() >= 0) {
                mo.add("bind", new JsonPrimitive(m.getKeyCode()));
            }

            if (!m.getSettings().isEmpty()) {
                JsonObject so = new JsonObject();
                // Seperate JsonObject with all the settings to keep the extra number so when it reads, it doesn't mess up the order
                JsonObject fullSo = new JsonObject();

                for (Setting s : m.getSettings()) {
                    String name = s.getName();

                    int extra = 0;
                    while (fullSo.has(name)) {
                        extra++;
                        name = s.getName() + extra;
                    }

                    fullSo.add(name, s.saveSettings());
                    if (!s.isDefault()) so.add(name, s.saveSettings());
                }

                if (so.size() != 0) {
                    mo.add("settings", so);
                }

            }

            if (mo.size() != 0) {
                jo.add(m.getName(), mo);
            }
        }

        FileHelper.setJsonFile(jo, "modules.json");
    }


    public static void readModules() {
        JsonObject jo = FileHelper.readJsonFile("modules.json");

        if (jo == null) return;

        for (Map.Entry<String, JsonElement> e : jo.entrySet()) {
            Module mod = Module.getModuleByName(e.getKey());

            if (mod == null) continue;

            if (e.getValue().isJsonObject()) {
                JsonObject mo = e.getValue().getAsJsonObject();
                if (mo.has("toggled")) {
                    mod.toggle();
                }

                if (mo.has("bind") && mo.get("bind").isJsonPrimitive() && mo.get("bind").getAsJsonPrimitive().isNumber()) {
                    mod.setKeyCode(mo.get("bind").getAsInt());
                }

                if (mo.has("settings") && mo.get("settings").isJsonObject()) {
                    for (Map.Entry<String, JsonElement> se : mo.get("settings").getAsJsonObject().entrySet()) {
                        // Map to keep track if there are multiple settings with the same name
                        HashMap<String, Integer> sNames = new HashMap<>();

                        for (Setting sb : mod.getSettings()) {
                            String name = sNames.containsKey(sb.getName()) ? sb.getName() + sNames.get(sb.getName()) : sb.getName();

                            if (name.equals(se.getKey())) {
                                sb.readSettings(se.getValue());
                                break;
                            } else {
                                sNames.put(sb.getName(), sNames.containsKey(sb.getName()) ? sNames.get(sb.getName()) + 1 : 1);
                            }
                        }
                    }
                }
            }
        }
    }

//    public static void saveClickGui() {
//
//        FileHelper.createEmptyFile("clickgui.txt");
//
//        String text = "";
//        for (Window w : Main.ClientScreens.clickGuiScreen.windows) text += w.x1 + ":" + w.y1 + ":" + ((ModuleWindow) w).hiding + "\n";
//
//        FileHelper.appendFile(text, "clickgui.txt");
//    }
//
//    public static void readClickGui() {
//        List<String> lines = FileHelper.readFileLines("clickgui.txt");
//
//        try {
//            int c = 0;
//            for (Window w : Main.ClientScreens.clickGuiScreen.windows) {
//                w.x1 = Integer.parseInt(lines.get(c).split(":")[0]);
//                w.y1 = Integer.parseInt(lines.get(c).split(":")[1]);
//                ((ModuleWindow)w).hiding = Boolean.parseBoolean(lines.get(c).split(":")[2]);
//                c++;
//            }
//        } catch (Exception e) {
//        }
//    }

    public static void readPrefix() {
        List<String> lines = FileHelper.readFileLines("prefix.txt");

        try {
            Command.PREFIX = lines.get(0);
        } catch (Exception e) {
        }
    }

    public static void updateFriendList()
    {
        try
        {
            File file = new File(String.valueOf(FileHelper.getRootDir()), "friends.txt");
            if(!file.exists()) file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(Friend friend: FriendManager.friendsList)
            {
                out.write(friend.getName());
                out.write("\r\n");
            }
            out.close();
        }catch(Exception e) {}
    }

    public void loadFriends()
    {
        try
        {
            File file = new File(String.valueOf(FileHelper.getRootDir()), "friends.txt");
            if(!file.exists()) file.createNewFile();
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null)
            {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                Module.getFriendManager().addFriend(name);
            }
            br.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void loadAuthenticatedUsers() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/Li4M4tt8IL/PMHack_Info/master/players.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                Main.ClientInfo.clientAuth.add(line);
            }
            br.close();
        } catch(Exception e) {
            Main.ClientInfo.clientAuth.add(Main.ClientInfo.clientCreators);
            e.printStackTrace();
        }
    }

    public static void updateBindCommands() {
        try {
            File file = new File(String.valueOf(FileHelper.getRootDir()), "bindCommands.txt");
            if(!file.exists()) file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(BindCommand command : BindCommandManager.commands) {
                if(!command.getCommandName().equalsIgnoreCase("/example")) {
                    out.write(command.getCommandName().toLowerCase().replace(" ", ";") + ":" + command.getKeyCode() + ":" + command.isToggled());
                    out.write("\r\n");
                }
            }
            out.close();
        } catch(Exception e) {
            LemonLogger.errorMessage("File Error","Failed to update the bind command list state");
            e.printStackTrace();
            return;
        }
    }

    public static void loadBindCommands() {
        try {
            File file = new File(String.valueOf(FileHelper.getRootDir()), "bindCommands.txt");
            if(!file.exists()) file.createNewFile();
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.toLowerCase().trim();
                String name = curLine.split(":")[0];
                int keycode = Integer.parseInt(curLine.split(":")[1]);
                boolean toggled = Boolean.parseBoolean(curLine.split(":")[2]);
                if(!name.equalsIgnoreCase("/example")) {
                    BindCommandManager.registerBindCommand(new BindCommand(keycode, name.replace(";", " ")));
                }
                BindCommand bc = BindCommandManager.getCommandByName(name.replace(";", " "));
                bc.setToggled(toggled);
            }
            br.close();
        } catch(Exception e) {
            LemonLogger.errorMessage("File Error","Failed to load the bind command list state");
            e.printStackTrace();
            return;
        }
    }

}
