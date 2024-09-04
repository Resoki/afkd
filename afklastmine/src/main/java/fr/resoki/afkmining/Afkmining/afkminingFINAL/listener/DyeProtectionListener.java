package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DyeProtectionListener implements Listener {

    private final Plugin plugin;

    public DyeProtectionListener(Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (item != null && (item.getType() == Material.RED_DYE || item.getType() == Material.LIME_DYE || item.getType() == Material.RECOVERY_COMPASS)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You cannot drop this item!");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (item != null && (item.getType() == Material.RED_DYE || item.getType() == Material.LIME_DYE ||  item.getType() == Material.RECOVERY_COMPASS)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(ChatColor.RED + "You cannot move this item!");
        }
    }
}
