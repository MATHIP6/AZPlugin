package fr.mathip.azplugin.velocity.connection;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import fr.mathip.azplugin.velocity.AZVelocity;
import io.netty.buffer.ByteBuf;

public class ProxyMessageManager {

    public static final MinecraftChannelIdentifier IDENTIFIER = MinecraftChannelIdentifier.from("azplugin:list");

    // @Subscribe
    // public void onPluginMessage(PluginMessageEvent event) {
    // if (!event.getIdentifier().equals(IDENTIFIER)) {
    // return;
    // }
    // event.setResult(PluginMessageEvent.ForwardResult.handled());
    //
    // if (event.getSource() instanceof Player) {
    // return;
    // }
    //
    // ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
    // String res = in.readUTF();
    //
    // }
    //
    public static void sendAZPlayer(ProxyServer proxy, UUID uuid) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("add-az-player");
        out.writeUTF(uuid.toString());
        for (RegisteredServer server : proxy.getAllServers()) {
            server.sendPluginMessage(IDENTIFIER, out.toByteArray());
        }
    }

    public static void sendRemoveAZPlayer(ProxyServer proxy, UUID uuid) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("remove-az-player");
        out.writeUTF(uuid.toString());
        for (RegisteredServer server : proxy.getAllServers()) {
            server.sendPluginMessage(IDENTIFIER, out.toByteArray());
        }
    }

    public static void sendAllAZPlayers(RegisteredServer server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("add-all-az-players");
        out.write(AZVelocity.getInstance().getAzPlayers().size());
        for (UUID uuid : AZVelocity.getInstance().getAzPlayers()) {
            out.writeUTF(uuid.toString());
        }
        server.sendPluginMessage(IDENTIFIER, out.toByteArray());
    }

    public static void sendClearAZPlayers(ProxyServer proxy) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("clear-az-players");
        for (RegisteredServer server : proxy.getAllServers()) {
            server.sendPluginMessage(IDENTIFIER, out.toByteArray());
        }
    }
}
