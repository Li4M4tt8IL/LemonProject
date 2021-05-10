package me.pm.lemon.notifications;

import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class Notification {
    private NotificationType type;
    private String title;
    private String message;
    private long start;

    private long fadedIn;
    private long fadeOut;
    private long end;

    public Notification(NotificationType type, String title, String message, int length) {
        this.type = type;
        this.title = title;
        this.message = message;

        fadedIn = 200 * length;
        fadeOut = fadedIn +
                MinecraftClient.getInstance().textRenderer.getWidth(message) < MinecraftClient.getInstance().textRenderer.getWidth(title) ?
                (MinecraftClient.getInstance().textRenderer.getWidth(title) * 20) :
                (MinecraftClient.getInstance().textRenderer.getWidth(message) * 20)
                        * length;
        end = fadeOut + fadedIn;
    }

    public void show() {
        start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return getTime() <= end;
    }

    public void render() {
        TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;
        double offset = 0;
        int width = 20+ fontRenderer.getWidth(message);
        int height = 30;
        long time = getTime();

        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        Color color = new Color(30, 30, 30, 219);
        Color color1;

        if (type == NotificationType.INFO) {
            color1 = new Color(0, 255, 200);
        }
        else if (type == NotificationType.WARNING) {
            color1 = new Color(200, 200, 150);
        }
        else {
            color1 = new Color(255, 0, 100, 255);
            int i = Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5)));
            color = new Color(i, 0, 0, 220);
        }


        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth() - 5;
        int scaledHeight = height + 10;

//        RenderUtils.drawRect(scaledWidth - offset, scaledHeight - 5 - height, scaledWidth, scaledHeight - 5, color.getRGB());
//        RenderUtils.drawRect(scaledWidth - offset, scaledHeight - 5 - height, scaledWidth - offset + 4, scaledHeight - 5, color1.getRGB());

        RenderUtils.drawRect(scaledWidth - offset, scaledHeight - 5 - height, scaledWidth, scaledHeight - 5, color.getRGB()); // BG
        RenderUtils.drawRect(scaledWidth - offset, scaledHeight - 5 - height, scaledWidth - offset + 4, scaledHeight - 5, color1.getRGB());

        fontRenderer.draw(new MatrixStack(), title, (int) (scaledWidth - offset + 8), scaledHeight - 2 - height, -1);
        fontRenderer.draw(new MatrixStack(), message, (int) (scaledWidth - offset + 8), scaledHeight - 15, -1);
    }

    private long getTime() {
        return System.currentTimeMillis() - start;
    }
}
