package fr.mathip.azplugin.bungee;

import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.SneakyThrows;

public class HandshakeFix implements Listener {
    private static final Pattern AZ_HOSTNAME_PATTERN = Pattern.compile("\u0000(PAC[0-9A-F]{5})\u0000");

    private static final Class<?> initialHandlerClass;
    private static final Method getExtraDataInHandshakeMethod;

    static {
        try {
            initialHandlerClass = Class.forName("net.md_5.bungee.connection.InitialHandler");
            getExtraDataInHandshakeMethod = initialHandlerClass.getDeclaredMethod("getExtraDataInHandshake");
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            throw new RuntimeException("Failed to initialize reflection", ex);
        }
    }

    @SneakyThrows(ReflectiveOperationException.class)
    private String getExtraDataInHandshake(PendingConnection connection) {
        Object initialHandler = initialHandlerClass.cast(connection);
        String extraDataInHandshake = (String) getExtraDataInHandshakeMethod.invoke(initialHandler);
        return extraDataInHandshake;
    }

    @EventHandler
    public void onPlayerHandshake(PlayerHandshakeEvent event) {
        Matcher m = AZ_HOSTNAME_PATTERN.matcher(getExtraDataInHandshake(event.getConnection()));
        if (m.find()) {
            event.getHandshake().setHost(event.getHandshake().getHost() + "\u0002" + m.group(1) + "\u0002");
        }
    }
}
