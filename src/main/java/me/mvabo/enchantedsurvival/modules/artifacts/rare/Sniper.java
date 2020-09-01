package me.mvabo.enchantedsurvival.modules.artifacts.rare;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Sniper {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.BOW);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&7Sniper"));
        im.setLore(Arrays.asList("Shoots arrows like if they are sniper bullets!"));
        i.setItemMeta(im);
        return i;
    }
}
