package fr.resoki.afkmining.Afkmining.afkminingFINAL.datas;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SaveMultiplyDatas {

    private static File file;
    private static YamlConfiguration config;
    private static JavaPlugin pluginInstance;  // Utilisez un terme plus général

    public static void initialize(String filePath, JavaPlugin pluginInstance) {
        file = new File(filePath);
        SaveMultiplyDatas.pluginInstance = pluginInstance;
        config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getPlayerData(String player) {
        double amount = config.getDouble("Multiply_" + player, 1);
        return amount;
    }

    public static void savePlayerDataAsync(Player player, double amount) {
        Bukkit.getScheduler().runTaskAsynchronously(pluginInstance, () -> {
            config.set("Multiply_" + player.getName(), amount);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void resetForAll() {
        config = new YamlConfiguration();

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTopPlayers(int topCount) {
        List<String> playerNames = new ArrayList<>(config.getKeys(false));

        List<String> filteredPlayerNames = playerNames.stream()
                .filter(key -> key.startsWith("Multiply_"))
                .collect(Collectors.toList());

        List<String> sortedPlayers = filteredPlayerNames.stream()
                .sorted((player1, player2) -> Double.compare(config.getDouble(player2), config.getDouble(player1)))
                .collect(Collectors.toList());

        return sortedPlayers.subList(0, Math.min(topCount, sortedPlayers.size()));
    }

}
