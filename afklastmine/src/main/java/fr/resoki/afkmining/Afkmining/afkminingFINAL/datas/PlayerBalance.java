/* Decompiler 3ms, total 145ms, lines 38 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.datas;

import java.io.File;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerBalance {
    private static File file;
    private static JavaPlugin pluginInstance;
    private static Economy economy;

    public static void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = (Economy)rsp.getProvider();
        } else {
            Bukkit.getLogger().severe("Vault not found! Economy features will not work.");
        }

    }

    public static double getPlayerData(String playerName) {
        double balance = economy.getBalance(playerName);
        return balance;
    }

    public static void savePlayerData(Player player, double amount) {
        economy.depositPlayer(player, amount);
    }

    public static void withdrawPlayerData(Player player, double amount) {
        economy.withdrawPlayer(player, amount);
    }
}
