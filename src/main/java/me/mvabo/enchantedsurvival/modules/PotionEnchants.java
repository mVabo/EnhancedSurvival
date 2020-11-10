package me.mvabo.enchantedsurvival.modules;

import me.mvabo.enchantedsurvival.EnchantedSurvival;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PotionEnchants implements Listener {

    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);
    List<String> worlds = plugin.getConfig().getStringList("worlds");
    boolean potionEnabled = plugin.getConfig().getBoolean("potionEnchants");
    boolean colorEnabled = plugin.getConfig().getBoolean("color");
    Random rand = new Random();

    @EventHandler
    public void playerShootBow(EntityShootBowEvent e) {
        if(potionEnabled) {
            if(e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                Projectile a = (Projectile) e.getProjectile();
                if(e.getBow().hasItemMeta()) {
                    if(e.getBow().getItemMeta().hasLore()) {
                        int count = 0;
                        a.setMetadata("bowlore", new FixedMetadataValue(plugin, 0));
                        for(String loreline : e.getBow().getItemMeta().getLore()) {
                            if(loreline.charAt(loreline.length()-1) == 'I') {
                                a.setMetadata(("potioneffect" + count), new FixedMetadataValue(plugin, loreline));
                                count++;
                            }
                            if(count == 3) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHitWithPotion(EntityDamageByEntityEvent e) {
        if(potionEnabled) {
            if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
                ItemStack item = ((Player) e.getDamager()).getInventory().getItemInMainHand();
                if(item != null) {
                    if(item.getType() != Material.AIR) {
                        if(item.hasItemMeta()) {
                            if(item.getItemMeta().hasLore()) {
                                doItemEffects(((LivingEntity) e.getEntity()), item.getItemMeta().getLore());
                            }
                        }
                    }
                }
            }
            if(e.getDamager() instanceof Projectile && e.getEntity() instanceof LivingEntity) {
                if(e.getDamager().hasMetadata("bowlore")) {
                    List<String> effects = new ArrayList<String>();
                    Entity a = e.getDamager();
                    if(a.hasMetadata("potioneffect0")) {
                        if(a.getMetadata("potioneffect0").size()>0) {
                            effects.add(a.getMetadata("potioneffect0").get(0).asString());
                        }
                    }
                    if(a.hasMetadata("potioneffect1")) {
                        if(a.getMetadata("potioneffect1").size()>0) {
                            effects.add(a.getMetadata("potioneffect1").get(0).asString());
                        }
                    }
                    if(a.hasMetadata("potioneffect2")) {
                        if(a.getMetadata("potioneffect2").size()>0) {
                            effects.add(a.getMetadata("potioneffect2").get(0).asString());
                        }
                    }
                    doItemEffects(((LivingEntity) e.getEntity()), effects);
                }
            }
        }
    }

    public void doArmorEffects(Player p, List<String> lore) {
        for(String effect : lore) {
            if(effect.length()>0) {
                if(effect.charAt(effect.length()-1) == 'I') {
                    String effectname = ChatColor.stripColor(effect.substring(0, effect.lastIndexOf(" ")));
                    String amplifier = ChatColor.stripColor(effect.substring(effect.lastIndexOf(" ")+1, effect.length()));
                    PotionEffectType pt = null;
                    int ampAmmount = 0;
                    if(amplifier.equals("II")) {
                        ampAmmount = 1;
                    }
                    if(effectname.equals("Haste")) {
                        pt = PotionEffectType.FAST_DIGGING;
                    }
                    else if(effectname.equals("Nausea")) {
                        pt = PotionEffectType.CONFUSION;
                    }
                    else if(effectname.equals("Mining Fatigue")) {
                        pt = PotionEffectType.SLOW_DIGGING;
                    }
                    else if(effectname.equals("Instant Damage")) {
                        pt = PotionEffectType.HARM;
                    }
                    else if(effectname.equals("Instant Health")) {
                        pt = PotionEffectType.HEAL;
                    }
                    else if(effectname.equals("Strength")) {
                        pt = PotionEffectType.INCREASE_DAMAGE;
                    }
                    else if(effectname.equals("Jump Boost")) {
                        pt = PotionEffectType.JUMP;
                    }
                    else if(effectname.equals("Slowness")) {
                        pt = PotionEffectType.SLOW;
                    }
                    else if(effectname.equals("Resistance")) {
                        pt = PotionEffectType.DAMAGE_RESISTANCE;
                    }
                    else {
                        pt = PotionEffectType.getByName((effectname.toUpperCase().replace(" ", "_")));
                    }
                    if(pt != null) {
                        if(pt == PotionEffectType.HEAL) {
                            if(rand.nextInt(20)==0) {
                                addPotionEffectBetter(p, pt, 20, ampAmmount, false, false, false);
                            }
                        }
                        else if(pt.getName().equals("NIGHT_VISION")) {
                            addPotionEffectBetter(p, pt, 20*16, ampAmmount, false, false, false);
                        }
                        else {
                            addPotionEffectBetter(p, pt, 20*5, ampAmmount, false, false, false);
                        }
                    }
                    p.addPotionEffect(new PotionEffect(pt, 1200, 1));
                }
            }
        }
    }

    public void addPotionEffectsArmor() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(potionEnabled) {
                ItemStack[] armor = p.getInventory().getArmorContents();
                for(int count = 0; count < armor.length; count++) {
                    ItemStack armorpiece = armor[count];
                    if(armorpiece != null) {
                        if(armorpiece.getType() != Material.AIR) {
                            if(armorpiece.hasItemMeta()) {
                                if(armorpiece.getItemMeta().hasLore()) {
                                    doArmorEffects(p, armorpiece.getItemMeta().getLore());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void doItemEffects(LivingEntity p, List<String> lore) {
        for(String effect : lore) {
            if(effect.charAt(effect.length()-1) == 'I') {
                String effectname = ChatColor.stripColor(effect.substring(0, effect.lastIndexOf(" ")));
                String amplifier = ChatColor.stripColor(effect.substring(effect.lastIndexOf(" ")+1, effect.length()));
                PotionEffectType pt = null;
                int ampAmmount = 0;
                if(amplifier.equals("II")) {
                    ampAmmount = 1;
                }
                if(effectname.equals("Haste")) {
                    pt = PotionEffectType.FAST_DIGGING;
                }
                else if(effectname.equals("Nausea")) {
                    pt = PotionEffectType.CONFUSION;
                }
                else if(effectname.equals("Mining Fatigue")) {
                    pt = PotionEffectType.SLOW_DIGGING;
                }
                else if(effectname.equals("Instant Damage")) {
                    pt = PotionEffectType.HARM;
                }
                else if(effectname.equals("Instant Health")) {
                    pt = PotionEffectType.HEAL;
                }
                else if(effectname.equals("Strength")) {
                    pt = PotionEffectType.INCREASE_DAMAGE;
                }
                else if(effectname.equals("Jump Boost")) {
                    pt = PotionEffectType.JUMP;
                }
                else if(effectname.equals("Slowness")) {
                    pt = PotionEffectType.SLOW;
                }
                else if(effectname.equals("Resistance")) {
                    pt = PotionEffectType.DAMAGE_RESISTANCE;
                }
                else {
                    pt = PotionEffectType.getByName((effectname.toUpperCase().replace(" ", "_")));
                }
                if(pt != null) {
                    if(pt == PotionEffectType.HARM || pt == PotionEffectType.HEAL) {
                        addPotionEffectBetter(p, pt, 20, ampAmmount, false, false, false);
                    }
                    else {
                        addPotionEffectBetter(p, pt, (15*20), ampAmmount, false, false, false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAnvilPotion(PrepareAnvilEvent event) {
        if(worlds.contains(event.getInventory().getLocation().getWorld().getName()) && potionEnabled) {
            boolean doPotion = false;
            ItemStack item = null;
            ItemStack potion = null;
            for(ItemStack i : event.getInventory().getContents()) {
                if(i!=null) {
                    Material m = i.getType();
                    if(m.name().toLowerCase().contains("potion")) {
                        doPotion=true;
                        potion = i;
                    }
                    else {
                        item = i;
                    }
                }
            }
            if(doPotion==false||item==null) {
                return;
            }
            else {
                ItemStack newItem = item.clone();
                ItemMeta im = newItem.getItemMeta();
                List<String> lore = new ArrayList<String>();
                String effect = getPotionEnchantment(potion);
                if(effect.equals(ChatColor.GRAY + "")) {
                    return;
                }
                if(im.hasLore()) {
                    lore = im.getLore();
                    if(lore.size() > 2) {
                        return;
                    }
                    lore.add("" + getPotionEnchantment(potion));
                    im.setLore(lore);
                }
                else {
                    lore.add("" + getPotionEnchantment(potion));
                    im.setLore(lore);
                }
                newItem.setItemMeta(im);
                //event.getInventory().setRepairCost(25);
                event.setResult(newItem);
                Bukkit.getScheduler().runTaskLater(plugin, () -> event.getInventory().setRepairCost(25), 1);
            }
        }
    }

    public String getPotionEnchantment(ItemStack i) {
        String name = ChatColor.GRAY + "";
        PotionMeta pm = ((PotionMeta) i.getItemMeta());
        if(pm.hasCustomEffects()) {
            if(pm.hasCustomEffect(PotionEffectType.WITHER)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Wither I";
                    }
                    else {
                        name += "Wither II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.FAST_DIGGING)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Haste I";
                    }
                    else {
                        name += "Haste II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Resistance I";
                    }
                    else {
                        name += "Resistance II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.CONFUSION)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Nausea I";
                    }
                    else {
                        name += "Nausea II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.BLINDNESS)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Blindness I";
                    }
                    else {
                        name += "Blindness II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Resistance I";
                    }
                    else {
                        name += "Resistance II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.HUNGER)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Hunger I";
                    }
                    else {
                        name += "Hunger II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.HEALTH_BOOST)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Health Boost I";
                    }
                    else {
                        name += "Health Boost II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.SATURATION)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Saturation I";
                    }
                    else {
                        name += "Saturation II";
                    }
                }
            }
            else if(pm.hasCustomEffect(PotionEffectType.SLOW_DIGGING)) {
                int amp = pm.getCustomEffects().get(0).getAmplifier();
                if(amp == 0 || amp == 1) {
                    if(amp == 0) {
                        name += "Mining Fatigue I";
                    }
                    else {
                        name += "Mining Fatigue II";
                    }
                }
            }
        }
        else {
            PotionEffectType pt = pm.getBasePotionData().getType().getEffectType();
            boolean upgraded = pm.getBasePotionData().isUpgraded();
            if(pt == PotionEffectType.FIRE_RESISTANCE) {
                name += "Fire Resistance";
            }
            else if(pt == PotionEffectType.HARM) {
                name += "Instant Damage";
            }
            else if(pt == PotionEffectType.HEAL) {
                name += "Instant Health";
            }
            else if(pt == PotionEffectType.INCREASE_DAMAGE) {
                name += "Strength";
            }
            else if(pt == PotionEffectType.INVISIBILITY) {
                name += "Invisibility";
            }
            else if(pt == PotionEffectType.JUMP) {
                name += "Jump Boost";
            }
            else if(pt == PotionEffectType.LEVITATION) {
                name += "Levitation";
            }
            else if(pt == PotionEffectType.LUCK) {
                name += "Luck";
            }
            else if(pt == PotionEffectType.NIGHT_VISION) {
                name += "Night Vision";
            }
            else if(pt == PotionEffectType.POISON) {
                name += "Poison";
            }
            else if(pt == PotionEffectType.REGENERATION) {
                name += "Regeneration";
            }
            else if(pt == PotionEffectType.SLOW) {
                name += "Slowness";
            }
            else if(pt == PotionEffectType.SLOW_FALLING) {
                name += "Slow Falling";
            }
            else if(pt == PotionEffectType.SPEED) {
                name += "Speed";
            }
            else if(pt == PotionEffectType.WATER_BREATHING) {
                name += "Water Breathing";
            }
            else if(pt == PotionEffectType.WEAKNESS) {
                name += "Weakness";
            }
            if(upgraded == false) {
                name += " I";
            }
            else {
                name += " II";
            }
        }
        return name;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerClickInAnvilPotion(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(worlds.contains(p.getWorld().getName()) && colorEnabled) {
            if(e.getClickedInventory()!=null && p.getLevel()>=25) {
                if(e.getClickedInventory().getType() == InventoryType.ANVIL) {
                    if(e.getSlot() == 2) {
                        ItemStack one = e.getClickedInventory().getItem(0);
                        ItemStack two = e.getClickedInventory().getItem(1);
                        if(two == null || one == null) {
                            return;
                        }
                        else {
                            int potioncount = 0;
                            if(two.getType().name().toLowerCase().contains("potion")) {
                                potioncount++;
                            }
                            if(one.getType().name().toLowerCase().contains("potion")) {
                                potioncount++;
                            }
                            if(potioncount == 1) {
                                if(e.getCursor()==null) {
                                    e.setCursor(e.getClickedInventory().getItem(2));
                                }
                                else if(e.getCursor().getType() == Material.AIR) {
                                    e.setCursor(e.getClickedInventory().getItem(2));
                                }
                                else {
                                    p.getInventory().addItem(e.getClickedInventory().getItem(2));
                                }
                                e.getClickedInventory().setItem(0, new ItemStack(Material.AIR));
                                e.getClickedInventory().setItem(1, new ItemStack(Material.AIR));
                                e.getClickedInventory().setItem(2, new ItemStack(Material.AIR));
                                p.setLevel(p.getLevel()-25);
                                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 1, 1);
                            }
                            else {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void addPotionEffectBetter(LivingEntity e, PotionEffectType pt, int duration, int amp, boolean ambient, boolean hp, boolean additive) {
        if(e.hasPotionEffect(pt)) {
            int level = amp;
            if(additive == true) {
                level = e.getPotionEffect(pt).getAmplifier() + (amp+1);
            }
            if(level < 200) {
                e.removePotionEffect(pt);
                e.addPotionEffect(new PotionEffect(pt, duration, level, ambient, hp));
            }
            else {
                e.removePotionEffect(pt);
                e.addPotionEffect(new PotionEffect(pt, duration, 200, ambient, hp));
            }
        }
        else {
            e.addPotionEffect(new PotionEffect(pt, duration, amp, ambient, hp));
        }
    }
}
