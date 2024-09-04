package fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.FurnanceMultiplierDatas;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthPoints;
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

public class FurnanceMenu {
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public FurnanceMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder) null, 27, ChatColor.translateAlternateColorCodes('&', "&6&lUpgrade Furnace"));

        for (int i = 0; i < 27; ++i) generateCases(inventory, i, Material.PINK_STAINED_GLASS_PANE, "", "");

        int rebirthPointPlayer = (int) SaveRebirthPoints.getPlayerData(player.getName());
        double currentMultiplier = getCurrentMultiplier(player);
        double nextMultiplier = currentMultiplier + 0.1;
        int upgradeCost = calculateUpgradeCost(currentMultiplier);

        generatePlayerHead(inventory, player, 11, ChatColor.GREEN + player.getName(), ChatColor.translateAlternateColorCodes('&', "→ &dYou have &l " + rebirthPointPlayer + " &r&d rebirth points"));

        generateCases(inventory, 15, Material.FURNACE, ChatColor.translateAlternateColorCodes('&', "&6&lUpgrade Furnace Multiply"),
                ChatColor.translateAlternateColorCodes('&', "→ &dCurrent Multiplier: &l" + decimalFormat.format(currentMultiplier)),
                ChatColor.translateAlternateColorCodes('&', "→ &dNext Multiplier: &l" + decimalFormat.format(nextMultiplier)),
                ChatColor.translateAlternateColorCodes('&', "→ &dCost: &l" + upgradeCost + " &r&drebirth points"));
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

    private double getCurrentMultiplier(Player player) {
        return FurnanceMultiplierDatas.getPlayerData(player);
    }

    private int calculateUpgradeCost(double currentMultiplier) {
        double baseCost = 10; // The cost for the first upgrade to 1.1
        int multiplierLevel = (int) ((currentMultiplier - 1.0) * 10); // Determine the current level
        return (int) (baseCost * Math.pow(2.5, multiplierLevel));
    }
}