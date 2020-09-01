package me.mvabo.enchantedsurvival.modules.artifacts.legendary;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IronEternityChestplate {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&6Eternal Iron Armor Piece"));
        im.setLore(Arrays.asList("Iron Eternity Chestplate"));
        im.setUnbreakable(true);
        i.setItemMeta(im);
        return i;
    }
}
