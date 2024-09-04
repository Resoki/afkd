package fr.resoki.afkmining.Afkmining.afkminingFINAL;

import java.util.Arrays;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.DefaultSpeedRespawn;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.PlayerBalance;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.SaveActualBlockData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradeMenu {

    public UpgradeMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder) null, 27, ChatColor.translateAlternateColorCodes('&', "&d&lUpgrade Block"));
        for (int i = 0; i < 27; ++i) generateCases(inventory, i, Material.GREEN_STAINED_GLASS_PANE, "", "");

        double currentBlock = SaveActualBlockData.getPlayerData(player.getName());
        Material[] blockList = ServerConfig.getBlockList();
        long playerBalance = (long) PlayerBalance.getPlayerData(player.getName());

        int price = (int) ((currentBlock * 500) * 1.78);
        if (price == 0) price = 200;

        if (currentBlock == blockList.length - 1) {
            generateCases(inventory, 12, blockList[(int) (currentBlock % blockList.length)], ChatColor.translateAlternateColorCodes('&', "&c&lMAX"), ChatColor.translateAlternateColorCodes('&', "&c&lLevel max reached !"));
        } else {
            Material nextBlockType = blockList[(int) (currentBlock + 1) % blockList.length];
            generateCases(inventory, 12, nextBlockType,
                    ChatColor.translateAlternateColorCodes('&', "&6&lUpgrade To Next Block (" + (currentBlock + 1) + "/" + blockList.length + ")"),
                    ChatColor.translateAlternateColorCodes('&', "&a&l$" + price + " &d&l+0.5X"));
        }

        int maxAffordableLevel = calculateMaxAffordableBlockLevel(currentBlock, blockList.length, playerBalance);
        double maxUpgradePrice = calculateMaxUpgradePrice(currentBlock, maxAffordableLevel);

        if (currentBlock == blockList.length - 1) {
            generateCases(inventory, 22, Material.ANVIL,
                    ChatColor.translateAlternateColorCodes('&', "&e&lMax Upgrade"),
                    ChatColor.translateAlternateColorCodes('&', "&c&lLevel max reached !"));
        } else {
            if (maxUpgradePrice == 0) {
                generateCases(inventory, 22, Material.ANVIL,
                        ChatColor.translateAlternateColorCodes('&', "&e&lMax Upgrade"),
                        ChatColor.translateAlternateColorCodes('&', "&c&lYou can't afford this upgrade."));
            } else {
                generateCases(inventory, 22, Material.ANVIL,
                        ChatColor.translateAlternateColorCodes('&', "&e&lMax Upgrade"),
                        ChatColor.translateAlternateColorCodes('&', "&a&l$" + String.format("%.2f", maxUpgradePrice) + " &d&l(Level " + maxAffordableLevel + ")"));

            }
        }

        long defaultSpeed = DefaultSpeedRespawn.getPlayerData(player);
        double second = 2.0;
        double basePrice = 1000;
        double priceMultiplier = 1.2;
        double defaultPrice;

        second = defaultSpeed / 20.0;

        defaultPrice = basePrice * Math.pow(priceMultiplier, (2.0 - second) / 0.1);

        String formattedSecond = String.format("%.1f", second);
        String nextSecond = String.format("%.1f", (second - 0.1));

        if (nextSecond.equals("0.9")) {
            generateCases(inventory, 14, Material.FEATHER,
                    ChatColor.translateAlternateColorCodes('&', "&b&lSpeed Block Respawn"),
                    ChatColor.translateAlternateColorCodes('&', "&c&lLevel max reached !"));

        } else {
            generateCases(inventory, 14, Material.FEATHER,
                    ChatColor.translateAlternateColorCodes('&', "&b&lSpeed Block Respawn"),
                    ChatColor.translateAlternateColorCodes('&', "\n" + ChatColor.YELLOW + formattedSecond + "s => " + (nextSecond) + "s" + "\n" + ChatColor.GREEN + "$" + String.format("%.2f", (defaultPrice))));
        }

        player.openInventory(inventory);
    }

    public static void generateCases(Inventory inventory, int position, Material material, String displayName, String description) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(description.split("\n")));
        item.setItemMeta(meta);
        inventory.setItem(position, item);
    }

    private double calculateMaxUpgradePrice(double currentBlock, int maxLevel) {
        double totalCost = 0;
        double priceMultiplier = 1.78;
        for (int i = (int) currentBlock; i < maxLevel; i++){
            if (i == 0){
                totalCost += 200;
                continue;
            }
            totalCost += (i * 500) * priceMultiplier;
        }

        return totalCost;
    }

    private int calculateMaxAffordableBlockLevel(double currentBlock, int maxLevel, long playerBalance) {
        double priceMultiplier = 1.78;
        double remainingBalance = playerBalance;
        int maxAffordableLevel = (int) currentBlock;

        while (maxAffordableLevel < maxLevel) {
            double price = (maxAffordableLevel * 500) * priceMultiplier;
            if (remainingBalance < price) break;
            remainingBalance -= price;
            maxAffordableLevel++;
        }

        return maxAffordableLevel;
    }
}
