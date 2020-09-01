package me.mvabo.enchantedsurvival.commands;

import me.mvabo.enchantedsurvival.EnchantedSurvival;
import me.mvabo.enchantedsurvival.PlayerStats;
import me.mvabo.enchantedsurvival.modules.Abilities;
import me.mvabo.enchantedsurvival.modules.Scoreboard;
import me.mvabo.enchantedsurvival.modules.abilities.AbilityGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ES implements CommandExecutor {

    Plugin plugin = EnchantedSurvival.getPlugin(EnchantedSurvival.class);
    PlayerStats stats;
    Abilities abs;
    AbilityGUI gui;
    Scoreboard scoreboard;

    boolean abilitiesEnabled = plugin.getConfig().getBoolean("abilities");

    public ES(PlayerStats ps, Abilities abs, AbilityGUI gui, Scoreboard scoreboard) {
        this.stats = ps;
        this.abs = abs;
        this.gui = gui;
        this.scoreboard = scoreboard;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0) {
            sender.sendMessage(ChatColor.YELLOW + "EnchantedSurvival Info");
            sender.sendMessage(ChatColor.YELLOW + "/es abilities to open abilities GUI");
            sender.sendMessage(ChatColor.YELLOW + "/es scoreboard to open/close your stats scoreboard");
            sender.sendMessage(ChatColor.YELLOW + "Thats it");
            return true;
        } else if (args[0].equalsIgnoreCase("abilities") && sender.hasPermission("enchantedsurvival.use.abilities") && abilitiesEnabled) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Only players can open abilities GUI");
                return true;
            }
            Player player = (Player) sender;
            String uuid = player.getUniqueId().toString();
            if(abs.abilities.containsKey(uuid)) {
                gui.openGUI(player);
                return true;
            }
        } else if (args[0].equalsIgnoreCase("scoreboard")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Only players can open the scoreboard");
                return true;
            }
            Player player = (Player) sender;
            if(plugin.getConfig().getBoolean("scoreboard")) {
                scoreboard.hideOrShowScoreboard(player);
                return true;
            }
        } else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.YELLOW + "EnchantedSurvival Info");
            sender.sendMessage(ChatColor.YELLOW + "/es abilities to open abilities GUI");
            sender.sendMessage(ChatColor.YELLOW + "/es scoreboard to open/close your stats scoreboard");
            sender.sendMessage(ChatColor.YELLOW + "Thats it");
            return true;
        }
        return false;
    }
}
