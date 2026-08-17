package fr.mathip.azplugin.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.DataOutput;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BukkitUtil {
    private static final Method PLAYER_ADDCHANNEL_METHOD;
    private static final Pattern SERVER_VERSION;

    private static final String CRAFT_PACKAGE = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_VERSION = CRAFT_PACKAGE.substring(CRAFT_PACKAGE.lastIndexOf('.') + 1);

    static {
        SERVER_VERSION = Pattern
                .compile("\\(MC: (?<major>[0-9]{1,3})\\.(?<minor>[0-9]{1,3})(?:\\.(?<patch>[0-9]{1,3}))?\\)");
        final String ocbPackage = Bukkit.getServer().getClass().getName().replaceAll("\\.[^.]+$", "");
        try {
            final Class<?> playerClass = Class.forName(ocbPackage + ".entity.CraftPlayer");
            final Method addChannelMethod = playerClass.getDeclaredMethod("addChannel", String.class);
            addChannelMethod.setAccessible(true);
            PLAYER_ADDCHANNEL_METHOD = addChannelMethod;
        } catch (ReflectiveOperationException ex) {
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
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object getItemNBT(ItemStack itemStack) {

        try {
            Class<?> craftItemStackClass = Class
                    .forName("org.bukkit.craftbukkit." + NMS_VERSION +
                            ".inventory.CraftItemStack");
            Method asNMSCopy = craftItemStackClass.getMethod("asNMSCopy",
                    ItemStack.class);
            Object nmsItemStack = asNMSCopy.invoke(null, itemStack);
            Class<?> nmsItemStackClass = nmsItemStack.getClass();

            return nmsItemStackClass.getMethod("getTag").invoke(nmsItemStack);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException
                | ClassNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeNBT(Object nbtTagCompound, DataOutput out) {
        try {
            Class<?> nbtCompressStreamToolsClass = Class
                    .forName("net.minecraft.server." + NMS_VERSION + ".NBTCompressedStreamTools");

            Class<?> nbtTagCompoundClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".NBTTagCompound");

            Method writeMethod = nbtCompressStreamToolsClass.getMethod("a", nbtTagCompoundClass, DataOutput.class);
            writeMethod.invoke(null, nbtTagCompound, out);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private BukkitUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
