package fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu;

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

public class RebirthMenu {
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public RebirthMenu(Player player) {
        int prestigePlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());
        Inventory inventory = Bukkit.createInventory((InventoryHolder) null, 27, ChatColor.translateAlternateColorCodes('&', "&d&lRebirth"));

        for (int i = 0; i < 27; ++i) if (i != 11 && i != 15 && i != 26) generateCases(inventory, i, Material.PINK_STAINED_GLASS_PANE, "", "");

        int rebirthPlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());

        int rebirthCost = 100 + (rebirthPlayer * 50);
        double nextMultiplier = calculateMultiplier(rebirthPlayer + 1);

        generatePlayerHead(inventory, player, 11, ChatColor.GREEN + player.getName(), ChatColor.translateAlternateColorCodes('&', "→ &dYou have &l " + prestigePlayer + " &r&d rebirths"));

        generateCases(inventory, 15, Material.EXPERIENCE_BOTTLE, ChatColor.translateAlternateColorCodes('&', "&c&lRebirth"),
                ChatColor.translateAlternateColorCodes('&', "→ &dLvl &l" + rebirthCost + " &r&dto rebirth"),
                ChatColor.translateAlternateColorCodes('&', "→ &bMulti: &l+X" + decimalFormat.format(nextMultiplier)),
                ChatColor.translateAlternateColorCodes('&', "→ &dRebirth points: &l+" + decimalFormat.format(1)));
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

    private double calculateMultiplier(int rebirthLevel) {
        return 0.1 + ((rebirthLevel - 1) * 0.15);
    }
}
