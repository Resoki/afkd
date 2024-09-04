/* Decompiler 2ms, total 143ms, lines 21 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu.TopLevelMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelTopCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("Only players can use this command!");
         return true;
      } else {
         Player player = (Player)sender;
         new TopLevelMenu(player);
         return true;
      }
   }
}
