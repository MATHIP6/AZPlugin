package fr.mathip.azplugin.bukkit.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import fr.mathip.azplugin.bukkit.Main;
import lombok.Getter;

public class ConnectionManager {

    @Getter
    private List<UUID> azPlayers = new ArrayList<>();

    public ConnectionManager() {
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), Main.getInstance());
    }

}
