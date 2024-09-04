/* Decompiler 2ms, total 137ms, lines 21 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu.TopMultiplyMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MultiplyTopCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("Only players can use this command!");
         return true;
      } else {
         Player player = (Player)sender;
         new TopMultiplyMenu(player);
         return true;
      }
   }
}
