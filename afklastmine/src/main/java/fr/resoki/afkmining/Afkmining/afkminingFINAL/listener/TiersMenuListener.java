package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TiersMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&6&lTier Reset Menu"))) {
            event.setCancelled(true);  // Empêche la prise des items du menu
            Player player = (Player) event.getWhoClicked();

            if (event.getSlot() == 15) {
                int currentTier = (int) TiersDatas.getPlayerData(player.getName());
                int rebirthPlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());
                int requiredRebirths = getRequiredRebirthsForTier(currentTier + 1);

                if (rebirthPlayer >= requiredRebirths) {
                    resetPlayerData(player);
                    upgradeTier(player);
                    player.closeInventory();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName()+ "&b&l "+ player.getName()+ "&r&b just advanced to tier &l" + (currentTier + 1) + "!"));
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough rebirths to pass to the next tier.");
                }
            }
            if (event.getSlot() == 26) player.closeInventory();
        }
    }

    private void resetPlayerData(Player player) {
        player.setLevel(0);
        player.setExp(0);
        long currentBalance = (long) PlayerBalance.getPlayerData(player.getName());
        PlayerBalance.withdrawPlayerData(player, currentBalance);
        SaveActualBlockData.savePlayerDataAsync(player, 0);
        SaveRebirthsDatas.savePlayerDataAsync(player, 0);
        PlayerLevel.savePlayerDataAsync(player, 0);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName()+ " &a&lAll your data has been reset for the new tier."));
    }

    private void upgradeTier(Player player) {
        int currentTier = (int) TiersDatas.getPlayerData(player.getName());
        TiersDatas.savePlayerDataAsync(player, currentTier + 1);
    }

    private int getRequiredRebirthsForTier(int tier) {
        switch (tier) {
            case 1: return 5;
            case 2: return 10;
            case 3: return 20;
            case 4: return 30;
            case 5: return 50;
            case 6: return 75;
            case 7: return 100;
            case 8: return 125;
            case 9: return 150;
            case 10: return 175;
            case 11: return 200;
            case 12: return 250;
            case 13: return 300;
            case 14: return 350;
            case 15: return 400;
            case 16: return 450;
            case 17: return 500;
            case 18: return 550;
            case 19: return 600;
            case 20: return 675;
            case 21: return 750;
            case 22: return 825;
            case 23: return 900;
            case 24: return 1000;
            case 25: return 1100;
            case 26: return 1200;
            case 27: return 1300;
            case 28: return 1400;
            case 29: return 1500;
            case 30: return 1750;
            case 31: return 2000;
            case 32: return 2250;
            case 33: return 2500;
            case 34: return 2750;
            case 35: return 3000;
            default: return Integer.MAX_VALUE;  // Aucun tier au-delà du 35
        }
    }
}
