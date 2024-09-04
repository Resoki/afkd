/* Decompiler 13ms, total 155ms, lines 72 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.SaveMultiplyDatas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TopMultiplyMenu {
   private final DecimalFormat decimalFormat2 = new DecimalFormat("#.##");

   public TopMultiplyMenu(Player player) {
      String serverName = ServerConfig.getServerName();
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.translateAlternateColorCodes('&', serverName + " → &6&lTop Multiply"));
      List<String> topPlayers = SaveMultiplyDatas.getTopPlayers(23);
      int rank = 1;
      Iterator players = topPlayers.iterator();

      while(players.hasNext()) {
         String playerName = (String)players.next();
         ItemStack playerHead = this.getPlayerHead(playerName.replace("Multiply_", ""));
         if (playerHead != null) {
            ItemMeta meta = playerHead.getItemMeta();
            if (meta != null) {
               meta.setDisplayName(ChatColor.GOLD + "#" + rank + " - " + playerName.replace("Multiply_", "") + ChatColor.RESET + " - " + ChatColor.YELLOW + this.decimalFormat2.format(SaveMultiplyDatas.getPlayerData(playerName.replace("Multiply_", ""))) + "X");
               playerHead.setItemMeta(meta);
               inventory.setItem(rank - 1, playerHead);
               ++rank;
            }
         }
      }
      generateCases(inventory, 26, Material.RED_BANNER, ChatColor.DARK_RED + "Close", "Close menu");
      player.openInventory(inventory);
   }

   public static void generateCases(Inventory inventory, int position, Material material, String displayName, String description) {
      ItemStack item = new ItemStack(material, 1);
      ItemMeta meta = item.getItemMeta();
      if (meta != null) {
         meta.setDisplayName(displayName);
         meta.setLore(Arrays.asList(description));
         item.setItemMeta(meta);
         inventory.setItem(position, item);
      }
   }

   private ItemStack getPlayerHead(String playerName) {
      ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
      SkullMeta meta = (SkullMeta)skull.getItemMeta();
      if (meta != null) {
         meta.setOwner(playerName);
         skull.setItemMeta(meta);
         return skull;
      } else {
         return null;
      }
   }
}
