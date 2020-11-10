package me.mvabo.enchantedsurvival;

import me.mvabo.enchantedsurvival.commands.ES;
import me.mvabo.enchantedsurvival.commands.ESTabCompleter;
import me.mvabo.enchantedsurvival.modules.*;
import me.mvabo.enchantedsurvival.modules.abilities.AbilityGUI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public final class EnchantedSurvival extends JavaPlugin {

    public PlayerStats stats;

    //Establish all modules
    Abilities abilitiesClass;
    Thirst thirst;
    PotionEnchants potions;
    Artifacts artifacts;
    AbilityGUI aGui;
    Scoreboard board;
    CommandExecutor cmd;
    Blood blood;
    Ignite ignite;

    @Override
    public void onEnable() {
        //Default config + stats file
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        //this.stats = new PlayerStats();

        abilitiesClass = new Abilities(stats);
        thirst = new Thirst();
        potions = new PotionEnchants();
        artifacts = new Artifacts();
        aGui = new AbilityGUI(abilitiesClass);
        board = new Scoreboard(thirst, artifacts, abilitiesClass);
        cmd = new ES(stats, abilitiesClass, aGui, board);
        blood = new Blood();
        ignite = new Ignite();

        //Check if es is enabled
        boolean enabled = getConfig().getBoolean("es-enabled");
        boolean longerDays = getConfig().getBoolean("longerDays");
        List<String> worlds = getConfig().getStringList("worlds");

        if (worlds.isEmpty()) {
            worlds.add("world");
        }

        //Register listeners
        if(enabled) {
            if (getConfig().getBoolean("abilities")) {
                Bukkit.getPluginManager().registerEvents(aGui, this);
                Bukkit.getPluginManager().registerEvents(abilitiesClass, this);
            }
            if (getConfig().getBoolean("thirst")) {
                Bukkit.getPluginManager().registerEvents(thirst, this);
            }
            Bukkit.getPluginManager().registerEvents(board, this);
            if (getConfig().getBoolean("artifacts")) {
                Bukkit.getPluginManager().registerEvents(artifacts, this);
            }
            if (getConfig().getBoolean("potionEnchants")) {
                Bukkit.getPluginManager().registerEvents(potions, this);
            }
            if (getConfig().getBoolean("ignite")) {
                Bukkit.getPluginManager().registerEvents(ignite, this);
            }
            if (getConfig().getBoolean("blood")) {
                Bukkit.getPluginManager().registerEvents(blood, this);
            }
        }

        //Abilites
        if(getConfig().getBoolean("abilities") && stats.getPlayerStats().contains("data")) {
            abilitiesClass.restoreAbs();
        }

        //Register Commands
        this.getCommand("es").setExecutor(cmd);
        getCommand("es").setTabCompleter(new ESTabCompleter());

        //Schedule task
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                board.updateScoreboard();
            }
        }, 0L, 60L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(longerDays) {
                    try {
                        World tempworld = Bukkit.getWorld(worlds.get(0));
                        tempworld.setFullTime(tempworld.getFullTime()-2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("No worlds have been added in the active worlds list, if you dont use the standard overworld-world, add the name of your world");
                    }

                }
            }
        }, 10L, /* 600 */3L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                potions.addPotionEffectsArmor();
            }
        }, 0L, 20l);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(!abilitiesClass.abilities.isEmpty()) {
            abilitiesClass.saveAbs();
        }
    }
}
