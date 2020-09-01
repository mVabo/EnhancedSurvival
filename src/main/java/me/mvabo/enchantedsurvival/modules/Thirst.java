package me.mvabo.enchantedsurvival.modules;

import me.mvabo.enchantedsurvival.EnchantedSurvival;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static me.mvabo.enchantedsurvival.utilities.Utils.exists;

public class Thirst implements Listener {

    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);

    //Declare lists and variables
    public HashMap<Player, Integer> thirst = new HashMap<>();
    List<String> worlds = plugin.getConfig().getStringList("worlds");

    //Check if thirst is enabled
    boolean thirstEnabled = plugin.getConfig().getBoolean("thirst");

    //Make random available
    Random rand = new Random();

    //Vital functions

    public void sendThirstMessage(int value, Player p, boolean add) {
        if(value <= 0){
            value = 0;
        }
        if(value >=100) {
            value = 100;
        }

        thirst.put(p, value);

        if(add || value % 5 ==0) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.BOLD + "" + ChatColor.BLUE + "-(" + value + ")-"));
        }
    }

    public void decreaseThirst(Player p) {
        HashMap<Player, Integer> players = new HashMap<>(thirst);
        if(players.containsKey(p)) {
            if(worlds.contains(p.getWorld().getName()) && p != null && p.getGameMode().equals(GameMode.SURVIVAL)) {
                if(p.isOnline()) {
                    int value = players.get(p);
                    value -= 1;
                    sendThirstMessage(value, p, false);
                    ThirstEffects();
                }
            }
        }
    }

    public void ThirstEffects() {
        HashMap<Player, Integer> players = new HashMap<Player, Integer>(thirst);
        for(Player p : players.keySet()) {
            if(worlds.contains(p.getWorld().getName())) {
                if(p.getGameMode().equals(GameMode.SURVIVAL) && p != null && p.isOnline()) {
                    int value = players.get(p);
                    if(value < 20)  {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0, false, false));
                    }
                    if(value < 15) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 14, false, false));
                    }
                    if(value < 5) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 0, false, false));
                        if(rand.nextInt(3) == 0) {
                            p.damage(1);
                        }
                    }
                }
            }
        }
    }

    //Listeners

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(thirstEnabled) {
            if(exists(p)) {
                if(thirst.containsKey(p.getName())) {

                }
            }
        }

    }

    public void playerJoin() {

    }

    @EventHandler
    public void newPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(thirstEnabled) {
            if(!thirst.containsKey(p.getName())) {
                thirst.put(p, 100);
            }
        }
    }

    @EventHandler
    public void playerDrink(PlayerItemConsumeEvent e) {
        ItemStack i = e.getItem();
        Player p = e.getPlayer();
        if(worlds.contains(p.getWorld().getName()) && thirstEnabled) {
            if(thirst.containsKey(p)) {
                int value = thirst.get(p);
                Material m = i.getType();
                if(m == Material.POTION || m == Material.MELON_SLICE || m == Material.MILK_BUCKET || m == Material.APPLE || m == Material.MUSHROOM_STEW
                        || m == Material.RABBIT_STEW || m == Material.CARROT || m == Material.GOLDEN_APPLE || m == Material.ENCHANTED_GOLDEN_APPLE || m == Material.SWEET_BERRIES
                        || m == Material.BEETROOT_SOUP || m == Material.BEETROOT) {
                    if(m == Material.POTION) {
                        value += 40;
                    }
                    else if(m == Material.MELON_SLICE || m == Material.APPLE || m == Material.GOLDEN_APPLE) {
                        value += 25;
                    }
                    else if(m == Material.MILK_BUCKET || m == Material.MUSHROOM_STEW || m == Material.BEETROOT_SOUP || m == Material.RABBIT_STEW) {
                        value += 20;
                    }
                    else if(m == Material.CARROT || m == Material.SWEET_BERRIES || m == Material.BEETROOT) {
                        value += 10;
                    }
                    else if(m == Material.ENCHANTED_GOLDEN_APPLE) {
                        value += 100;
                    }
                }
                sendThirstMessage(value, p, true);
            }
        }
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(worlds.contains(p.getWorld().getName()) && thirstEnabled) {
            if(thirst.containsKey(p)){
                thirst.put(p, 100);
            }
        }
    }

    @EventHandler
    public void playerDrinkLake(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(worlds.contains(p.getWorld().getName()) && thirstEnabled) {
            if(p.isSneaking()) {
                if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if(e.getClickedBlock().getRelative(e.getBlockFace()).getType() == Material.WATER) {
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, (float) 1.3);
                        int newThirstValue = thirst.get(p)+5;
                        sendThirstMessage(newThirstValue, p, true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(rand.nextDouble() < 0.005 && thirstEnabled) {
            decreaseThirst(p);
        }
    }
}
