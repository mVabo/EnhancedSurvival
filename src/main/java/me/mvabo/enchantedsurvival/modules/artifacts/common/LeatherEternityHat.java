package me.mvabo.enchantedsurvival.modules.artifacts.common;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LeatherEternityHat {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&7Eternal Leather Armor Piece"));
        im.setLore(Arrays.asList("Leather Eternity Hat"));
        im.setUnbreakable(true);
        i.setItemMeta(im);
        return i;
    }
}
