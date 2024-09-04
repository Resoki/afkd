package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import java.text.DecimalFormat;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.TiersDatas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DisplayLevelListener implements Listener {
    private final DecimalFormat decimalFormat = new DecimalFormat("#");

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        int prestige = (int) SaveRebirthsDatas.getPlayerData(player.getName());
        String levelStr = "";
        double level = (double) PlayerLevel.getPlayerData(player.getName());
        String tierName = "";
        int tier = (int) TiersDatas.getPlayerData(player.getName());

        String tierDisplay = "";
        if (tier < 5) {
            tierName = ChatColor.translateAlternateColorCodes('&', "&f[&r&e&lT&5&l" + tier + "&f]");
        } else if (tier < 10) {
            tierName = ChatColor.translateAlternateColorCodes('&', "&f[&r&e&lT&5&l" + tier + "&f]");
        } else if (tier < 15) {
            tierName = ChatColor.translateAlternateColorCodes('&', "&f[&r&e&lT&5&l" + tier + "&f]");
        } else if (tier < 20) {
            tierName = ChatColor.translateAlternateColorCodes('&', "&f[&r&e&lT&5&l" + tier + "&f]");
        } else if (tier < 30) {
            tierName = ChatColor.translateAlternateColorCodes('&', "&f[&r&e&lT&5&l" + tier + "&f]");
        } else {
            tierName = ChatColor.translateAlternateColorCodes('&', "&f[&r&e&lT&5&l" + tier + "&f]");
        }


        if (level <= 10.0D) {
            levelStr = "&f[&r&b&l" + "&a&l" + formatAmount(level)+ "&f]";
        } else if (level <= 20.0D) {
            levelStr = "&f[&r&b&l" +"&b&l" + formatAmount(level) + "&f]";
        } else if (level <= 30.0D) {
            levelStr = "&f[&r&b&l" +"&&l" + formatAmount(level) + "&f]";
        } else if (level <= 40.0D) {
            levelStr = "&f[&r&b&l" +"&5&l" + formatAmount(level) + "&f]";
        } else if (level <= 50.0D) {
            levelStr = "&f[&r&b&l" +"&d&l" + formatAmount(level) + "&f]";
        } else if (level >= 60.0D) {
            levelStr = "&f[&r&b&l" +"&c&l" + formatAmount(level)+ "&f]";
        }
        String textDisplay = "";
        if (prestige < 5) {
            textDisplay = ChatColor.translateAlternateColorCodes('&', "&f[&r&d&lR&a&l" + prestige + "&f]");
        } else if (prestige < 10) {
            textDisplay = ChatColor.translateAlternateColorCodes('&', "&f[&r&d&lR&b&l" + prestige + "&f]");
        } else if (prestige < 15) {
            textDisplay = ChatColor.translateAlternateColorCodes('&', "&f[&r&d&lR&d&l" + prestige + "&f]");
        } else if (prestige < 20) {
            textDisplay = ChatColor.translateAlternateColorCodes('&', "&f[&r&d&lR&5&l" + prestige + "&f]");
        } else if (prestige < 30) {
            textDisplay = ChatColor.translateAlternateColorCodes('&', "&f[&r&d&lR&c&l" + prestige + "&f]");
        } else {
            textDisplay = ChatColor.translateAlternateColorCodes('&', "&f[&r&d&lR&4&l" + prestige + "&f]");
        }

        String formattedMessage = tierName + textDisplay + ChatColor.translateAlternateColorCodes('&', "" + levelStr + " ") + event.getFormat();
        event.setFormat(formattedMessage);

        String message = event.getMessage();
        for (Player p : Bukkit.getOnlinePlayers()) if (message.contains(p.getName())) p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
    }

    private static String formatAmount(double amount) {
        String[] suffix = new String[]{"", "K", "M", "B", "T"};
        int index;
        for(index = 0; amount >= 1000.0D && index < suffix.length - 1; ++index) amount /= 1000;
        String formattedAmount;
        if (index == 0) {
            formattedAmount = String.format("%.0f", amount);  // No decimal places if amount < 1000
        } else {
            formattedAmount = String.format("%.2f", amount) + suffix[index];  // Use decimal places for larger amounts
        }
        return formattedAmount;
    }

}
