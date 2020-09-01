package me.mvabo.enchantedsurvival.utilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

public class Utils {
    public static boolean exists(Entity e) {
        if(e == null) {
            return false;
        }
        if(e.isDead()) {
            return false;
        }

        return true;
    }

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
