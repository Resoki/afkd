package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu.FurnanceMenu;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.UpgradeMenu;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.PlayerBalance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RightClickBlock implements Listener {
    private JavaPlugin plugin;

    public RightClickBlock(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final Location targetLocation = new Location(Bukkit.getWorld("world"), 260, 99, -96);

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();

            if (block != null) {
                Location blockLocation = block.getLocation();
                if (blockLocation.equals(targetLocation)) new UpgradeMenu(player);
            }

            if (block.getType() == Material.FURNACE) {
                event.setCancelled(true);
                new FurnanceMenu(player);
            }
        }
    }

}
