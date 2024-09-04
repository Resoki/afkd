package fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Worker.WorkerLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class WorkerMenu {
    public WorkerMenu(Player player) {
        int WorkerUpgradeLevel = WorkerLevel.getPlayerData(player.getName());
        int price = (int) (WorkerUpgradeLevel * 5000 * 1.78);
        Inventory workerMenu = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', "&6&lWorker menu"));
        int workerLevel = WorkerLevel.getPlayerData(player.getName());
        for (int i = 0; i < 27; ++i) {
            generateCases(workerMenu, i, Material.WHITE_STAINED_GLASS_PANE, "", "");
            if (i == 11) generateCases(workerMenu, i, Material.BOOK, ChatColor.translateAlternateColorCodes('&',"&f&lYour level"), ChatColor.translateAlternateColorCodes('&',"&aYou worker is level &l" + String.valueOf(workerLevel)));
            if (i == 15) generateCases(workerMenu, i, Material.CHEST, ChatColor.translateAlternateColorCodes('&',"&f&lWorker Upgrade"),  ChatColor.translateAlternateColorCodes('&',"&aPrice: &l$"+String.valueOf(formatNumber(price))));
        }
        player.openInventory(workerMenu);
    }

    public static void generateCases(Inventory inventory, int position, Material material, String displayName, String... descriptions) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(descriptions));
        item.setItemMeta(meta);
        inventory.setItem(position, item);
    }

    public String formatNumber(double number) {
        char[] suffix = {' ', 'K', 'M', 'B', 'T'};
        int tier = 0;

        while (number >= 1000) {
            number /= 1000;
            tier++;
        }

        return String.format("%.2f%c", number, suffix[tier]);
    }

}
