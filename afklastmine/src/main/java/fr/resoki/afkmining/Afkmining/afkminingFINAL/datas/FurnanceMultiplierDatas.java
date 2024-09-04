/* Decompiler 6ms, total 154ms, lines 69 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.datas;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FurnanceMultiplierDatas {
    private static File file;
    private static YamlConfiguration config;
    private static JavaPlugin pluginInstance;

    public static void initialize(String filePath, JavaPlugin pluginInstance) {
        file = new File(filePath);
        FurnanceMultiplierDatas.pluginInstance = pluginInstance;
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static double getPlayerData(Player player) {
        double amount = config.getDouble("furnanceMultiplier_" + player.getName(), 1.0);
        return amount;
    }

    public static void savePlayerDataAsync(Player player, double amount) {
        Bukkit.getScheduler().runTaskAsynchronously(pluginInstance, () -> {
            config.set("furnanceMultiplier_" + player.getName(), amount);

            try {
                config.save(file);
            } catch (IOException var4) {
                var4.printStackTrace();
            }

        });
    }

    public static void resetForAll() {
        config = new YamlConfiguration();

        try {
            config.save(file);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public static void resetPlayerData(Player player) {
        config.set("furnanceMultiplier_" + player.getName(), 0);

        try {
            config.save(file);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}
