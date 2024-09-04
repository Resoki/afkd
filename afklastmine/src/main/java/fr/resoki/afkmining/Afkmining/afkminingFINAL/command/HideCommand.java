package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class HideCommand implements CommandExecutor, Listener {

    private final Plugin plugin;
    private final Map<Player, Boolean> hiddenPlayers = new HashMap<>();

    public HideCommand(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (hiddenPlayers.containsKey(player)) {
            for (Player target : Bukkit.getOnlinePlayers()) if (!target.equals(player)) player.showPlayer(plugin, target);
            hiddenPlayers.remove(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAll players now &lvisible"));
        } else {
            for (Player target : Bukkit.getOnlinePlayers()) if (!target.equals(player)) player.hidePlayer(plugin, target);
            hiddenPlayers.put(player, true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAll players now &lhidden"));
        }

        return true;
    }
}