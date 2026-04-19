package fr.mathip.azplugin.bukkit.api;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.AZEntity;
import fr.mathip.azplugin.bukkit.entity.AZPlayer;

public class AZAPI {

    /**
     * Retrieves an AZPlayer wrapper for the given Player.
     *
     * @param player the Bukkit Player to look up
     * @return the AZPlayer wrapper, or null if not found
     */
    public static AZPlayer getPlayer(Player player) {
        return Main.getAZManager().getPlayer(player);
    }

    /**
     * Returns a list of all AZPlayers.
     *
     * @return list of all AZPlayers
     */
    public static List<AZPlayer> getPlayers() {
        return Main.getAZManager().getAZPlayers();
    }

    /**
     * Retrieves an AZEntity wrapper for the given Entity.
     * Returns null if the entity is not tracked.
     *
     * @param entity the Bukkit Entity to look up
     * @return the AZEntity wrapper, or null if not tracked
     */
    public static AZEntity getEntityOrNull(Entity entity) {
        return Main.getAZManager().getEntityOrNull(entity);
    }

    /**
     * Retrieves an AZEntity wrapper for the given Entity.
     * Creates a new AZEntity if not already tracked.
     *
     * @param entity the Bukkit Entity to look up
     * @return the AZEntity wrapper
     */
    public static AZEntity getEntity(Entity entity) {
        return Main.getAZManager().getEntity(entity);
    }

    /**
     * Returns a list of all tracked AZEntities.
     *
     * @return list of all AZEntities
     */
    public static List<AZEntity> getEntities() {
        return Main.getAZManager().getEntyties();
    }
}
