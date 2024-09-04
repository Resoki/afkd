package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class DyeListener implements Listener {

    private final Plugin plugin;

    public DyeListener(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;

        Material itemType = item.getType();

        if (itemType == Material.RED_DYE) {
            for (Player target : Bukkit.getOnlinePlayers()) if (!target.equals(player)) player.hidePlayer(plugin, target);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAll players now &lhidden"));
            setPlayerDye(player, Material.LIME_DYE, "&6&lShow", " &6Toggle Hide player");

        } else if (itemType == Material.LIME_DYE) {
            for (Player target : Bukkit.getOnlinePlayers()) if (!target.equals(player)) player.showPlayer(plugin, target);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAll players now &lvisible"));
            setPlayerDye(player, Material.RED_DYE, " &6&lHide", " &6Toggle Show player");
        }
    }

    private void setPlayerDye(Player player, Material dyeMaterial, String displayName, String lore) {
        ItemStack dye = new ItemStack(dyeMaterial);
        ItemMeta meta = dye.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore)));
            dye.setItemMeta(meta);
        }

        player.getInventory().setItem(8, dye); // Remplace l'item dans le slot 8
    }
}
