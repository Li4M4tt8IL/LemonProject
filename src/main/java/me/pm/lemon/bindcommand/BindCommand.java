package me.pm.lemon.bindcommand;

public class BindCommand {
    private String command;
    private int keyCode;
    private boolean enabled = true;

    public BindCommand(int keyCode, String command) {
        this.command = command;
        this.keyCode = keyCode;
    }

    public boolean isToggled() {
        return enabled;
    }

    public void toggle() {
        this.enabled = !enabled;
    }

    public void setToggled(boolean toggled) {
        this.enabled = toggled;
    }

    public String getCommandName() {
        return command;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) { this.keyCode = keyCode;}
}
