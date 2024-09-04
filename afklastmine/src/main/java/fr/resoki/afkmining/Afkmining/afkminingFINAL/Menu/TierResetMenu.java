package fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
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

public class TierResetMenu {
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public TierResetMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder) null, 27, ChatColor.translateAlternateColorCodes('&', "&6&lTier Reset Menu"));
        for (int i = 0; i < 27; ++i) generateCases(inventory, i, Material.YELLOW_STAINED_GLASS_PANE, "", "");

        int rebirthPlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());
        int currentTier = (int) TiersDatas.getPlayerData(player.getName());
        int requiredRebirths = getRequiredRebirthsForTier(currentTier + 1);

        generatePlayerHead(inventory, player, 11, ChatColor.GREEN + player.getName(),
                ChatColor.translateAlternateColorCodes('&', " → &dYou have &l " + rebirthPlayer + " &r&d rebirths"));

        if (rebirthPlayer >= requiredRebirths) {
            generateCases(inventory, 15, Material.NETHER_STAR, ChatColor.translateAlternateColorCodes('&', "&c&lReset All & Pass Tier"),
                    ChatColor.translateAlternateColorCodes('&', "&7Click to reset all levels"),
                    ChatColor.translateAlternateColorCodes('&', "&7and pass to the next tier."),
                    ChatColor.translateAlternateColorCodes('&', "&dCost: &l" + requiredRebirths + " &r&drebirths"));
        } else {
            generateCases(inventory, 15, Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&c&lInsufficient Rebirths"),
                    ChatColor.translateAlternateColorCodes('&', "&7You need &l" + requiredRebirths + " &r&7rebirths to pass to the next tier."),
                    ChatColor.translateAlternateColorCodes('&', "&7You currently have &l" + rebirthPlayer + " &r&7rebirths."));
        }

        // Leave button
        generateCases(inventory, 26, Material.RED_BANNER, ChatColor.DARK_RED + "Leave", "Leave menu");

        player.openInventory(inventory);
    }

    public static void generateCases(Inventory inventory, int position, Material material, String displayName, String... descriptions) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(descriptions));
        item.setItemMeta(meta);
        inventory.setItem(position, item);
    }

    public static void generatePlayerHead(Inventory inventory, Player player, int position, String displayName, String description) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(description));
        head.setItemMeta(meta);
        inventory.setItem(position, head);
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
