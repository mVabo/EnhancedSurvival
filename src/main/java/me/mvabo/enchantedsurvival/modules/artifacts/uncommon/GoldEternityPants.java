package me.mvabo.enchantedsurvival.modules.artifacts.uncommon;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GoldEternityPants {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.GOLDEN_LEGGINGS);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&2Eternal Gold Armor Piece"));
        im.setLore(Arrays.asList("Gold Eternity Pants"));
        im.setUnbreakable(true);
        i.setItemMeta(im);
        return i;
    }
}
