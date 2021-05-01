package me.pm.lemon.command;

import java.util.List;

public abstract class Command {

    public static String PREFIX = ";;";

    public abstract String getAlias();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract List<String> getSuggestedArgs();
    public abstract boolean isCommandEnabled();
    public abstract void onCommand(String command, String[] args) throws Exception;

}
