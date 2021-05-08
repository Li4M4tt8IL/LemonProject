package me.pm.lemon.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.pm.lemon.Main;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker {
    public static JsonObject version;
    public static JsonObject version1;
    private static JsonParser jsonParser = new JsonParser();

    public static boolean isUpToDate() {
        if(version == null) {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL("https://raw.githubusercontent.com/Li4M4tt8IL/PMHack_Info/master/info.json").openConnection();
                connection.connect();
                version = jsonParser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                String curVersion = version.getAsJsonObject("versions").get("1.16.5").getAsString();
                String clientVersion = Main.ClientInfo.clientVersion;
                if(clientVersion.equals(curVersion)) {
                    version = null;
                    return true;
                } else {
                    version = null;
                    return false;
                }
            } catch(Exception e) {
                LemonLogger.errorMessage("GitHub Error","Failed to load the mod version!");
                e.printStackTrace();
                version = null;
                return false;
            }
        }
        return false;
    }

    public static void checkOwner() {
        if(version1 == null) {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL("https://raw.githubusercontent.com/Li4M4tt8IL/PMHack_Info/master/info.json").openConnection();
                connection.connect();
                version1 = jsonParser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                String curAuthor = version1.get("author").getAsString();
                String pluginAuthor = Main.ClientInfo.clientCreators;
                if(!(pluginAuthor.equals(curAuthor))) {
                    System.out.println(curAuthor + " | " + Main.ClientInfo.clientCreators);
                    Main.ClientInfo.clientCreators = curAuthor;
                }
            } catch(Exception e) {
                LemonLogger.errorMessage("GitHub Error","Failed to load the mod author!");
                e.printStackTrace();;
            }
        }
    }
}
