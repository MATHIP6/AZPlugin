package fr.mathip.azplugin.bukkit;

import fr.mathip.azplugin.bukkit.utils.PLSPPacketBuffer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import pactify.client.api.mcprotocol.util.NotchianPacketUtil;
import pactify.client.api.plsp.PLSPPacket;
import pactify.client.api.plsp.PLSPPacketHandler;
import pactify.client.api.plsp.PLSPProtocol;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class AZManager implements Listener, Closeable {
    private final Main plugin;
    private final List<AZPlayer> players;

    public AZManager(final Main plugin) {
        this.players = new ArrayList<>();
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "PLSP");
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final AZPlayer azPlayer;
        azPlayer = new AZPlayer(this, event.getPlayer());
        if (plugin.getConnectionManager().getAzPlayers().contains(azPlayer.getPlayer().getUniqueId())) {
            azPlayer.setAZ(true);
        }
        azPlayer.init();
        azPlayer.join();
    }

    public AZPlayer getPlayer(final Player player) {
        for (AZPlayer azPlayer : players) {
            if (azPlayer.getPlayer().equals(player)) {
                return azPlayer;
            }
        }
        return null;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        for (AZPlayer azPlayer : players) {
            if (azPlayer.getPlayer().equals(event.getPlayer())) {
                if (azPlayer != null) {
                    azPlayer.free();
                }
            }
        }
    }

    public static void sendPLSPMessage(final Player player, final PLSPPacket<PLSPPacketHandler.ClientHandler> message) {
        try {
            final PLSPPacketBuffer buf = new PLSPPacketBuffer();
            final PLSPProtocol.PacketData<?> packetData = PLSPProtocol.getClientPacketByClass(message.getClass());
            NotchianPacketUtil.writeString(buf, packetData.getId(), 32767);
            message.write(buf);
            player.sendPluginMessage(Main.getInstance(), "PLSP", buf.toBytes());
        } catch (Exception e) {
            Main.getInstance().getLogger().log(Level.WARNING,
                    "Exception sending PLSP message to " + ((player != null) ? player.getName() : "null") + ":", e);
        }
    }

    public void close() throws IOException {
        HandlerList.unregisterAll(this);
        this.plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(this.plugin, "PLSP");
    }

    public Plugin getPlugin() {
        return this.plugin;
    }
}
