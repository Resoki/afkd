package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockDamageListener implements Listener {
    private final JavaPlugin plugin;

    public BlockDamageListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();

        if (blockLocation.equals(new Location(Bukkit.getWorld("world"), -38, 65, -183)) && block.getType() != Material.BEDROCK) {
            event.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (block.getType() != Material.BEDROCK) {
                        block.setType(Material.AIR); // Détruire le bloc
                        player.sendBlockChange(block.getLocation(), Material.AIR.createBlockData());
                    }
                }
            }.runTaskLater(plugin, 20L);
        }
    }
}
