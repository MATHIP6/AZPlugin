package fr.mathip.azplugin.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class ConfigMigration {

    public static final int CURRENT_VERSION = 1;

    private final File configFile;
    private final Logger logger;

    public ConfigMigration(File configFile, Logger logger) {
        this.configFile = configFile;
        this.logger = logger;
    }

    public void migrate(FileConfiguration config) {
        int currentVersion = config.getInt("config-version", 0);

        if (currentVersion >= CURRENT_VERSION) {
            return;
        }

        logger.info("Migration de la config : version " + currentVersion + " -> " + CURRENT_VERSION);
        backupConfig();

        for (int v = currentVersion; v < CURRENT_VERSION; v++) {
            migrateFrom(config, v);
        }

        config.set("config-version", CURRENT_VERSION);

        try {
            config.save(configFile);
            logger.info("Config migratée avec succes !");
        } catch (IOException e) {
            logger.severe("Erreur lors de la sauvegarde de la config migrée : " + e.getMessage());
        }
    }

    private void migrateFrom(FileConfiguration config, int fromVersion) {
        switch (fromVersion) {
            case 0:
                migrateFromV0(config);
                break;
            default:
                logger.warning("Pas de migration trouvée pour la version " + fromVersion);
                break;
        }
    }

    private void migrateFromV0(FileConfiguration config) {
        logger.info("Migration du fichier de configuration v0 -> v1...");

        if (!config.contains("config-version")) {
            config.set("config-version", 1);
        }
        config.getConfigurationSection("module").set("cosmetic-equipment", false);
        config.set("head-api-url", "http://head-api.mathip.dev");

    }

    private void backupConfig() {
        if (!configFile.exists()) {
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File backup = new File(configFile.getParent(), "config.yml.bak." + timestamp);

        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            config.save(backup);
            logger.info("Backup crée : " + backup.getName());
        } catch (IOException e) {
            logger.severe("Erreur lors de la creation du backup : " + e.getMessage());
        }
    }
}
