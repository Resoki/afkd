package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.EventCycle;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.DefaultSpeedRespawn;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.listener.BlockBreak;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AutoBreakCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private static final Location TARGET_LOCATION = new Location(Bukkit.getWorld("world"), -46, 77, -138);
    private EventCycle eventCycle;
    public AutoBreakCommand(JavaPlugin plugin, EventCycle eventCycle) {
        this.plugin = plugin;
        this.eventCycle = eventCycle;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasMetadata("autoBreakTask")) {
                // Stop the existing task
                BukkitTask existingTask = (BukkitTask) player.getMetadata("autoBreakTask").get(0).value();
                if (existingTask != null) {
                    existingTask.cancel();
                    player.removeMetadata("autoBreakTask", plugin);
                    String msg = ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &cAuto-break &lstopped");
                    player.sendMessage(msg);
                }
            } else {
                // Start a new auto-break task
                startAutoBreakTask(player);
                String msg = ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &aAuto-break &lactivated");
                player.sendMessage(msg);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
        }
        return true;
    }

    private void startAutoBreakTask(Player player) {
        long actual = DefaultSpeedRespawn.getPlayerData(player);
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline() && TARGET_LOCATION.getBlock().getType() != Material.BEDROCK) {
                    // Simulate block breaking
                    player.sendBlockChange(TARGET_LOCATION, Material.AIR.createBlockData());
                    new BukkitRunnable() {
                        private EventCycle eventCycle;

                        @Override
                        public void run() {
                            player.sendBlockChange(TARGET_LOCATION, Material.BEDROCK.createBlockData());
                            // Call the method in BlockBreak to handle the block break logic
                            new BlockBreak(plugin).simulateBlockBreak(player, TARGET_LOCATION);
                        }
                    }.runTaskLater(plugin, actual);
                } else {
                    cancel(); // Stop the task if the block is no longer valid
                }
            }
        }.runTaskTimer(plugin, 0L, actual); // Run every 5 seconds (100 ticks)

        player.setMetadata("autoBreakTask", new FixedMetadataValue(plugin, task));
    }
}