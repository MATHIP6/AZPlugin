package fr.mathip.azplugin.bukkit.entity;

import fr.mathip.azplugin.bukkit.AZManager;
import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.config.ConfigManager;
import fr.mathip.azplugin.bukkit.utils.BukkitUtil;
import fr.mathip.azplugin.bukkit.utils.SchedulerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import pactify.client.api.plprotocol.metadata.ImmutablePactifyModelMetadata;
import pactify.client.api.plprotocol.metadata.PactifyModelMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketAbstractMeta;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;
import pactify.client.api.plsp.packet.client.PLSPPacketPlayerMeta;
import pactify.client.api.plsp.packet.client.PLSPPacketReset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AZPlayer extends AZEntity {
    private static final Pattern AZ_HOSTNAME_PATTERN;
    private final Player player;
    private final Set<Integer> scheduledTasks;
    private boolean joined;
    private int launcherProtocolVersion;

    private PLSPPacketEntityMeta playerMeta;

    static {
        AZ_HOSTNAME_PATTERN = Pattern.compile("[\u0000\u0002]PAC([0-9A-F]{5})[\u0000\u0002]");
    }

    public void init() {
        final List<MetadataValue> hostnameMeta = this.player.getMetadata("AZPlugin:hostname");
        if (!hostnameMeta.isEmpty()) {
            final String hostname = hostnameMeta.get(0).asString();
            final Matcher m = AZPlayer.AZ_HOSTNAME_PATTERN.matcher(hostname);
            if (m.find()) {
                this.launcherProtocolVersion = Math.max(1, Integer.parseInt(m.group(1), 16));
            }
        } else {
            Main.getInstance().getLogger().warning("Unable to verify the launcher of " + this.player.getName()
                    + ": it probably logged when the plugin was disabled!");
        }
        BukkitUtil.addChannel(this.player, "PLSP");
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                Main.getInstance(),
                () -> AZManager.sendPLSPMessage(player, new PLSPPacketReset()),
                10L);
    }

    public void join() {
        this.joined = true;
    }

    public void free() {
        SchedulerUtil.cancelTasks(Main.getInstance(), this.scheduledTasks);
    }

    public boolean hasLauncher() {
        return this.launcherProtocolVersion > 0;
    }

    public int getLauncherProtocolVersion() {
        return this.launcherProtocolVersion;
    }

    public AZPlayer(Player player) {
        super(player);
        this.scheduledTasks = new HashSet<Integer>();
        this.player = player;
        this.playerMeta = new PLSPPacketEntityMeta(player.getEntityId());
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                ConfigManager.getInstance().getConFlags().applyFlags(player);
                ConfigManager.getInstance().applyUIComponents(player);
            }

        }, 1);
    }

    public Player getPlayer() {
        return this.player;
    }

    public Set<Integer> getScheduledTasks() {
        return this.scheduledTasks;
    }

    public boolean isJoined() {
        return this.joined;
    }

    @Override
    protected PLSPPacketAbstractMeta createMetadataPacket() {
        PLSPPacketPlayerMeta playerMeta = new PLSPPacketPlayerMeta(player.getUniqueId());
        playerMeta.setScale(getScale().toPacMetadata());
        playerMeta.setTag(getTag().toPacMetadata());
        playerMeta.setSupTag(getSupTag().toPacMetadata());
        playerMeta.setSubTag(getSubTag().toPacMetadata());
        playerMeta.setModel(getModel().toPacMetadata());
        playerMeta.setOpacity(getOpacity());
        return playerMeta;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AZPlayer)) {
            return false;
        }
        final AZPlayer other = (AZPlayer) o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$player = this.getPlayer();
        final Object other$player = other.getPlayer();
        Label_0102: {
            if (this$player == null) {
                if (other$player == null) {
                    break Label_0102;
                }
            } else if (this$player.equals(other$player)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$scheduledTasks = this.getScheduledTasks();
        final Object other$scheduledTasks = other.getScheduledTasks();
        if (this$scheduledTasks == null) {
            if (other$scheduledTasks == null) {
                return this.isJoined() == other.isJoined()
                        && this.getLauncherProtocolVersion() == other.getLauncherProtocolVersion();
            }
        } else if (this$scheduledTasks.equals(other$scheduledTasks)) {
            return this.isJoined() == other.isJoined()
                    && this.getLauncherProtocolVersion() == other.getLauncherProtocolVersion();
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AZPlayer;
    }

    @Override
    public int hashCode() {
        int result = 1;
        final Object $player = this.getPlayer();
        result = result * 59 + (($player == null) ? 43 : $player.hashCode());
        final Object $scheduledTasks = this.getScheduledTasks();
        result = result * 59 + (($scheduledTasks == null) ? 43 : $scheduledTasks.hashCode());
        result = result * 59 + (this.isJoined() ? 79 : 97);
        result = result * 59 + this.getLauncherProtocolVersion();
        return result;
    }

    @Override
    public String toString() {
        return "AZPlayer(player=" + this.getPlayer() + ", scheduledTasks=" + this.getScheduledTasks() + ", joined="
                + this.isJoined() + ", launcherProtocolVersion=" + this.getLauncherProtocolVersion() + ")";
    }

    public static boolean hasAZLauncher(final Player player) {
        return Main.getAZManager().getPlayer(player).hasLauncher();
    }

    public PLSPPacketEntityMeta getPlayerMeta() {
        return playerMeta;
    }

    public void updateMeta() {
        // This will be removed in the future
        /*
         * AZManager.sendPLSPMessage(player, this.playerMeta);
         * for (Player pl : this.player.getWorld().getPlayers()) {
         * AZManager.sendPLSPMessage(pl, this.playerMeta);
         * }
         */
    }
}
