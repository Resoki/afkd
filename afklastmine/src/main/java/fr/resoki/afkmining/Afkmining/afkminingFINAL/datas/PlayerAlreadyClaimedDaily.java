/* Decompiler 6ms, total 144ms, lines 58 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.datas;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class PlayerAlreadyClaimedDaily {
   private static File file;
   private static YamlConfiguration config;
   private static JavaPlugin pluginInstance;

   public static void initialize(String filePath, JavaPlugin pluginInstance) {
      file = new File(filePath);
      PlayerAlreadyClaimedDaily.pluginInstance = pluginInstance;
      config = YamlConfiguration.loadConfiguration(file);
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

   }

   public static boolean getPlayerData(Player player) {
      boolean amount = config.getBoolean("Players_Already_Claimed_Daily_" + player.getName(), false);
      return amount;
   }

   public static void resetForAll() {
      config = new YamlConfiguration();

      try {
         config.save(file);
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static void savePlayerDataAsync(Player player, boolean value) {
      Bukkit.getScheduler().runTaskAsynchronously(pluginInstance, () -> {
         config.set("Players_Already_Claimed_Daily_" + player.getName(), value);

         try {
            config.save(file);
         } catch (IOException var3) {
            var3.printStackTrace();
         }

      });
   }
}
