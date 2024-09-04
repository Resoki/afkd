/* Decompiler 4ms, total 131ms, lines 37 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.SaveMultiplyDatas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GiveMultiplyCommand implements CommandExecutor {
   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!sender.hasPermission("multiply.give.admin")) {
         sender.sendMessage(ChatColor.RED + "You dont have permission to do this !");
         return false;
      }
      if (args.length < 2) {
         sender.sendMessage("Not enough arguments!");
         return false;
      }

      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
         sender.sendMessage("Player not found!");
         return false;
      }
      double currentPlayerMulti = SaveMultiplyDatas.getPlayerData(target.getName());

      String operation = args[1];
      double multiplier = args.length > 2 ? Double.parseDouble(args[2]) : 0;

      switch (operation) {
         case "add":
            SaveMultiplyDatas.savePlayerDataAsync(target, currentPlayerMulti + multiplier);
            break;
         case "remove":
            SaveMultiplyDatas.savePlayerDataAsync(target, currentPlayerMulti - multiplier);
            break;
         case "set":
            SaveMultiplyDatas.savePlayerDataAsync(target, multiplier);
            break;
         case "reset":
            SaveMultiplyDatas.savePlayerDataAsync(target, 1);
            break;
         default:
            sender.sendMessage("Invalid operation!");
            return false;
      }

      return true;
   }
}