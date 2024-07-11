package fr.mathip.azplugin.bungee;

import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandshakeFix implements Listener {
    private static final Pattern AZ_HOSTNAME_PATTERN = Pattern.compile("\u0000(PAC[0-9A-F]{5})\u0000");

    @EventHandler
    public void onPlayerHandshake(PlayerHandshakeEvent event) {
        InitialHandler con = (InitialHandler) event.getConnection();
        Matcher m = AZ_HOSTNAME_PATTERN.matcher(con.getExtraDataInHandshake());
        if (m.find()) {
            event.getHandshake().setHost(event.getHandshake().getHost() + "\u0002" + m.group(1) + "\u0002");
        }
    }
}
