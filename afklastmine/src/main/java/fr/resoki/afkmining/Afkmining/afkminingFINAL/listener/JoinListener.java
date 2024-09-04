/* Decompiler 28ms, total 177ms, lines 156 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Worker.WorkerLevel;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

public class JoinListener implements Listener {
   String serverName = ServerConfig.getServerName();
   private final Plugin plugin;
   private static final Location TARGET_LOCATION = new Location(Bukkit.getWorld("world"), 260, 99, -96);
   public JoinListener(Plugin plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onPlayerJoin(final PlayerJoinEvent event) {
      String playerName = event.getPlayer().getName();
      Random random = new Random();
      String[] tableauDeChaines = new String[]{" join the server!", " join us!", " join Factory Idle!", " join the idle !", " just logged !"};
      String chaineAleatoire = tableauDeChaines[random.nextInt(tableauDeChaines.length)];
      event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[+] &e&l" + playerName  + "&r&e" + chaineAleatoire));

      this.sendDiscordNotification(playerName);

      Player player = event.getPlayer();
      boolean pAlreadyClaimedDailyToday = PlayerAlreadyClaimedDaily.getPlayerData(player);
      String msg;
      String msg2;
      if (!pAlreadyClaimedDailyToday) {
         msg = ChatColor.GRAY + "---------------------------";
         msg2 = ChatColor.translateAlternateColorCodes('&', "     &6&l⭐   DAILY   ⭐");
         String msg1 = ChatColor.translateAlternateColorCodes('&', "&e ⭐ " + player.getName() + ", you can claim our /daily ⭐");
         String formattedMessage = msg + "\n" + msg2 + "\n" + msg1 + "\n" + msg;
         player.sendMessage(formattedMessage);
      }

      msg = ChatColor.translateAlternateColorCodes('&', "&eYou joined " + JoinListener.this.serverName + " &e!");
      player.sendMessage(msg);
      if (!player.hasPlayedBefore()) {
         msg2 = ChatColor.translateAlternateColorCodes('&', "&eYou joined " + JoinListener.this.serverName + " for the first time &e!");
         player.sendMessage(msg2);
      }

      startAutoBreakTask(player);
      workerTask(player);

      player.getInventory().clear();
      giveDye(player);
      giveCompass(player);
   }

   private void giveCompass(Player player) {
      ItemStack star = new ItemStack(Material.RECOVERY_COMPASS);
      int compassSlot = 0;
      ItemMeta meta = star.getItemMeta();
      if (meta != null) {
         meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&lHelp Menu"));
         meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&dHelp menu")));
         star.setItemMeta(meta);
      }

      ItemStack itemInSlot = player.getInventory().getItem(compassSlot);
      boolean hasStar = false;
      ItemStack[] var7 = player.getInventory().getContents();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         ItemStack item = var7[var9];
         if (item != null && item.getType() == Material.RECOVERY_COMPASS) {
            hasStar = true;
            break;
         }
      }

      if (!hasStar) {
         if (itemInSlot != null && itemInSlot.getType() != Material.AIR) {
            int emptySlot = player.getInventory().firstEmpty();
            if (emptySlot != -1) {
               player.getInventory().setItem(emptySlot, star);
            } else {
               player.getInventory().addItem(new ItemStack[]{star});
            }
         } else {
            player.getInventory().setItem(compassSlot, star);
         }
      }

   }

   private void giveDye(Player player) {
      ItemStack star = new ItemStack(Material.RED_DYE);
      int compassSlot = 8;
      ItemMeta meta = star.getItemMeta();
      if (meta != null) {
         meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lHide players"));
         meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&aHide all players")));
         star.setItemMeta(meta);
      }

      ItemStack itemInSlot = player.getInventory().getItem(compassSlot);
      boolean hasStar = false;
      ItemStack[] var7 = player.getInventory().getContents();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         ItemStack item = var7[var9];
         if (item != null && item.getType() == Material.RED_DYE) {
            hasStar = true;
            break;
         }
      }

      if (!hasStar) {
         if (itemInSlot != null && itemInSlot.getType() != Material.AIR) {
            int emptySlot = player.getInventory().firstEmpty();
            if (emptySlot != -1) {
               player.getInventory().setItem(emptySlot, star);
            } else {
               player.getInventory().addItem(new ItemStack[]{star});
            }
         } else {
            player.getInventory().setItem(compassSlot, star);
         }
      }

   }

   public void startAutoBreakTask(Player player) {
      long actual = DefaultSpeedRespawn.getPlayerData(player);
      BukkitTask task = new BukkitRunnable() {
         @Override
         public void run() {
            if (player.isOnline() && TARGET_LOCATION.getBlock().getType() != Material.BEDROCK) {
               player.sendBlockChange(TARGET_LOCATION, Material.AIR.createBlockData());
               new BukkitRunnable() {
                  @Override
                  public void run() {
                     player.sendBlockChange(TARGET_LOCATION, Material.BEDROCK.createBlockData());
                     new BlockBreak((JavaPlugin) plugin).simulateBlockBreak(player, TARGET_LOCATION);
                  }
               }.runTaskLater(plugin, actual);
            } else {
               cancel();
            }
         }
      }.runTaskTimer(plugin, 0L, actual);

      player.setMetadata("autoBreakTask", new FixedMetadataValue(plugin, task));
      Location factoryLocation = new Location(player.getWorld(), 220.3, 99, -173);
      factoryLocation.setYaw(0);
      player.teleport(factoryLocation);
   }

   // Each 15 seconds the worker brings the blocks and gives player money for them based on multiplier and worker level
   public void workerTask(Player player) {
      BukkitTask task = new BukkitRunnable() {
         @Override
         public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
               double currentMulti = SaveMultiplyDatas.getPlayerData(player.getName());
               double WorkerUpgradeLevel = WorkerLevel.getPlayerData(player.getName());
               double currentBalance = PlayerBalance.getPlayerData(player.getName());

               PlayerBalance.savePlayerData(player, currentBalance + (currentMulti * (WorkerUpgradeLevel * 200)));
               String message = ChatColor.translateAlternateColorCodes('&',  "&fYour worker just sell &a&l$" + ServerConfig.formatAmount(currentMulti * (WorkerUpgradeLevel * 200)));
               sendActionBar(player, message);
            };
         }
      }.runTaskTimer(plugin, 0L, 20L * 15L);
      player.setMetadata("workerTask", new FixedMetadataValue(plugin, task));
   }

   public void sendActionBar(Player player, String message) {
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
   }


   private void sendDiscordNotification(String playerName) {
      try {
         URL url = new URL("https://discord.com/api/webhooks/1220560990425583789/CBBUcwSYkueA8Dh9EEVVLGUWtzUH0ibzT5w7wCh41fKjfwDWkAijCadk1CUReIAu6vhl");
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setRequestMethod("POST");
         conn.setRequestProperty("Content-Type", "application/json");
         conn.setDoOutput(true);
         String jsonPayload = "{\"embeds\": [{\"title\": \"⭐ Factory Idle ⭐\", \"description\": \"" + playerName + " logged on the server !\", \"thumbnail\": {\"url\": \"https://minotar.net/helm/" + playerName + "/100.png\"}}]}";
         OutputStream os = conn.getOutputStream();

         try {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
         } catch (Throwable var9) {
            if (os != null) {
               try {
                  os.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }
            }

            throw var9;
         }

         if (os != null) os.close();

      } catch (Exception var10) {
      }

   }
}
