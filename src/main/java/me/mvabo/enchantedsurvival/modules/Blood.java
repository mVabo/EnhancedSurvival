package me.mvabo.enchantedsurvival.modules;

import me.mvabo.enchantedsurvival.EnchantedSurvival;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;

public class Blood implements Listener {
    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent e) {
        Entity attacker = e.getDamager();
        Entity victim = e.getEntity();

        if (victim instanceof Player || attacker instanceof Player) {
            victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_LAVA_POP, 1, 2);
            BoundingBox bb = victim.getBoundingBox();
            double heightAdd = bb.getHeight() / 2.0;
            double width = (bb.getWidthX() + bb.getWidthZ()) / 2.0;
            BlockData bd = getBlood((LivingEntity) victim);
            victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, victim.getLocation().add(0, heightAdd, 0), 25, width/3.0, heightAdd/2.0, width/3.0, 0.005, bd);

            //Drop blood
            ItemStack blood = new ItemStack(Material.REDSTONE, 1);
            ItemMeta bloodMeta = blood.getItemMeta();
            bloodMeta.setDisplayName("blood");
            blood.setItemMeta(bloodMeta);
            victim.getWorld().dropItem(victim.getLocation(), blood);

            //Queue next effect
            queueBloodEffect((LivingEntity) victim);

            //Queue remove blood
            queueRemoveBlood(blood);
        }
    }

    @EventHandler
    public void onPlayerFall(EntityDamageByBlockEvent e) {
        Entity victim = e.getEntity();

        if (victim instanceof Player) {
            victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_LAVA_POP, 1, 2);
            BoundingBox bb = victim.getBoundingBox();
            double heightAdd = bb.getHeight() / 2.0;
            double width = (bb.getWidthX() + bb.getWidthZ() / 2.0);
            BlockData bd = getBlood((LivingEntity) victim);
            victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, victim.getLocation().add(0, heightAdd, 0), 25, width/3.0, heightAdd/2.0, width/3.0, 0.005, bd);

            //Drop blood
            ItemStack blood = new ItemStack(Material.REDSTONE, 1);
            ItemMeta bloodMeta = blood.getItemMeta();
            bloodMeta.setDisplayName("blood");
            blood.setItemMeta(bloodMeta);
            victim.getWorld().dropItem(victim.getLocation(), blood);

            //Queue next effect
            queueBloodEffect((LivingEntity) victim);

            //Queue remove blood
            queueRemoveBlood(blood);
        }
    }

    @EventHandler
    public void onEntityPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("blood")) {
                e.setCancelled(true);
            }
        }
    }

    public void bleedEffect(LivingEntity e) {
        if (e.isDead()) {
            return;
        }

        //Make bleed effect + sound
        e.getWorld().playSound(e.getLocation(), Sound.BLOCK_LAVA_POP, 1, 2);
        BoundingBox bb = e.getBoundingBox();
        double heightAdd = bb.getHeight() / 2.0;
        double width = (bb.getWidthX() + bb.getWidthZ()) / 2.0;
        BlockData bd = getBlood(e);
        e.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getLocation().add(0, heightAdd, 0), 25, width/3.0, heightAdd/2.0, width/3.0, 0.005, bd);

        //Drop blood
        ItemStack blood = new ItemStack(Material.REDSTONE, 1);
        ItemMeta bloodMeta = blood.getItemMeta();
        bloodMeta.setDisplayName("blood");
        blood.setItemMeta(bloodMeta);
        e.getWorld().dropItem(e.getLocation(), blood);

        //Add damage
        if (e.getHealth() > 1.0) {
            ((LivingEntity) e).damage(1.0);
        }

        //Queue next effect
        lastQueueBloodEffect(e);

        //Queue remove blood
        queueRemoveBlood(blood);
    }

    public void queueRemoveBlood(ItemStack blood) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                removeBlood(blood);
            }
        }, 60);
    }

    public void removeBlood(ItemStack blood) {
        try {
            blood.setAmount(0);
        } catch (Exception e) {
            //The reason for this would be falling in lava, being picked up and much more, so its not neccessary to print the stack-trace each time
        }
    }

    public void lastBleedEffect(LivingEntity e) {
        if (e.isDead()) {
            return;
        }

        //Make bleed effect + sound
        e.getWorld().playSound(e.getLocation(), Sound.BLOCK_LAVA_POP, 1, 2);
        BoundingBox bb = e.getBoundingBox();
        double heightAdd = bb.getHeight() / 2.0;
        double width = (bb.getWidthX() + bb.getWidthZ()) / 2.0;
        BlockData bd = getBlood(e);
        e.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getLocation().add(0, heightAdd, 0), 25, width/3.0, heightAdd/2.0, width/3.0, 0.005, bd);

        //Drop blood
        ItemStack blood = new ItemStack(Material.REDSTONE, 1);
        ItemMeta bloodMeta = blood.getItemMeta();
        bloodMeta.setDisplayName("blood");
        blood.setItemMeta(bloodMeta);
        e.getWorld().dropItem(e.getLocation(), blood);

        //Add damage
        if (e.getHealth() > 1.0) {
            ((LivingEntity) e).damage(1.0);
        }

        //Queue remove blood
        queueRemoveBlood(blood);
    }

    public void queueBloodEffect(LivingEntity e) {
        if (!e.isDead()) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    bleedEffect(e);
                }
            }, 20L);
        }

    }

    public void lastQueueBloodEffect(LivingEntity e) {
        if (!e.isDead()) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    lastBleedEffect(e);
                }
            }, 20L);
        }
    }

    public BlockData getBlood(LivingEntity le) {
        BlockData bd = Material.NETHER_WART_BLOCK.createBlockData();
        if(le instanceof Creeper) {
            bd = Material.LIME_CONCRETE_POWDER.createBlockData();
        }
        else if(le instanceof WitherSkeleton || le instanceof Wither) {
            bd = Material.COAL_BLOCK.createBlockData();
        }
        else if(le instanceof Skeleton || le instanceof SkeletonHorse) {
            bd = Material.BONE_BLOCK.createBlockData();
        }
        else if(le instanceof MagmaCube) {
            bd = Material.MAGMA_BLOCK.createBlockData();
        }
        else if(le instanceof Blaze) {
            bd = Material.FIRE.createBlockData();
        }
        else if(le instanceof Slime) {
            bd = Material.SLIME_BLOCK.createBlockData();
        }
        else if(le instanceof Shulker) {
            bd = Material.SHULKER_BOX.createBlockData();
        }
        return bd;
    }
}
