package fr.mathip.azplugin.velocity.connection;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.proxy.server.ServerRegisteredEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import fr.mathip.azplugin.velocity.AZVelocity;

public class ConnectionManager {

    private static final Pattern AZ_HOSTNAME_PATTERN = Pattern.compile("\u0000(PAC[0-9A-F]{5})\u0000");

    private ProxyServer proxy;

    public ConnectionManager(ProxyServer proxy) {
        this.proxy = proxy;
        proxy.getEventManager().register(AZVelocity.getInstance(), this);
        init();
    }

    public void init() {
        ProxyMessageManager.sendClearAZPlayers(proxy);
        if (!AZVelocity.getInstance().getAzPlayers().isEmpty()) {
            for (RegisteredServer server : proxy.getAllServers()) {
                ProxyMessageManager.sendAllAZPlayers(server);
            }
        }
    }

    @Subscribe
    public void onLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        Matcher m = AZ_HOSTNAME_PATTERN.matcher(player.getRemoteAddress().getHostName());
        if (m.find()) {
            UUID uuid = player.getUniqueId();
            AZVelocity.getInstance().getAzPlayers().add(uuid);
            ProxyMessageManager.sendAZPlayer(proxy, uuid);
        }
    }

    @Subscribe
    public void onQuit(DisconnectEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (AZVelocity.getInstance().getAzPlayers().contains(uuid)) {
            AZVelocity.getInstance().getAzPlayers().remove(event.getPlayer().getUniqueId());
            ProxyMessageManager.sendRemoveAZPlayer(proxy, uuid);
        }
    }

    @Subscribe
    public void onServerStart(ServerRegisteredEvent event) {
        ProxyMessageManager.sendAllAZPlayers(event.registeredServer());
    }

}
