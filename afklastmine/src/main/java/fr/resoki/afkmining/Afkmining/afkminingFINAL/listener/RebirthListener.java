package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.CurrentXp;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthPoints;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RebirthListener implements Listener {
    boolean somethingToSell = false;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&d&lRebirth"))) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null || currentItem.getType() == Material.AIR) return;

            if (currentItem.getType() == Material.RED_BANNER) {
                player.closeInventory();
            } else if (currentItem.getType() == Material.EXPERIENCE_BOTTLE) {
                int levelPlayer = PlayerLevel.getPlayerData(player.getName());
                int rebirthPlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());

                int rebirthCost = 100 + (rebirthPlayer * 50);

                if (levelPlayer < rebirthCost) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + "→ &cYou can't &lrebirth &r&cnow. You need " + rebirthCost + " levels."));
                    return;
                } else {
                    performRebirth(player, rebirthPlayer, rebirthCost);
                }
            } else if (currentItem.getType() == Material.ANVIL) {
                int levelPlayer = PlayerLevel.getPlayerData(player.getName());
                int rebirthPlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());
                int maxRebirths = calculateMaxRebirths(rebirthPlayer, levelPlayer);

                if (maxRebirths > 0) {
                    for (int i = 1; i <= maxRebirths; i++) {
                        int rebirthCost = 100 + ((rebirthPlayer + i) * 50);
                        performRebirth(player, rebirthPlayer + i - 1, rebirthCost);
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + "→ &cYou don't have enough levels to perform a max rebirth."));
                }
            }
        }
    }

    public void performRebirth(Player player, int currentRebirth, int cost) {
        SaveRebirthsDatas.savePlayerDataAsync(player, currentRebirth + 1);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + "→ &bYou just &lrebirthed &r&bto &d&lR" + (currentRebirth + 1)+ " &r&d(+" + (currentRebirth + 1) + " rebirths points)"));
        if ((currentRebirth + 1) % 5 == 0) {
            Bukkit.broadcastMessage((ChatColor.translateAlternateColorCodes('&', "&b&l" + player.getName() + " &r&bjust rebirthed to &d&lR" + (currentRebirth + 1))));
        }

        // Mise à jour des niveaux après chaque rebirth
        int remainingLevels = PlayerLevel.getPlayerData(player.getName()) - cost;
        PlayerLevel.savePlayerDataAsync(player, Math.max(0, remainingLevels));
        PlayerBalance.savePlayerData(player, 0);
        player.closeInventory();

        double multi = SaveMultiplyDatas.getPlayerData(player.getName());
        double multiToGive = calculateMultiplier(currentRebirth + 1);
        SaveMultiplyDatas.savePlayerDataAsync(player, multi + multiToGive);
        int currentPoint = (int) SaveRebirthPoints.getPlayerData(player.getName());
        SaveRebirthPoints.savePlayerDataAsync(player, currentPoint + (currentRebirth + 1));
        CurrentXp.savePlayerDataAsync(player, 0);

        String title = ChatColor.translateAlternateColorCodes('&', "&b&lYou just rebirthed to &d&lR" + (currentRebirth + 1));
        String subtitle = ChatColor.translateAlternateColorCodes('&', "&d(+" + (currentRebirth + 1) + " rebirths points)");
        player.sendTitle(title, subtitle, 10, 50, 20);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }

    private double calculateMultiplier(int rebirthLevel) {
        return 0.1 + ((rebirthLevel - 1) * 0.15);
    }

    private int calculateMaxRebirths(int currentRebirthLevel, int currentLevel) {
        int maxRebirths = 0;
        int levelPlayer = currentLevel;

        for (int i = currentRebirthLevel + 1; ; i++) {
            int rebirthCost = 100 + (i * 50);
            if (levelPlayer >= rebirthCost) {
                levelPlayer -= rebirthCost;
                maxRebirths++;
            } else {
                break;
            }
        }

        return maxRebirths;
    }

}
