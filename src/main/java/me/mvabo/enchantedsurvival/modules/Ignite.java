package me.mvabo.enchantedsurvival.modules;

import me.mvabo.enchantedsurvival.EnchantedSurvival;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

public class Ignite implements Listener {

    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);
    Random rand = new Random();

    @EventHandler
    public void onTorchPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.TORCH)) {
            if (rand.nextInt(100) < 3) {
                Location l = e.getBlock().getLocation();
                igniteBlock(l);
            }
        }
    }

    public void igniteBlock(Location l) {
        Location newLoc = l.clone();
        newLoc = newLoc.subtract(1,0, 1);
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                if (isAvailable(newLoc)) {
                    newLoc.getBlock().setType(Material.FIRE);
                    if (rand.nextBoolean()) {
                        return;
                    }
                }
            }
        }
    }

    public boolean isAvailable(Location l) {
        List<String> worlds = (List<String>) plugin.getConfig().getList("worlds");

        if (l.getWorld().getEnvironment().equals(World.Environment.NORMAL) && worlds.contains(l.getWorld().toString())) {
            if (l.getBlock().getType().equals(Material.AIR) || l.getBlock().getType().equals(Material.CAVE_AIR)) {
                return true;
            }
        }
        return false;
    }
}
