package fr.mathip.azplugin.bukkit.connection;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class ConnectionListener implements Listener, PluginMessageListener {

    public static final String CHANNEL = "azplugin:list";

    ConnectionManager manager;

    public ConnectionListener(ConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals(CHANNEL)) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        UUID uuid;
        switch (subchannel) {
            case "add-az-player":
                uuid = UUID.fromString(in.readUTF());
                manager.getAzPlayers().add(uuid);
                break;
            case "remove-az-player":
                uuid = UUID.fromString(in.readUTF());
                manager.getAzPlayers().remove(uuid);
                break;
            case "add-all-az-players":
                int size = in.readInt();
                for (int i = 0; i < size; i++) {
                    uuid = UUID.fromString(in.readUTF());
                    manager.getAzPlayers().add(uuid);
                }
                break;
            case "clear-az-players":
                manager.getAzPlayers().clear();
        }
    }
}
