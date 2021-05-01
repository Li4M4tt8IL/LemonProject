package me.pm.lemon.utils;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;

public class MySQL {
    private static final MySQL instance = new MySQL();

    private Connection connection;
    public String host, database, username, password, player_table;
    public int port;

    public void mysqlSetup(){
        host = "localhost";
        port = 3306;
        database = "mysql";
        username = "root";
        password = "";
        player_table = "player_data";

        try{
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":"
                        + port + "/" + database, username, password));
                LemonLogger.infoMessage("MYSQL connected!");
            }
        } catch(SQLException e){
            e.printStackTrace();
            LemonLogger.errorMessage("MYSQL error doesn't matter");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private boolean logged = false;

    @EventTarget
    public void EventConnect(TickEvent event) {
        if(!logged) {
            // add player to list
            addPlayer(MinecraftClient.getInstance().player.getName().getString(),
                    getServerHostAddress());
            System.out.println("login successful");
            logged = true;
        } else {
            if(MinecraftClient.getInstance().currentScreen instanceof TitleScreen) {
                // remove player to list
                removePlayer(MinecraftClient.getInstance().player.getName().getString(),
                        getServerHostAddress());
                System.out.println("disconnect successful");
                logged = false;
            }
        }
    }

    public ArrayList<String> playersInServer() {
        ArrayList<String> playersNames = new ArrayList<>();
        for(Entity player : MinecraftClient.getInstance().world.getEntities()) {
            if(player instanceof PlayerEntity) {
                if(checkIfPlayerInSQL(player.getName().getString(), getServerHostAddress())) {
                    playersNames.add(player.getName().getString());
                }
            }
        }
        return playersNames;
    }

    public Boolean checkIfPlayerInSQL(String username, String ip) {
        try {
            PreparedStatement statement2 = getConnection().prepareStatement("SELECT * FROM " + player_table + " WHERE (NAME, IP) VALUES (?,?)");
            statement2.setString(1, username);
            statement2.setString(2, ip);
            ResultSet result2 = statement2.executeQuery();
            return result2.next();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getServerHostAddress() {
        try {
            InetAddress inetAddress = MinecraftClient.getInstance().getCurrentServerEntry() == null ? null
                    : InetAddress.getByName(MinecraftClient.getInstance().getCurrentServerEntry().address);
            if(inetAddress != null) {
                return inetAddress.getHostAddress();
            }
            return "0";
        } catch (UnknownHostException e) {
            return "0";
        }
    }

    public void addPlayer(String playerName, String ip) {
        if(ip.equals("0")) return;
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT * FROM " + player_table + " WHERE NAME=?");
            statement.setString(1, playerName);
            ResultSet results = statement.executeQuery();
            if(!results.next()) {
                PreparedStatement insert = getConnection()
                        .prepareStatement("INSERT INTO " + player_table + " (NAME,IP) VALUES (?,?)");
                insert.setString(1, playerName);
                insert.setString(2, ip);
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePlayer(String playerName, String ip) {
        if(ip.equals("0")) return;
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT * FROM " + player_table + " WHERE NAME=?");
            statement.setString(1, playerName);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                PreparedStatement insert = getConnection()
                        .prepareStatement("DELETE * FROM " + player_table + " WHERE NAME=?");
                insert.setString(1, playerName);
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static MySQL getInstance() {
        return instance;
    }
}
