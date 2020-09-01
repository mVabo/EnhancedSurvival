package me.mvabo.enchantedsurvival.modules.artifacts.rare;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EternalBow {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.BOW);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&1Eternal Bow"));
        im.setLore(Arrays.asList("A bow that never brakes!"));
        im.setUnbreakable(true);
        i.setItemMeta(im);
        return i;
    }
}
