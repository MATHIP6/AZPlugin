package fr.mathip.azplugin.velocity.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

public class ProxyMessageManager {

    public static final MinecraftChannelIdentifier IDENTIFIER = MinecraftChannelIdentifier.from("azplugin:list");

    public ProxyMessageManager(ProxyServer server) {
        server.getChannelRegistrar().register(IDENTIFIER);
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(IDENTIFIER)) {
            return;
        }
        event.setResult(PluginMessageEvent.ForwardResult.handled());

        if (event.getSource() instanceof Player) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String res = in.readUTF();

    }

}
