package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.FurnanceMultiplierDatas;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthPoints;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FurnaceMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&6&lUpgrade Furnace"))) {
            event.setCancelled(true); // Prevent moving items in the custom menu

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            if (clickedItem.getType() == Material.FURNACE) {
                double currentMultiplier = FurnanceMultiplierDatas.getPlayerData(player);
                double nextMultiplier = currentMultiplier + 0.1;
                int upgradeCost = calculateUpgradeCost(currentMultiplier);

                int rebirthPoints = (int) SaveRebirthPoints.getPlayerData(player.getName());

                if (rebirthPoints >= upgradeCost) {
                    // Deduct rebirth points
                    SaveRebirthPoints.savePlayerDataAsync(player, rebirthPoints - upgradeCost);

                    // Update the furnace multiplier
                    FurnanceMultiplierDatas.savePlayerDataAsync(player, nextMultiplier);

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() +   "→ &aSuccessfully upgraded your furnace multiplier to &l" + String.format("%.2f", nextMultiplier)  + "x!"));
                    player.closeInventory();
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() +   "→ &cYou don't have enough rebirth points to upgrade your furnace multiplier!"));
                }
            } else if (clickedItem.getType() == Material.RED_BANNER) {
                // Handle the leave button
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Optional: Add any cleanup logic when the menu is closed
    }

    // Method to calculate the cost of upgrading the furnace multiplier
    private int calculateUpgradeCost(double currentMultiplier) {
        double baseCost = 10; // The cost for the first upgrade to 1.1
        int multiplierLevel = (int) ((currentMultiplier - 1.0) * 10); // Determine the current level
        return (int) (baseCost * Math.pow(2.5, multiplierLevel));
    }
}
