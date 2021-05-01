package me.pm.lemon.utils;

import com.google.gson.*;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileHelper {
    private static Path ROOT_DIR;
    private static Path HUD_DIR;

    public static boolean first_run;
    public static void init() {
        ROOT_DIR = Paths.get(MinecraftClient.getInstance().runDirectory.getPath(), "lemon/");
        if (!ROOT_DIR.toFile().exists()) {
            ROOT_DIR.toFile().mkdirs();
            first_run = true;
        }
        HUD_DIR = Paths.get(MinecraftClient.getInstance().runDirectory.getPath(), "lemon/HUD");
        if (!HUD_DIR.toFile().exists()) {
            HUD_DIR.toFile().mkdirs();
        }
    }

    /**
     * Gets the directories minecraft folder.
     **/
    public static Path getRootDir() {
        return ROOT_DIR;
    }
    public static Path getHudDir() {
        return HUD_DIR;
    }

    /**
     * Reads a file and returns a list of the lines.
     **/
    public static List<String> readFileLines(String... file) {
        try {
            return Files.readAllLines(stringsToPath(file));
        } catch (NoSuchFileException e) {

        } catch (Exception e) {
            System.out.println("Error Reading File: " + stringsToPath(file));
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Creates a file, doesn't do anything if the file already exists.
     **/
    public static void createFile(String... file) {
        try {
            if (fileExists(file)) return;
            getRootDir().toFile().mkdirs();
            Files.createFile(stringsToPath(file));
        } catch (Exception e) {
            System.out.println("Error Creating File: " + Arrays.toString(file));
            e.printStackTrace();
        }
    }

    /**
     * Creates a file, clears it if it already exists
     **/
    public static void createEmptyFile(String... file) {
        try {
            createFile(file);

            FileWriter writer = new FileWriter(stringsToPath(file).toFile());
            writer.write("");
            writer.close();
        } catch (Exception e) {
            System.out.println("Error Clearing/Creating File: " + Arrays.toString(file));
            e.printStackTrace();
        }
    }

    /**
     * Adds a line to a file.
     **/
    public static void appendFile(String content, String... file) {
        try {
            String fileContent = new String(Files.readAllBytes(stringsToPath(file)));
            FileWriter writer = new FileWriter(stringsToPath(file).toFile(), true);
            writer.write(
                    (fileContent.endsWith("\n") || !fileContent.contains("\n") ? "" : "\n")
                            + content
                            + (content.endsWith("\n") ? "" : "\n"));
            writer.close();
        } catch (Exception e) {
            System.out.println("Error Appending File: " + Arrays.toString(file));
            e.printStackTrace();
        }
    }

    /**
     * Returns true if a file exists, returns false otherwise
     **/
    public static boolean fileExists(String... file) {
        try {
            return stringsToPath(file).toFile().exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes a file if it exists.
     **/
    public static void deleteFile(String... file) {
        try {
            Files.deleteIfExists(stringsToPath(file));
        } catch (Exception e) {
            System.out.println("Error Deleting File: " + Arrays.toString(file));
            e.printStackTrace();
        }
    }

    /**
     * Gets a file by walking down all of the parameters (starts at .minecraft/bleach/).
     **/
    public static Path stringsToPath(String... strings) {
        Path path = getRootDir();
        for (String s : strings) path = path.resolve(s);
        return path;
    }

    private static final Gson jsonWriter = new GsonBuilder().setPrettyPrinting().create();

    public static void addJsonElement(String key, JsonElement element, String... path) {
        JsonObject file = null;
        boolean overwrite = false;

        if (!fileExists(path)) {
            overwrite = true;
        } else {
            List<String> lines = readFileLines(path);

            if (lines.isEmpty()) {
                overwrite = true;
            } else {
                String merged = String.join("\n", lines);

                try {
                    file = new JsonParser().parse(merged).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                    overwrite = true;
                }
            }
        }

        createEmptyFile(path);
        if (overwrite) {
            JsonObject mainJO = new JsonObject();
            mainJO.add(key, element);

            appendFile(jsonWriter.toJson(mainJO), path);
        } else {
            file.add(key, element);

            appendFile(jsonWriter.toJson(file), path);
        }
    }

    public static void setJsonFile(JsonObject element, String... path) {
        createEmptyFile(path);
        appendFile(jsonWriter.toJson(element), path);
    }

    public static JsonElement readJsonElement(String key, String... path) {
        JsonObject jo = readJsonFile(path);

        if (jo == null) return null;

        if (jo.has(key)) {
            return jo.get(key);
        }

        return null;
    }

    public static JsonObject readJsonFile(String... path) {
        List<String> lines = readFileLines(path);

        if (lines.isEmpty()) return null;

        String merged = String.join("\n", lines);

        try {
            return new JsonParser().parse(merged).getAsJsonObject();
        } catch (JsonParseException e) {
            System.err.println("Json error Trying to read " + Arrays.asList(path) + "! DELETING ENTIRE FILE!");
            e.printStackTrace();

            deleteFile(path);
            return null;
        }
    }

    public static void writeJsonToFile(File file, Object obj) {
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(jsonWriter.toJson(obj).getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Object> T readFromJson(File file, Class<T> c) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder builder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();

            return jsonWriter.fromJson(builder.toString(), c);
        } catch (IOException e) {
            return null;
        }
    }
}
