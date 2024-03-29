package me.pm.lemon.utils;

import me.pm.lemon.notifications.Notification;
import me.pm.lemon.notifications.NotificationManager;
import me.pm.lemon.notifications.NotificationType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.util.logging.Logger;

public class LemonLogger {
    private static final Logger LOGGER = Logger.getLogger("LEMON");

    public static void infoMessage(String s1, String s) {
        try {
//            MinecraftClient.getInstance().inGameHud.getChatHud()
//                    .addMessage(new LiteralText(s1+" "+s));
            NotificationManager.show(new Notification(NotificationType.INFO, s1, s, 1));
        } catch (Exception e) {
            LOGGER.info(s);
        }
    }

    public static void urlMessage(String s) {
        try {
            LiteralText text = (LiteralText) new LiteralText(s)
                    .formatted(Formatting.UNDERLINE)
                    .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, s)));
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(text);
        } catch (Exception e) {
            LOGGER.info(s);
        }
    }

    public static void warningMessage(String s1, String s) {
        try {
//            MinecraftClient.getInstance().inGameHud.getChatHud()
//                    .addMessage(new LiteralText(s1+" "+s));
            NotificationManager.show(new Notification(NotificationType.WARNING, s1, s, 1));
        } catch (Exception e) {
            LOGGER.warning(s);
        }
    }

    public static void errorMessage(String s1, String s) {
        try {
//            MinecraftClient.getInstance().inGameHud.getChatHud()
//                    .addMessage(new LiteralText(s1+" "+s));
            NotificationManager.show(new Notification(NotificationType.ERROR, s1, s, 1));
        } catch (Exception e) {
            LOGGER.warning(s);
        }
    }

    public static void noPrefixMessage(String s) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText(s));
        } catch (Exception e) {
            LOGGER.info(s);
        }
    }

    private static String getPrefix(Formatting color) {
        return color + "\u00A77[\u00A7bPMHack\u00A77] \u00A7f";
    }
}