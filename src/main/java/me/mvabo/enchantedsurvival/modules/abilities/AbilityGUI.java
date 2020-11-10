package me.mvabo.enchantedsurvival.modules.abilities;

import me.mvabo.enchantedsurvival.modules.Abilities;
import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class AbilityGUI implements Listener {

    Inventory inv;
    Abilities a;

    public AbilityGUI(Abilities a) {
        this.a = a;
    }

    public void openGUI(Player p) {
        inv = Bukkit.createInventory(p, 27, "Abilities");
        initializeItems();
        p.openInventory(inv);
    }

    public void initializeItems() {
        ItemStack mining = createGUIItem(Material.GREEN_WOOL, "Mining", "Upgrade mining ability");
        ItemStack woodcutting = createGUIItem(Material.GREEN_WOOL, "Wood Cutting", "Upgrade woodcutting ability");
        ItemStack melee = createGUIItem(Material.GREEN_WOOL, "Melee", "Upgrade melee ability");

        inv.setItem(0, mining);
        inv.setItem(9, woodcutting);
        inv.setItem(18, melee);
    }

    public ItemStack createGUIItem(Material m, String name, String lore) {
        ItemStack i;

        i = new ItemStack(m);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        i.setItemMeta(im);

        return i;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack i = e.getCurrentItem();
        if (e.getClickedInventory().getType().name().equalsIgnoreCase("abilities")) {

        }
        Player p = (Player) e.getWhoClicked();
        if(i!=null) {
            //for(int integer = 0; lores.size() < integer; integer++) {
            if (i.hasItemMeta()) {
                if (i.getItemMeta().hasLore()) {
                    List<String> lores = i.getItemMeta().getLore();
                    if (lores.get(0).equalsIgnoreCase("Upgrade mining ability")) {
                        if (p.getLevel() >= 50) {
                            a.addMining(p);
                            e.setCancelled(true);
                            p.setLevel(p.getLevel() - 50);
                            p.closeInventory();
                        } else {
                            p.closeInventory();
                            p.sendMessage(Utils.colorize("&4You don't have enough experience"));
                        }
                    } else if (lores.get(0).equalsIgnoreCase("Upgrade woodcutting ability")) {
                        if (p.getLevel() >= 50) {
                            a.addWoodcutting(p);
                            e.setCancelled(true);
                            p.closeInventory();
                            p.setLevel(p.getLevel() - 50);
                        } else {
                            p.closeInventory();
                            p.sendMessage(Utils.colorize("&4You don't have enough experience"));
                        }
                    } else if (lores.get(0).equalsIgnoreCase("Upgrade melee ability")) {
                        if (p.getLevel() >= 50) {
                            a.addMelee(p);
                            e.setCancelled(true);
                            p.closeInventory();
                            p.setLevel(p.getLevel() - 50);
                        } else {
                            p.closeInventory();
                            p.sendMessage(Utils.colorize("&4You don't have enough experience"));
                        }
                    }
                }
            }
           //}
        } else {
            e.setCancelled(true);
        }
        if(i == null || i.getType().equals(Material.AIR)) {
            //e.setCancelled(true);
            //p.closeInventory();
            return;
        }
    }
}
