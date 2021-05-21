package me.pm.lemon.utils;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import me.pm.lemon.Main;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class ApiV2 {
    private static final ApiV2 instance = new ApiV2();

    public String name = "";
    private String url = "http://craftcommunity.pl/players/";

    public List<String> getNames() {
        try {
            URL url = new URL(this.url);
            String json = get(url).asString();
            List<String> UUIDs = JsonPath.from(json).get("names");
            return UUIDs;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getNames().isEmpty()) {
                return backup;
            } else {
                return Main.getNames();
            }
        }
    }

    public void updatePlayer(String name, boolean online) {
        try {
            String query = String.format("?name=%s&online=%s", name.replaceAll("-", ""), online);
            URL url = new URL(this.url+query);
            given()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON).accept(ContentType.JSON)
                    .when()
                    .post(url).then().statusCode(200);
            this.name = name.replaceAll("-", "");
        } catch (Exception e) {
            return;
        }
    }

    public void updatePlayerWithName(String name, boolean online) {
        try {
            String query = String.format("?name=%s&online=%s", getUuid(name), online);
            URL url = new URL(this.url+query);
            given()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON).accept(ContentType.JSON)
                    .when()
                    .post(url).then().statusCode(200);
            this.name = getUuid(name);
        } catch (Exception e) {
            return;
        }
    }

    public void updatePlayerWithName(String name, boolean online, String hat, String wings) {
        try {
            String query = String.format("?name=%s&online=%s&hat=%s&wings=%s", getUuid(name), online, hat, wings);
            URL url = new URL(this.url+query);
            given()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON).accept(ContentType.JSON)
                    .when()
                    .post(url).then().statusCode(200);
            this.name = getUuid(name);
        } catch (Exception e) {
            return;
        }
    }


    public void updatePlayer(String name, boolean online, String hat, String wings) {
        try {
            String query = String.format("?name=%s&online=%s&hat=%s&wings=%s", name.replaceAll("-", ""), online, hat, wings);
            URL url = new URL(this.url+query);
            given()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON).accept(ContentType.JSON)
                    .when()
                    .post(url).then().statusCode(200);
            this.name = name.replaceAll("-", "");
        } catch (Exception e) {
            return;
        }
    }

    public String getUuid(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/"+name;
        try {
            @SuppressWarnings("deprecation")
            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.isEmpty()) return "invalid name";
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("id").toString();
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return "error";
    }

    public void updatePlayer(String query) {
        try {
            URL url = new URL(this.url+query.replaceAll("-", ""));
            given()
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON).accept(ContentType.JSON)
                    .when()
                    .post(url).then().statusCode(200);
            this.name = name;
        } catch (Exception e) {
            return;
        }
    }

    public List<String> getTophats() {
        try {
            String query = "hats";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String hat = string.split(":")[1];
                String name = string.split(":")[0];
                if(hat.equalsIgnoreCase("tophat")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getTopHats().isEmpty()) {
                return backup;
            } else {
                return Main.getTopHats();
            }
        }
    }

    public List<String> getChristmasHats() {
        try {
            String query = "hats";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String hat = string.split(":")[1];
                String name = string.split(":")[0];
                if(hat.equalsIgnoreCase("xmas")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getChristmasHats().isEmpty()) {
                return backup;
            } else {
                return Main.getChristmasHats();
            }
        }
    }

    public List<String> getLightEars() {
        try {
            String query = "hats";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String hat = string.split(":")[1];
                String name = string.split(":")[0];
                if(hat.equalsIgnoreCase("ears-light")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getLightEars().isEmpty()) {
                return backup;
            } else {
                return Main.getLightEars();
            }
        }
    }

    public List<String> getDarkEars() {
        try {
            String query = "hats";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String hat = string.split(":")[1];
                String name = string.split(":")[0];
                if(hat.equalsIgnoreCase("ears-dark")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getDarkEars().isEmpty()) {
                return backup;
            } else {
                return Main.getDarkEars();
            }
        }
    }

    public List<String> getDemonWings() {
        try {
            String query = "wings";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String wings = string.split(":")[1];
                String name = string.split(":")[0];
                if(wings.equalsIgnoreCase("demon")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getDemonWings().isEmpty()) {
                return backup;
            } else {
                return Main.getDemonWings();
            }
        }
    }

    public List<String> getDragonWings() {
        try {
            String query = "wings";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String wings = string.split(":")[1];
                String name = string.split(":")[0];
                if(wings.equalsIgnoreCase("dragon")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getDemonWings().isEmpty()) {
                return backup;
            } else {
                return Main.getDemonWings();
            }
        }
    }

    public List<String> getAngelWings() {
        try {
            String query = "wings";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String wings = string.split(":")[1];
                String name = string.split(":")[0];
                if(wings.equalsIgnoreCase("angel")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getAngelWings().isEmpty()) {
                return backup;
            } else {
                return Main.getAngelWings();
            }
        }
    }

    public List<String> getOrangeFoxEars() {
        try {
            String query = "hats";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String hat = string.split(":")[1];
                String name = string.split(":")[0];
                if(hat.equalsIgnoreCase("fox-orange")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getOrangeFoxEars().isEmpty()) {
                return backup;
            } else {
                return Main.getOrangeFoxEars();
            }
        }
    }

    public List<String> getWhiteFoxEars() {
        try {
            String query = "hats";
            URL url = new URL(this.url+query);
            String json = get(url).asString();
            List<String> list = JsonPath.from(json).get("names");
            List<String> newList = new ArrayList<>();
            for(String string : list) {
                String hat = string.split(":")[1];
                String name = string.split(":")[0];
                if(hat.equalsIgnoreCase("fox-white")) {
                    newList.add(name.replaceAll("-", ""));
                }
            }
            return newList;
        } catch (Exception e) {
            List<String> backup = new ArrayList<>();
            backup.add("Player1");
            LemonLogger.errorMessage("Something went wrong! " , e.getMessage());
            if(Main.getOrangeFoxEars().isEmpty()) {
                return backup;
            } else {
                return Main.getOrangeFoxEars();
            }
        }
    }

    public static ApiV2 getInstance() {
        return instance;
    }
}
