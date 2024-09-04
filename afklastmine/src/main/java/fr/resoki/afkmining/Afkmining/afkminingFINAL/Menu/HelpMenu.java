package fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HelpMenu {

    public HelpMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder) null, 27, ChatColor.translateAlternateColorCodes('&', "&d&lHelp Menu"));

        generateCase(inventory, 10, Material.EMERALD,  ChatColor.translateAlternateColorCodes('&', "&a&lUpgrade Menu"), "&7Click to access Upgrade");
        generateCase(inventory, 12, Material.DIAMOND,  ChatColor.translateAlternateColorCodes('&', "&d&lRebirth Menu"), "&7Click to access Rebirth");
        generateCase(inventory, 14, Material.GOLD_INGOT,  ChatColor.translateAlternateColorCodes('&', "&e&lTier Menu"), "&7Click to access Tiers");
        generateCase(inventory, 16,  Material.IRON_INGOT,  ChatColor.translateAlternateColorCodes('&', "&f&lWorker Menu"), "&7Click to access Worker");

        player.openInventory(inventory);
    }

    public static void generateCase(Inventory inventory, int position, Material material, String displayName, String description) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', description).split("\n")));
            item.setItemMeta(meta);
        }
        inventory.setItem(position, item);
    }
}