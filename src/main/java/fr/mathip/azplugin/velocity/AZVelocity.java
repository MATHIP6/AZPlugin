package fr.mathip.azplugin.velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import fr.mathip.azplugin.velocity.connection.ConnectionManager;
import lombok.Getter;

@Getter
@Plugin(id = "azplugin", name = "AZPlugin", version = "1.0-SNAPSHOT", url = "https://www.spigotmc.org/resources/azplugin.115548/", description = "A plugin to integrate the AZ Launcher.", authors = {
        "MATHIP6" })
public class AZVelocity {

    @Inject
    private ProxyServer proxy;
    @Inject
    private Logger logger;
    @Getter
    private static AZVelocity instance;
    private List<UUID> azPlayers = new ArrayList<>();

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        new ConnectionManager(proxy);

    }

}
