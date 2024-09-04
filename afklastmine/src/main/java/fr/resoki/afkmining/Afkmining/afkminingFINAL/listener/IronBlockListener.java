package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.PlayerBalance;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.SaveMultiplyDatas;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IronBlockListener implements Listener {
    private final Plugin plugin;
    private final Map<UUID, BukkitTask> playerTasks = new HashMap<>();

    public IronBlockListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.IRON_BLOCK) {
            if (!playerTasks.containsKey(playerUUID)) startPayTask(player);
        } else {
            stopPayTask(playerUUID);
        }
    }

    private void startPayTask(Player player) {
        UUID playerUUID = player.getUniqueId();
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                double playerMultiply = SaveMultiplyDatas.getPlayerData(player.getName());
                PlayerBalance.savePlayerData(player, 10 * playerMultiply);
                String formattedAmount = ServerConfig.formatAmount(10 * playerMultiply);


                player.sendTitle(ChatColor.GOLD + "AFK", ChatColor.GREEN + "+$"+ formattedAmount + " /s", 0, 20, 0);

            }
        }.runTaskTimer(plugin, 0L, 20L);

        playerTasks.put(playerUUID, task);
    }

    private void stopPayTask(UUID playerUUID) {
        if (playerTasks.containsKey(playerUUID)) {
            playerTasks.get(playerUUID).cancel();
            playerTasks.remove(playerUUID);
        }
    }
}
