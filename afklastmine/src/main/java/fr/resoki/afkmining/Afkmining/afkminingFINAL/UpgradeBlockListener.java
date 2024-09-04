package fr.resoki.afkmining.Afkmining.afkminingFINAL;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.DefaultSpeedRespawn;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.PlayerBalance;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.SaveActualBlockData;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.SaveMultiplyDatas;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeBlockListener implements Listener {

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&d&lUpgrade Block"))) {
         event.setCancelled(true);
         Player player = (Player) event.getWhoClicked();

         double currentBlock = SaveActualBlockData.getPlayerData(player.getName());
         int price = (int) (currentBlock * 500 * 1.78);
         Material[] blockList = ServerConfig.getBlockList();

         double balancePlayer = PlayerBalance.getPlayerData(player.getName());

         if (event.getSlot() == 12) {
            if (currentBlock >= blockList.length - 1) {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &cYou have reached the maximum upgrade level."));
               player.closeInventory();
               return;
            }

            if (balancePlayer < price) {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &cNot enough money!"));
               player.closeInventory();
               return;
            } else {
               SaveActualBlockData.savePlayerDataAsync(player, currentBlock + 1);
               PlayerBalance.withdrawPlayerData(player, price);
               Material newBlock = blockList[(int) (currentBlock) + 1];
               Location blockLocationSet = new Location(Bukkit.getWorld("world"), -46, 77, -138);
               player.sendBlockChange(blockLocationSet, newBlock.createBlockData());
               player.closeInventory();
               player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, blockLocationSet, 1);
               player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
               double currentMulti = SaveMultiplyDatas.getPlayerData(player.getName());
               SaveMultiplyDatas.savePlayerDataAsync(player, currentMulti + 0.5);
            }
         }

         if (event.getSlot() == 22) {
            if (currentBlock >= blockList.length - 1) {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → You have reached the maximum upgrade level."));
               player.closeInventory();
               return;
            }

            while (currentBlock < blockList.length - 1) {
               double priceBuy = (currentBlock * 500) * 1.78;

               if (balancePlayer >= priceBuy) {
                  balancePlayer -= priceBuy;
                  currentBlock++;
                  Material newBlock = blockList[(int) currentBlock];
                  if (newBlock == null) {
                     player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &cAn error occurred while upgrading the block."));
                     player.closeInventory();
                     break;
                  }
                  Location blockLocationSet = new Location(Bukkit.getWorld("world"), -46, 77, -138);
                  player.sendBlockChange(blockLocationSet, newBlock.createBlockData());
                  player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, blockLocationSet, 1);
                  player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                  PlayerBalance.withdrawPlayerData(player, priceBuy);
               } else {
                  break; // Si le joueur n'a plus assez d'argent pour l'amélioration suivante, arrêter.
               }
            }

            SaveActualBlockData.savePlayerDataAsync(player, currentBlock);
            double currentMulti = SaveMultiplyDatas.getPlayerData(player.getName());
            SaveMultiplyDatas.savePlayerDataAsync(player, currentMulti + currentBlock * 0.5); // Mise à jour du multiplicateur
            player.closeInventory();
         }

         if (event.getSlot() == 14) {
            if (DefaultSpeedRespawn.getPlayerData(player) == 20L) {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &cYou have reached the maximum speed level."));
               player.closeInventory();
               return;
            }

            long defaultSpeed = DefaultSpeedRespawn.getPlayerData(player);
            double second = 2.0;
            double basePrice = 1000;
            double priceMultiplier = 1.2;
            double defaultPrice;

            second = defaultSpeed / 20.0;

            defaultPrice = basePrice * Math.pow(priceMultiplier, (2.0 - second) / 0.1);

            if (balancePlayer < defaultPrice) {
               player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNot enough money!"));
               player.closeInventory();
            } else {
               DefaultSpeedRespawn.savePlayerDataAsync(player, defaultSpeed - 2L);
               PlayerBalance.withdrawPlayerData(player, defaultPrice);
               player.closeInventory();
            }
         }
      }
   }
}
