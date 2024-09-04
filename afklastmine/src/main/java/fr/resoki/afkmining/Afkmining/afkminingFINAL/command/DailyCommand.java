/* Decompiler 9ms, total 192ms, lines 58 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.PlayerAlreadyClaimedDaily;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.SaveMultiplyDatas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalTime;

public class DailyCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (sender instanceof Player) {
         boolean alreadyClaimedToday = PlayerAlreadyClaimedDaily.getPlayerData((Player)sender);
         String timeUntilMidnight;
         String dashedLine;
         String chatgameTitle;
         String msg1;
         String msg2;
         String formattedMessage;
         if (alreadyClaimedToday) {
            LocalTime now = LocalTime.now();
            LocalTime midnight = LocalTime.MIDNIGHT;
            if (now.isAfter(midnight)) midnight = LocalTime.of(23, 59, 59);

            Duration durationUntilMidnight = Duration.between(now, midnight);
            timeUntilMidnight = String.format("%02d:%02d:%02d", durationUntilMidnight.toHours(), durationUntilMidnight.toMinutesPart(), durationUntilMidnight.toSecondsPart());
            dashedLine = ChatColor.GRAY + "---------------------------";
            chatgameTitle = ChatColor.translateAlternateColorCodes('&', "     &6&l⭐   DAILY   ⭐");
            msg1 = ChatColor.translateAlternateColorCodes('&', "&cYou already claimed your daily today !");
            msg2 = ChatColor.translateAlternateColorCodes('&', "&cYou can in: " + timeUntilMidnight + " ⌛");
            formattedMessage = dashedLine + "\n" + chatgameTitle + "\n" + msg1 + "\n" + msg2 + "\n" + dashedLine;
            sender.sendMessage(formattedMessage);
         } else {
            Player player = (Player)sender;
            double currentMultiply = SaveMultiplyDatas.getPlayerData(player.getName());
            SaveMultiplyDatas.savePlayerDataAsync(player, currentMultiply + 2);
            timeUntilMidnight = ChatColor.GRAY + "---------------------------";
            dashedLine = ChatColor.translateAlternateColorCodes('&', "     &6&l⭐   DAILY   ⭐");
            chatgameTitle = ChatColor.translateAlternateColorCodes('&', "&e&lYou just claimed your daily !");
            msg1 = ChatColor.translateAlternateColorCodes('&', "&e+2 Multiplier");
            msg2 = timeUntilMidnight + "\n" + dashedLine + "\n" + chatgameTitle + "\n" + msg1 + "\n" + timeUntilMidnight;
            formattedMessage = ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " just claim rewards with /daily !");
            Bukkit.broadcastMessage(formattedMessage);
            sender.sendMessage(msg2);
            PlayerAlreadyClaimedDaily.savePlayerDataAsync((Player)sender, true);
         }
      }

      return true;
   }
}
