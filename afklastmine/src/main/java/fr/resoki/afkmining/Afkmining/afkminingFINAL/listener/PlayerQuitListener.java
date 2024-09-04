package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class PlayerQuitListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerQuitListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().hasMetadata("autoBreakTask")) {
            BukkitTask task = (BukkitTask) event.getPlayer().getMetadata("autoBreakTask").get(0).value();
            if (task != null) task.cancel();
            event.getPlayer().removeMetadata("autoBreakTask", plugin);
        }
    }
}
