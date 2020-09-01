package me.mvabo.enchantedsurvival.modules;

import me.mvabo.enchantedsurvival.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;

public class Scoreboard implements Listener {
    //Show thirst stuff
    Thirst thirstClass;
    Artifacts artifactClass;
    Abilities abilityClass;

    Team thirstValue;

    public Scoreboard(Thirst thirst, Artifacts artifact, Abilities ability) {
        this.thirstClass = thirst;
        this.artifactClass = artifact;
        this.abilityClass = ability;
    }

    //General scoreboard managment
    public void createScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("enchantedStats", "dummy", ChatColor.RED + "Stats");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score thirst = objective.getScore(ChatColor.BLUE + "Thirst: ");
        thirstValue = board.registerNewTeam("thirstValue");
        thirstValue.addEntry(ChatColor.RED + "" + ChatColor.GOLD);
        thirstValue.setPrefix(ChatColor.AQUA + "" + getThirst(player));
        Score thirstValueScore = objective.getScore(ChatColor.RED + "" + ChatColor.GOLD);

        Score line = objective.getScore("");

        /*
        Score artifacts = objective.getScore(ChatColor.WHITE + "---Artifacts---");
        Score artifact = objective.getScore(ChatColor.GOLD + "Artifacts:");
        Team artifactValue = board.registerNewTeam("artifactValue");
        artifactValue.addEntry(ChatColor.BLUE + "" + ChatColor.RED);
        artifactValue.setPrefix(ChatColor.YELLOW + "" + getArtifact(player));
        Score artifactValueScore = objective.getScore(ChatColor.BLUE + "" + ChatColor.RED);
         */

        Score abilities = objective.getScore(ChatColor.GOLD + "---Abilities---");
        Team miningValue = board.registerNewTeam("miningValue");
        miningValue.addEntry(ChatColor.GREEN + "" + ChatColor.GOLD);
        miningValue.setPrefix(ChatColor.GRAY + "" + getMiningLevel(player));
        Score miningValueScore = objective.getScore(ChatColor.GREEN + "" + ChatColor.GOLD);
        Team woodcuttingValue = board.registerNewTeam("cuttingValue");
        woodcuttingValue.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD);
        woodcuttingValue.setPrefix(ChatColor.YELLOW + "" + getCuttingLevel(player));
        Score woodcuttingValueScore = objective.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD);
        Team meleeValue = board.registerNewTeam("meleeValue");
        meleeValue.addEntry(ChatColor.LIGHT_PURPLE + "" + ChatColor.GOLD);
        meleeValue.setPrefix(ChatColor.RED + "" + getMeleeLevel(player));
        Score meleeValueScore = objective.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.GOLD);

        thirst.setScore(9);
        thirstValueScore.setScore(8);
        line.setScore(7);
        /*
        artifacts.setScore(6);
        artifact.setScore(5);
        artifactValueScore.setScore(4);
         */
        abilities.setScore(3);
        miningValueScore.setScore(3);
        woodcuttingValueScore.setScore(2);
        meleeValueScore.setScore(1);

        player.setScoreboard(board);
    }

    public String getArtifact(Player p) {
        String s = null;
        if(artifactClass.activatedPlayers.contains(p)) {
            s = Utils.colorize("&2Enabled");
        } else {
            s = Utils.colorize("&4Disabled");
        }
        return s;
    }

    public int getThirst(Player p){
        int thirstValue = thirstClass.thirst.get(p);
        return thirstValue;
    }

    public String getMiningLevel(Player p) {
        String uuid = p.getUniqueId().toString();
        Integer[] value = abilityClass.abilities.get(uuid);
        String convertedValue = value[0].toString();
        return convertedValue;
    }

    public String getCuttingLevel(Player p) {
        String uuid = p.getUniqueId().toString();
        Integer[] value = abilityClass.abilities.get(uuid);
        String convertedValue = value[1].toString();
        return convertedValue;
    }

    public String getMeleeLevel(Player p) {
        String uuid = p.getUniqueId().toString();
        Integer[] value = abilityClass.abilities.get(uuid);
        String convertedValue = value[2].toString();
        return convertedValue;
    }

    public void hideOrShowScoreboard(Player p) {
        if(p.getScoreboard().getTeams().contains(thirstValue)) {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        } else {
            createScoreboard(p);
        }
    }

    public void updateScoreboard() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getScoreboard().getTeams().contains(thirstValue)) {
                org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();
                board.getTeam("thirstValue").setPrefix(ChatColor.AQUA + "" + getThirst(player));
                //board.getTeam("artifactValue").setPrefix(ChatColor.YELLOW + "" + getArtifact(player));
                board.getTeam("miningValue").setPrefix(ChatColor.GRAY + "" + getMiningLevel(player));
                board.getTeam("cuttingValue").setPrefix(ChatColor.YELLOW + "" + getCuttingLevel(player));
                board.getTeam("meleeValue").setPrefix(ChatColor.RED + "" + getMeleeLevel(player));
                player.setScoreboard(board);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        createScoreboard(e.getPlayer());
    }
}
