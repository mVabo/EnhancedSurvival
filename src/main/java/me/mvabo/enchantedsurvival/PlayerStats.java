package me.mvabo.enchantedsurvival;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PlayerStats {
    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);

    private FileConfiguration playerStats;
    private File statFile;

    public PlayerStats() {
        saveDefaultStatsTable();
    }

    public void setup() {
        if (!statFile.exists()) {
            statFile = new File(plugin.getDataFolder(), "playerStats.yml");
        }

        playerStats = YamlConfiguration.loadConfiguration(statFile);

        InputStream defaultStream = plugin.getResource("playerStats.yml");
        if(defaultStream != null) {
            YamlConfiguration c = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            playerStats.setDefaults(c);
        }
    }

    public FileConfiguration getPlayerStats() {
        if (this.playerStats == null) {
            setup();
        }
        return playerStats;
    }

    public void saveStatsConfig() {
        if (this.playerStats == null || this.statFile == null) {
            return;
        }
        try {
            this.getPlayerStats().save(this.statFile);
        } catch (IOException e) {
            System.out.println(Color.RED + "Could not save playerStats.yml");
        }
    }

    public void saveDefaultStatsTable() {
        if (this.statFile == null) {
            this.statFile = new File(plugin.getDataFolder(), "playerStats.yml");
        }

        if (!statFile.exists()) {
            plugin.saveResource("playerStats.yml", false);
        }
    }
}
