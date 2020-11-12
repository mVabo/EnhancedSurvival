package me.mvabo.enchantedsurvival;

import me.mvabo.enchantedsurvival.commands.ES;
import me.mvabo.enchantedsurvival.commands.ESTabCompleter;
import me.mvabo.enchantedsurvival.modules.*;
import me.mvabo.enchantedsurvival.modules.abilities.AbilityGUI;
import me.mvabo.enhancedcore.EnhancedCore;
import me.mvabo.enhancedcore.files.Config;
import me.mvabo.enhancedcore.files.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

import static me.mvabo.enhancedcore.files.Config.*;

public final class EnchantedSurvival extends JavaPlugin {

    public PlayerData data;

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
    Config c;

    @Override
    public void onEnable() {
        //Default config + stats file
        //getConfig().options().copyDefaults(true);
        //saveDefaultConfig();
        this.data = new PlayerData();

        abilitiesClass = new Abilities(data);
        thirst = new Thirst();
        potions = new PotionEnchants();
        artifacts = new Artifacts();
        aGui = new AbilityGUI(abilitiesClass);
        board = new Scoreboard(thirst, artifacts, abilitiesClass);
        cmd = new ES(data, abilitiesClass, aGui, board);
        blood = new Blood();
        ignite = new Ignite();

        //Check if es is enabled
        boolean enabled = true;
        boolean longerDays = thirstEnabled;
        List<String> worlds = survivalWorlds;

        if (worlds.isEmpty()) {
            worlds.add("world");
        }

        //Register listeners
        if(enabled) {
            if (abilitiesEnabled) {
                Bukkit.getPluginManager().registerEvents(aGui, this);
                Bukkit.getPluginManager().registerEvents(abilitiesClass, this);
            }
            if (thirstEnabled) {
                Bukkit.getPluginManager().registerEvents(thirst, this);
            }
            Bukkit.getPluginManager().registerEvents(board, this);
            if (artifactsEnabled) {
                Bukkit.getPluginManager().registerEvents(artifacts, this);
            }
            if (potionEnchantsEnabled) {
                Bukkit.getPluginManager().registerEvents(potions, this);
            }
            if (igniteEnabled) {
                Bukkit.getPluginManager().registerEvents(ignite, this);
            }
            if (bloodEnabled) {
                Bukkit.getPluginManager().registerEvents(blood, this);
            }
        }

        //Abilites
        if(abilitiesEnabled && data.getPlayerData().contains("data")) {
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

    public void getConfigs(Config config) { this.c = config; }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(!abilitiesClass.abilities.isEmpty()) {
            abilitiesClass.saveAbs();
        }
    }
}
