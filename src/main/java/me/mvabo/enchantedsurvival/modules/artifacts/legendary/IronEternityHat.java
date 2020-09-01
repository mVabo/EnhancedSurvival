package me.mvabo.enchantedsurvival.modules.artifacts.legendary;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IronEternityHat {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.IRON_HELMET);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&6Eternal Iron Armor Piece"));
        im.setLore(Arrays.asList("Iron Eternity Hat"));
        im.setUnbreakable(true);
        i.setItemMeta(im);
        return i;
    }
}
