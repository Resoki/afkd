package fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Worker;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkerLevel {
   private static File file;
   private static YamlConfiguration config;
   private static JavaPlugin pluginInstance;

   public static void initialize(String filePath, JavaPlugin pluginInstance) {
      file = new File(filePath);
      WorkerLevel.pluginInstance = pluginInstance;
      config = YamlConfiguration.loadConfiguration(file);
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

   }

   public static int getPlayerData(String playername) {
      int amount = config.getInt("WorkerLevel_" + playername, 1);
      return amount;
   }

   public static void savePlayerDataAsync(Player player, int amount) {
      Bukkit.getScheduler().runTaskAsynchronously(pluginInstance, () -> {
         config.set("WorkerLevel_" + player.getName(), amount);

         try {
            config.save(file);
         } catch (IOException var3) {
            var3.printStackTrace();
         }

      });
   }

   public static void resetForAll() {
      config = new YamlConfiguration();

      try {
         config.save(file);
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static void resetPlayerData(Player player) {
      config.set("WorkerLevel_" + player.getName(), 1);

      try {
         config.save(file);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public static List<String> getTopPlayers(int topCount) {
      List<String> playerNames = new ArrayList(config.getKeys(false));
      List<String> filteredPlayerNames = (List)playerNames.stream().filter((key) -> {
         return key.startsWith("WorkerLevel_");
      }).collect(Collectors.toList());
      List<String> sortedPlayers = (List)filteredPlayerNames.stream().sorted((player1, player2) -> {
         return Integer.compare(config.getInt(player2), config.getInt(player1));
      }).collect(Collectors.toList());
      return sortedPlayers.subList(0, Math.min(topCount, sortedPlayers.size()));
   }

   public static int getPlayerRank(String playerName) {
      int playerMultiply = getPlayerData(playerName);
      List<String> sortedPlayers = getTopPlayers(Integer.MAX_VALUE);
      int playerIndex = sortedPlayers.indexOf("WorkerLevel_" + playerName);
      return playerIndex == -1 ? -1 : playerIndex + 1;
   }
}
