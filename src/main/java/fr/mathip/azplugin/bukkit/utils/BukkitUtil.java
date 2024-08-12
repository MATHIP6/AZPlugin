package fr.mathip.azplugin.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BukkitUtil {
    private static final Method PLAYER_ADDCHANNEL_METHOD;
    private static final Pattern SERVER_VERSION;

    static {
        SERVER_VERSION = Pattern.compile("\\(MC: (?<major>[0-9]{1,3})\\.(?<minor>[0-9]{1,3})(?:\\.(?<patch>[0-9]{1,3}))?\\)");
        final String ocbPackage = Bukkit.getServer().getClass().getName().replaceAll("\\.[^.]+$", "");
        try {
            final Class<?> playerClass = Class.forName(ocbPackage + ".entity.CraftPlayer");
            final Method addChannelMethod = playerClass.getDeclaredMethod("addChannel", String.class);
            addChannelMethod.setAccessible(true);
            PLAYER_ADDCHANNEL_METHOD = addChannelMethod;
        }
        catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static int findServerVersion() {
        final Matcher m = BukkitUtil.SERVER_VERSION.matcher(Bukkit.getVersion());
        if (m.find()) {
            final int major = Integer.parseInt(m.group("major"));
            final int minor = Integer.parseInt(m.group("minor"));
            final int patch = (m.group("patch") != null) ? Integer.parseInt(m.group("patch")) : 0;
            return major * 1000000 + minor * 1000 + patch;
        }
        throw new RuntimeException("Unable to detect server version! Bukkit.getVersion()=" + Bukkit.getVersion());
    }

    public static void addChannel(final Player player, final String channel) {
        try {
            BukkitUtil.PLAYER_ADDCHANNEL_METHOD.invoke(player, channel);
        }
        catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private BukkitUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
