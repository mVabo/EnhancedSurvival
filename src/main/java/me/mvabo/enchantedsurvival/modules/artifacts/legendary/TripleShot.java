package me.mvabo.enchantedsurvival.modules.artifacts.legendary;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TripleShot {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.MAGENTA_DYE);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&7Triple Shot"));
        im.setLore(Arrays.asList("Shoots 3 arrows at once!"));
        i.setItemMeta(im);
        return i;
    }
}
