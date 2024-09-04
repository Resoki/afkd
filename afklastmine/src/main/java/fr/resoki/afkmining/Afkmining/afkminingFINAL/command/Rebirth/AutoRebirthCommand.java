package fr.resoki.afkmining.Afkmining.afkminingFINAL.command.Rebirth;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.listener.RebirthListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AutoRebirthCommand implements CommandExecutor {

    private final HashMap<UUID, Boolean> autoRebirthStatus = new HashMap<>();
    private final RebirthListener rebirthListener;
    private JavaPlugin plugin;

    public AutoRebirthCommand(RebirthListener rebirthListener, JavaPlugin plugin) {
        this.rebirthListener = rebirthListener;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        if (autoRebirthStatus.containsKey(playerId) && autoRebirthStatus.get(playerId)) {
            autoRebirthStatus.put(playerId, false);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAutoRebirth has been disabled."));
        } else {
            autoRebirthStatus.put(playerId, true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAutoRebirth has been enabled."));
            startAutoRebirthCheck(player);
        }

        return true;
    }

    private void startAutoRebirthCheck(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !autoRebirthStatus.getOrDefault(player.getUniqueId(), false)) {
                    this.cancel();
                    return;
                }

                int levelPlayer = PlayerLevel.getPlayerData(player.getName());
                int rebirthPlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());
                int rebirthCost = 100 + (rebirthPlayer * 50);

                if (levelPlayer >= rebirthCost) {
                    rebirthListener.performRebirth(player, rebirthPlayer, rebirthCost);
                }
            }
        }.runTaskTimer(this.plugin, 20L, 20L); // Runs every second (20 ticks)
    }
}
