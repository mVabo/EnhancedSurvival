package me.mvabo.enchantedsurvival.modules;

import me.mvabo.enchantedsurvival.EnchantedSurvival;
import me.mvabo.enchantedsurvival.PlayerStats;
import me.mvabo.enhancedcore.files.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Array;
import java.util.*;

public class Abilities implements Listener {

    //Declare variables

    PlayerData stats;
    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);

    boolean abilitiesEnabled = plugin.getConfig().getBoolean("abilitites");

    public Map<String, Integer[]> abilities = new HashMap<String, Integer[]>();
    Integer[] data;

    public Abilities(PlayerData stats) {
        this.stats = stats;
    }

    //Add functions

    public void addMining(Player p) {
        saveAbilities(p, "mining");
    }

    public void addWoodcutting(Player p) {
        saveAbilities(p, "woodcutting");
    }

    public void addMelee(Player p) {
        saveAbilities(p, "melee");
    }

    //Listeners

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        Integer[] newData = new Integer[3];
        newData[0] = 0;
        newData[1] = 0;
        newData[2] = 0;
        if(abilities.containsKey(uuid)) {
            return;
        } else {
            abilities.put(uuid, newData);
        }
    }

    @EventHandler
    public void onPlayerMine(BlockBreakEvent e) {
        Player p = e.getPlayer();
        String uuid = e.getPlayer().getUniqueId().toString();
        Material m = e.getBlock().getType();
        if(abilitiesEnabled && isMiningBlock(m)) {
            if(abilities.containsKey(uuid)) {
                Integer[] ints = abilities.get(uuid);
                int level = ints[0];
                if(level == 1) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level == 2) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level ==3) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level == 4) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level == 5) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                }
            }
        } else if (abilitiesEnabled && isWoodenBlock(m)) {
            if(abilities.containsKey(uuid)) {
                Integer[] ints = abilities.get(uuid);
                int level = ints[1];
                if(level == 1) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level == 2) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level ==3) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level == 4) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                } else if (level == 5) {
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                    e.getBlock().getDrops().add(new ItemStack(m));
                }
            }
        }
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            String uuid = p.getUniqueId().toString();
            if(abilitiesEnabled && abilities.containsKey(uuid)) {
                Integer[] ints = abilities.get(uuid);
                int level = ints[2];
                if(level == 1) {
                    e.setDamage(e.getDamage()*1.2);
                } else if (level == 2) {
                    e.setDamage(e.getDamage()*1.3);
                } else if (level ==3) {
                    e.setDamage(e.getDamage()*1.6);
                } else if (level == 4) {
                    e.setDamage(e.getDamage()*2.0);
                } else if (level == 5) {
                    e.setDamage(e.getDamage()*2.5);
                }
            }
        }
    }

    public boolean isMiningBlock(Material m) {
        boolean miningBlock = false;

        return miningBlock;
    }

    public boolean isWoodenBlock(Material m) {
        boolean woodenBlock = false;

        return woodenBlock;
    }

    //Save

    public void saveAbilities(Player p, String type) {
        String uuid = p.getUniqueId().toString();
        data = abilities.get(uuid);
        if(type.equalsIgnoreCase("mining")) {
            data[0] += 1;
        } else if (type.equalsIgnoreCase("woodcutting")) {
            data[1] += 1;
        } else if (type.equalsIgnoreCase("melee")) {
            data[2] += 1;
        }
        abilities.put(uuid, data);
    }

    public void saveAbs() {
        for(Map.Entry<String, Integer[]> entry : abilities.entrySet()) {
            this.stats.getPlayerData().set("data." + entry.getKey(), entry.getValue());
        }
        this.stats.saveDataConfig();
    }

    public void restoreAbs() {
        stats.getPlayerData().getConfigurationSection("data").getKeys(false).forEach(key ->{
            Integer[] content = ((List<Integer>) stats.getPlayerData().get("data." + key)).toArray(new Integer[0]);
            abilities.put(key, content);
        });
    }
}
