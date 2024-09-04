package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.PlayerBalance;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Worker.WorkerLevel;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WorkerListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&6&lWorker menu"))) {
            if (event.getSlot() == 11) event.setCancelled(true);

            if (event.getSlot() == 15) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();

                double WorkerUpgradeLevel = WorkerLevel.getPlayerData(player.getName());
                double currentBalance = PlayerBalance.getPlayerData(player.getName());

                int price = (int) (WorkerUpgradeLevel * 5000 * 1.78);

                if (WorkerUpgradeLevel >= 10) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &cYou have reached the maximum upgrade level."));
                    player.closeInventory();
                    return;
                } else if (currentBalance < price) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &cNot enough money!"));
                    player.closeInventory();
                    return;
                } else {
                    WorkerLevel.savePlayerDataAsync(player, (int) WorkerUpgradeLevel + 1);
                    PlayerBalance.withdrawPlayerData(player, price);
                   // player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &aYou have successfully upgraded your worker level!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &aYour worker level is now " + ((int) Math.round(WorkerUpgradeLevel + 1)) + "!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    player.closeInventory();
                }
                player.closeInventory();
            }
        }
    }

}
