package me.mvabo.enchantedsurvival.modules.artifacts.common;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NanoTechArmorLevel1 extends ItemStack {

   public ItemStack make() {
      ItemStack i = new ItemStack(Material.IRON_CHESTPLATE);
      ItemMeta im = i.getItemMeta();
      im.setDisplayName(Utils.colorize("&7Nano-Tech Armor"));
      im.setLore(Arrays.asList("Nano-Tech Armor Level: 1"));
      i.setItemMeta(im);
      return i;
   }

}
