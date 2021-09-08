package me.pm.lemon.utils;

import me.pm.lemon.Main;

public class LoginUtils {

    public static boolean login(String email, String password) {
        if (Main.debugMode) {
            Main.loggedIn = true;
            return true;
        }
        if(Main.ClientInfo.logins.get(email).equals(password)) {
            FileManager.saveLogin(email, password);
            return true;
        }
        return false;
    }
}
