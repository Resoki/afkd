/* Decompiler 8ms, total 421ms, lines 73 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatWordListener implements Listener {
    private String currentWord;
    private String currentCalculation;
    private int currentResult;
    private long startTime;
    private Set<Player> winners = new HashSet();
    private JavaPlugin plugin;

    public ChatWordListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
        this.currentCalculation = null;
        this.startTime = System.currentTimeMillis();
        this.winners.clear();
    }

    public void setCurrentCalculation(String currentCalculation, int currentResult) {
        this.currentCalculation = currentCalculation;
        this.currentResult = currentResult;
        this.currentWord = null;
        this.startTime = System.currentTimeMillis();
        this.winners.clear();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        if (this.currentWord != null && message.equalsIgnoreCase(this.currentWord) || this.currentCalculation != null && this.isNumeric(message) && Integer.parseInt(message) == this.currentResult) {
            if (this.winners.contains(player) || !this.winners.isEmpty()) {
                return;
            }

            long elapsedTime = System.currentTimeMillis() - this.startTime;
            long seconds = elapsedTime / 1000L;
            long milliseconds = elapsedTime % 1000L;

            String playerName = player.getName();

            double multiplier = ThreadLocalRandom.current().nextDouble(0.1, 0.4);
            multiplier = Math.round(multiplier * 10.0) / 10.0; // Round to 1 decimal place

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    "&e&l⭐ &r&6&lCHATGAME &e&l⭐ → &r &6" + playerName +
                            " win with the " + (this.currentWord != null ? "word &6&l" + this.currentWord
                            : "calculation &6&l" + this.currentCalculation + " = " + this.currentResult) +
                            " &r&6on " + seconds + "." + milliseconds + " seconds"));

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&e&l⭐ &r&6&lCHATGAME &e&l⭐ → &r &eYou win &6&lX" + multiplier + " &r&6multiply !"));

            this.winners.add(player);

            double finalMultiplier = multiplier;
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                String keyCommand = "givemultiplier " + player.getPlayer().getName() + " " + finalMultiplier;
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), keyCommand);
            });
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }
}
