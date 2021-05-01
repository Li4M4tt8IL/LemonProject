package me.pm.lemon.gui.autoUpdate;

import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AutoUpdate {
    private String url = "https://github.com/Li4M4tt8IL/PMHack_Info/blob/master/pmmod-1.16.2+.jar?raw=true";
    public int downloadProgress = 0;

    private File getFile() {
        Path path = Paths.get(MinecraftClient.getInstance().runDirectory.getPath(), "mods/pmmod-1.16.2+.jar");
        return path.toFile();
    }

    public void newDownload() {
        try {
            URL url = new URL("https://github.com/Li4M4tt8IL/PMHack_Info/blob/master/pmmod-1.16.2+.jar?raw=true");
            HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
            long completeFileSize = httpConnection.getContentLength();

            BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
            FileOutputStream fos = new FileOutputStream(
                    getFile());
            BufferedOutputStream bout = new BufferedOutputStream(
                    fos, 1024);
            byte[] data = new byte[1024];
            long downloadedFileSize = 0;
            int x = 0;
            while ((x = in.read(data, 0, 1024)) >= 0) {
                downloadedFileSize += x;

                // calculate progress
                downloadProgress = (int) ((double)downloadedFileSize);

                bout.write(data, 0, x);
            }
            bout.close();
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    public int getDownloadProgress() {
        return downloadProgress;
    }

    public int getContentLength() throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.connect();

        // Check if the request is handled successfully
        if(connection.getResponseCode() / 100 == 2) {
            // This should get you the size of the file to download (in bytes)
            return connection.getContentLength();
        }
        return -1;
    }
}
