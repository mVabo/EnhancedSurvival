package me.mvabo.enchantedsurvival.modules;

import me.mvabo.enchantedsurvival.EnchantedSurvival;
import me.mvabo.enchantedsurvival.modules.artifacts.common.*;
import me.mvabo.enchantedsurvival.modules.artifacts.legendary.*;
import me.mvabo.enchantedsurvival.modules.artifacts.rare.*;
import me.mvabo.enchantedsurvival.modules.artifacts.uncommon.*;
import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Artifacts implements Listener {

    Random rand = new Random();

    List<Player> activatedPlayers = new ArrayList<Player>();
    List<String> playersInArtifact = new ArrayList<String>();

    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);

    @EventHandler
    public void playerActivateArtifact(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        Entity attacker = e.getEntity().getKiller();
        if(entity.getWorld().getEnvironment().equals(World.Environment.NETHER) && attacker instanceof Player) {
            Player p = (Player) attacker;
            if(activatedPlayers.contains(p)) {
                if(rand.nextInt(10) < 10) {
                    try {
                        entity.getWorld().dropItem(entity.getLocation(), makeArtifact());
                    } catch (Exception ex) {

                    }
                }
            }
        }
    }

    @EventHandler
    public void activatePlayer(PlayerAdvancementDoneEvent e) {
        Player p = e.getPlayer();
        Advancement a = e.getAdvancement();
        String advName = a.getKey().toString();
        if(advName.equalsIgnoreCase("minecraft:nether/create_beacon")) {
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 3, 0);
            p.sendTitle(ChatColor.DARK_RED + "Do Y" + ChatColor.MAGIC + "o" + ChatColor.RESET + "" + ChatColor.DARK_RED + "U deSirE", "P" + ChatColor.MAGIC + "o" + ChatColor.RESET + "" + ChatColor.DARK_RED + "wER?", 10, 75, 10);
            p.sendMessage(Utils.colorize("&5You can now obtain Artifacts!"));
            activatedPlayers.add(activatedPlayers.size(), p);
        }
    }

    @EventHandler
    public void playerJoinReAdd(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Advancement a = Bukkit.getAdvancement(NamespacedKey.minecraft("nether/create_beacon"));
        if(p.getAdvancementProgress(a).isDone()) {
            try {
                activatedPlayers.add(activatedPlayers.size(), p);
            } catch (Exception ex) {

            }
            p.sendMessage(Utils.colorize("&6Artifacts enabled, welcome back " + p.getName()));
        }
    }

    @EventHandler
    public void playerDeleteOnExit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(activatedPlayers.contains(p)){
            activatedPlayers.remove(p);
        }
    }

    public void removeArtifactEffect(String s) {
        Player p = Bukkit.getPlayer(s);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 2, 0);
        playersInArtifact.remove(p);
        decideOnGift(p);
    }

    public void decideOnGift(Player p) {

    }

    public ItemStack makeArtifact() {
        ItemStack i = null;

        //Decide item
        int choice = rand.nextInt(10);
        int level = rand.nextInt(100);
        int legendaryChoice = rand.nextInt(11);

        if(choice == 0) {
            if(level < 90) {
                i = new NanoTechArmorLevel1().make();
            } else if(level <= 99 && level >= 90) {
                i = new NanoTechArmorLevel2().make();
            } else if (level == 100) {
                i = new NanoTechArmorLevel3().make();
            }
        } else if (choice == 1) {
            if(level < 90) {

            } else if(level <= 99 && level >= 90) {

            } else if (level == 100) {
                i = new Sniper().make();
            }
        } else if (choice == 2) {
            if(level < 90) {
                i = new LeatherEternityBoots().make();
            } else if(level <= 99 && level >= 90) {
                i = new GoldEternityBoots().make();
            } else if (level == 100) {
                i = new ChainmailEternityBoots().make();
            }
        } else if (choice == 3) {
            if(level < 90) {
                i = new LeatherEternityChestplate().make();
            } else if(level <= 99 && level >= 90) {
                i = new GoldEternityChestplate().make();
            } else if (level == 100) {
                i = new ChainmailEternityChestplate().make();
            }
        } else if (choice == 4) {
            if(level < 90) {
                i = new LeatherEternityHat().make();
            } else if(level <= 99 && level >= 90) {
                i = new GoldEternityHat().make();
            } else if (level == 100) {
                i = new ChainmailEternityHat().make();
            }
        } else if (choice == 5) {
            if(level < 90) {
                i = new LeatherEternityPants().make();
            } else if(level <= 99 && level >= 90) {
                i = new GoldEternityPants().make();
            } else if (level == 100) {
                i = new ChainmailEternityPants().make();
            }
        } else if (choice == 6) {
            if(level < 90) {

            } else if(level <= 99 && level >= 90) {

            } else if (level == 100) {

            }
        } else if (choice == 7) {
            if(level < 90) {

            } else if(level <= 99 && level >= 90) {

            } else if (level == 100) {

            }
        } else if (choice == 8) {
            if(level < 90) {

            } else if(level <= 99 && level >= 90) {

            } else if (level == 100) {

            }
        } else if (choice == 9) {
            if(level < 90) {

            } else if(level <= 99 && level >= 90) {

            } else if (level == 100) {

            }
        } else if (choice == 10) {
            if(level < 90) {

            } else if(level <= 99 && level >= 90) {

            } else if (level == 100) {

            }
        } else if (choice == 11) {
            if(level > 98) {
                if(legendaryChoice == 1) {
                    i = new IronEternityBoots().make();
                } else if (legendaryChoice == 3) {
                    i = new IronEternityChestplate().make();
                } else if (legendaryChoice == 5) {
                    i = new IronEternityHat().make();
                } else if (legendaryChoice == 7) {
                    i = new IronEternityPants().make();
                } else if (legendaryChoice == 9) {
                    i = new TripleShot().make();
                }
            }
        }

        return i;
    }

    //Artifact use logic

    public boolean hasLore(ItemStack i, String s) {
        boolean hasLore = false;

        if(i != null) {
            if(i.hasItemMeta()) {
                if(i.getItemMeta().hasLore()) {
                    List<String> lore = i.getItemMeta().getLore();
                    if(lore.size() > 0) {
                        hasLore = true;
                    }
                }
            }
        }

        return hasLore;
    }

    List<Player> activeSnipers = new ArrayList<>();
    List<Player> activeTripleshots = new ArrayList<>();

    @EventHandler
    public void artifactUsed(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(activatedPlayers.contains(p)) {



            }
        }
    }

    @EventHandler
    public void onEntityDeathApplyNano(EntityDeathEvent event) {
        if(event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = event.getEntity();
            if(entity.getKiller() != null) {
                ItemStack its = ((Player) entity.getKiller()).getInventory().getChestplate();
                Player p = (Player) entity.getKiller();
                if(hasLore(its, "Nano-Tech Armor Level: 3")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 4));
                }
                else if(hasLore(its, "Nano-Tech Armor Level: 2")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 2));
                }
                else if(hasLore(its, "Nano-Tech Armor Level: 1")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 1));
                }
            }
        }
    }

    @EventHandler
    public void onArtifactShoot(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player) {
            ItemStack i = e.getBow();
            Player p = (Player) e.getEntity();

            Vector v = new Vector(e.getProjectile().getVelocity().getX(),e.getProjectile().getVelocity().getY(),e.getProjectile().getVelocity().getZ());

            if(hasLore(i, "Shoots arrows like if they are sniper bullets!")) {
                e.getProjectile().setGravity(false);
                e.getProjectile().setGlowing(true);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1, 0);
            } else if (hasLore(i, "Shoots 3 arrows at once!")) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> p.launchProjectile(Arrow.class, v), 5);
                Bukkit.getScheduler().runTaskLater(plugin, () -> p.launchProjectile(Arrow.class, v), 10);
                Bukkit.getScheduler().runTaskLater(plugin, () -> p.launchProjectile(Arrow.class, v), 15);
            }
        }
    }
}
