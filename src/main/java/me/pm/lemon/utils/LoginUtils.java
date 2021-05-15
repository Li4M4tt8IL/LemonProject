package me.pm.lemon.utils;

import me.pm.lemon.Main;

public class LoginUtils {

    public static boolean login(String email, String password) {
        if(Main.debugMode) {
            Main.loggedIn = true;
            return true;
        } else if(email.equals("admin@lemon.com") && password.equals("LemonClient")) {
            FileManager.saveLogin(email, password);
            Main.loggedIn = true;
            return true;
        } else {
            // TODO: Server connection
            return false;
        }
    }
}
