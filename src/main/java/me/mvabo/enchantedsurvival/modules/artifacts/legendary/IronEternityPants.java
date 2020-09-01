package me.mvabo.enchantedsurvival.modules.artifacts.legendary;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IronEternityPants {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.IRON_LEGGINGS);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&6Eternal Iron Armor Piece"));
        im.setLore(Arrays.asList("Iron Eternity Pants"));
        im.setUnbreakable(true);
        i.setItemMeta(im);
        return i;
    }
}
