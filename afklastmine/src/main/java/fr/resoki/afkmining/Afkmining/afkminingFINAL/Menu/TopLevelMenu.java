/* Decompiler 10ms, total 137ms, lines 69 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TopLevelMenu {
   public TopLevelMenu(Player player) {
      String serverName = ServerConfig.getServerName();
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.translateAlternateColorCodes('&', serverName + " → &6&lTop Level"));
      List<String> topPlayers = PlayerLevel.getTopPlayers(23);
      int rank = 1;
      Iterator var6 = topPlayers.iterator();

      while(var6.hasNext()) {
         String playerName = (String)var6.next();
         ItemStack playerHead = this.getPlayerHead(playerName.replace("PlayerLevel_", ""));
         if (playerHead != null) {
            ItemMeta meta = playerHead.getItemMeta();
            if (meta != null) {
               ChatColor var10001 = ChatColor.GOLD;
               meta.setDisplayName(var10001 + "#" + rank + " - " + playerName.replace("PlayerLevel_", "") + ChatColor.RESET + " - " + ChatColor.YELLOW + "level " + PlayerLevel.getPlayerData(playerName.replace("PlayerLevel_", "")));
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
