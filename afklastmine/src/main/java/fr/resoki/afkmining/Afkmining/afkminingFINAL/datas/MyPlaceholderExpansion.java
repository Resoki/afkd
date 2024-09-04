package fr.resoki.afkmining.Afkmining.afkminingFINAL.datas;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.CurrentXp;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthPoints;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyPlaceholderExpansion extends PlaceholderExpansion {

   @Override
   @NotNull
   public String getAuthor() {
      return "Author";
   }

   @Override
   @NotNull
   public String getIdentifier() {
      return "factory";
   }

   @Override
   @NotNull
   public String getVersion() {
      return "1.0.0";
   }

   @Override
   public String onRequest(OfflinePlayer player, @NotNull String params) {
      if (params.equalsIgnoreCase("user_multiplier")) {
         return String.format("%.2f", SaveMultiplyDatas.getPlayerData(player.getName()));
      }

      if (params.equalsIgnoreCase("user_level")) {
         return String.valueOf(formatAmount(PlayerLevel.getPlayerData(player.getName())));
      }

      if (params.equalsIgnoreCase("user_tier")) {
         return String.valueOf(TiersDatas.getPlayerData(player.getName()));
      }

      if (params.equalsIgnoreCase("user_gems")) {
         return String.format("%.2f",SaveGemsDatas.getPlayerData(player.getName()));
      }
      if (params.equalsIgnoreCase("user_current_xp")) {
         return String.valueOf((int) CurrentXp.getPlayerData((Player) player));
      }
      if (params.equalsIgnoreCase("user_furnace_level")) {
         return String.format("%.2f",FurnanceMultiplierDatas.getPlayerData((Player) player));
      }
      if (params.equalsIgnoreCase("user_rebirth")) {
         return String.valueOf((int) SaveRebirthsDatas.getPlayerData(player.getName()));
      }
      if (params.equalsIgnoreCase("user_rebirth_points")) {
         return String.valueOf((int) SaveRebirthPoints.getPlayerData(player.getName()));
      }


      if (params.equalsIgnoreCase("user_pourcentage")) {
         double playerLevel = PlayerLevel.getPlayerData(player.getName());
         double currentXp = CurrentXp.getPlayerData((Player) player);
         int xpNeeded = (int) (100 + (playerLevel - 1) * 10);

         double xpPercentage = (currentXp / xpNeeded) * 100;

         return String.format("%.2f", xpPercentage) + "%";
      }

      if (params.equalsIgnoreCase("user_currentblock")) {
         return String.valueOf((int) SaveActualBlockData.getPlayerData(player.getName()) +1);
      }

      if (params.equalsIgnoreCase("totalblock")) {
         Material[] blockList = ServerConfig.getBlockList();
         return String.valueOf(blockList.length);
      }

      if (params.equalsIgnoreCase("user_needed_xp")) {
         int playerLevel = PlayerLevel.getPlayerData(player.getName());
         int xpNeeded = 100 + (playerLevel - 1) * 10;

         return String.valueOf(xpNeeded);
      }

      if (params.startsWith("username_multiply_top_")) {
         int prefixLength = "username_multiply_top_".length();
         String numberPart = params.substring(prefixLength); // Extract part after the prefix
         try {
            int position = Integer.parseInt(numberPart) - 1; // Adjust index for 0-based array
            List<String> topPlayers = SaveMultiplyDatas.getTopPlayers(23);

            if (position >= 0 && position < topPlayers.size()) {
               return topPlayers.get(position).replace("Multiply_", "");
            } else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }
      if (params.startsWith("value_multiply_top_")) {
         int prefixLength = "value_multiply_top_".length(); // Longueur du préfixe
         String numberPart = params.substring(prefixLength); // Extraire la partie après le préfixe
         try {
            int position = Integer.parseInt(numberPart) - 1; // Ajuster l'index pour le tableau à base zéro
            List<String> topPlayers = SaveMultiplyDatas.getTopPlayers(23);

            if (position >= 0 && position < topPlayers.size()) {
               String playerName = topPlayers.get(position).replace("Multiply_", "");
               double multiplierValue = SaveMultiplyDatas.getPlayerData(playerName);
               return String.format("%.2f", multiplierValue);
            } else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }


// Placeholder pour obtenir le nom d'utilisateur
      if (params.startsWith("username_level_top_")) {
         try {
            int prefixLength = "username_level_top_".length(); // Longueur du préfixe
            String numberPart = params.substring(prefixLength); // Extraire la partie après le préfixe
            int position = Integer.parseInt(numberPart) - 1;  // Ajuster l'index pour le tableau à base zéro
            List<String> topPlayers = PlayerLevel.getTopPlayers(10);
            if (position >= 0 && position < topPlayers.size()) {
               return topPlayers.get(position).replace("PlayerLevel_", "");
            } else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }

// Placeholder pour obtenir le niveau du joueur
      if (params.startsWith("value_level_top_")) {
         try {
            int prefixLength = "value_level_top_".length(); // Longueur du préfixe
            String numberPart = params.substring(prefixLength); // Extraire la partie après le préfixe
            int position = Integer.parseInt(numberPart) - 1;  // Ajuster l'index pour le tableau à base zéro
            List<String> topPlayers = PlayerLevel.getTopPlayers(10);
            if (position >= 0 && position < topPlayers.size()) {
               String playerName = topPlayers.get(position).replace("PlayerLevel_", "");
               int playerLevel = PlayerLevel.getPlayerData(playerName); // Obtenir le niveau du joueur
               return String.valueOf(playerLevel);
            } else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }

      if (params.startsWith("username_rebirth_top_")) {
         try {
            int prefixLength = "username_rebirth_top_".length(); // Longueur du préfixe
            String numberPart = params.substring(prefixLength); // Extraire la partie après le préfixe
            int position = Integer.parseInt(numberPart) - 1;  // Ajuster l'index pour le tableau à base zéro
            List<String> topPlayers = SaveRebirthsDatas.getTopPlayers(10);
            if (position >= 0 && position < topPlayers.size()) {
               return topPlayers.get(position).replace("Rebirths_", "");
            }else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }


// Placeholder pour obtenir le niveau du joueur
      if (params.startsWith("value_rebirth_top_")) {
         try {
            int prefixLength = "value_rebirth_top_".length(); // Longueur du préfixe
            String numberPart = params.substring(prefixLength); // Extraire la partie après le préfixe
            int position = Integer.parseInt(numberPart) - 1;  // Ajuster l'index pour le tableau à base zéro
            List<String> topPlayers = SaveRebirthsDatas.getTopPlayers(10);
            if (position >= 0 && position < topPlayers.size()) {
               String playerName = topPlayers.get(position).replace("Rebirths_", "");
               int playerLevel = (int) SaveRebirthsDatas.getPlayerData(playerName); // Obtenir le niveau du joueur
               return String.valueOf(playerLevel);
            }else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }


      if (params.startsWith("username_tier_top_")) {
         try {
            int prefixLength = "username_tier_top_".length(); // Longueur du préfixe
            String numberPart = params.substring(prefixLength); // Extraire la partie après le préfixe
            int position = Integer.parseInt(numberPart) - 1;  // Ajuster l'index pour le tableau à base zéro
            List<String> topPlayers = TiersDatas.getTopPlayers(10);
            if (position >= 0 && position < topPlayers.size()) {
               return topPlayers.get(position).replace("TiersDatas_", "");
            }else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }

// Placeholder pour obtenir le niveau du joueur
      if (params.startsWith("value_tier_top_")) {
         try {
            int prefixLength = "value_tier_top_".length(); // Longueur du préfixe
            String numberPart = params.substring(prefixLength); // Extraire la partie après le préfixe
            int position = Integer.parseInt(numberPart) - 1;  // Ajuster l'index pour le tableau à base zéro
            List<String> topPlayers = TiersDatas.getTopPlayers(10);
            if (position >= 0 && position < topPlayers.size()) {
               String playerName = topPlayers.get(position).replace("TiersDatas_", "");
               int playerLevel = (int) TiersDatas.getPlayerData(playerName); // Obtenir le niveau du joueur
               return String.valueOf(playerLevel);
            }else {
               return "";
            }
         } catch (NumberFormatException e) {
            return "";
         }
      }
         return null; //
   }

   private static String formatAmount(double amount) {
      String[] suffix = new String[]{"", "K", "M", "B", "T"};
      int index;
      for(index = 0; amount >= 1000.0D && index < suffix.length - 1; ++index) {
         amount /= 1000;
      }
      String formattedAmount;
      if (index == 0) {
         formattedAmount = String.format("%.0f", amount);  // No decimal places if amount < 1000
      } else {
         formattedAmount = String.format("%.2f", amount) + suffix[index];  // Use decimal places for larger amounts
      }
      return formattedAmount;
   }



}