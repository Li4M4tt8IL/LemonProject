package me.pm.lemon.gui.alts;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.pm.lemon.mixin.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

import java.net.Proxy;

public final class AltLoginThread
        extends Thread {
    private final String password;
    private String status;
    private final String username;
    private MinecraftClient mc = MinecraftClient.getInstance();

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = "\2477Waiting...";
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        if (this.password.equals("")) {
            ((IMinecraftClient) this.mc).setSession(new Session(this.username, "", "", "mojang"));
            this.status = "\247aLogged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = "\247eLogging in...";
        Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = "\2474Login failed!";
        } else {
            AltManager.lastAlt = new Alt(this.username, this.password);
            this.status = "\247aLogged in. (" + auth.getUsername() + ")";
            ((IMinecraftClient) this.mc).setSession(auth);
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}