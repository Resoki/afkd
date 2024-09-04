package fr.resoki.afkmining.Afkmining.afkminingFINAL.command.Rebirth;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu.RebirthMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

    public class RebirthCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) new RebirthMenu(((Player) sender).getPlayer());
        return true;
    }
}
