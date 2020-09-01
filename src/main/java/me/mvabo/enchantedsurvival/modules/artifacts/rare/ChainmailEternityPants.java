package me.mvabo.enchantedsurvival.modules.artifacts.rare;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ChainmailEternityPants {
    public ItemStack make() {
        ItemStack i = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize("&1Eternal Chainmail Armor Piece"));
        im.setLore(Arrays.asList("Chainmail Eternity Pants"));
        im.setUnbreakable(true);
        i.setItemMeta(im);
        return i;
    }
}
