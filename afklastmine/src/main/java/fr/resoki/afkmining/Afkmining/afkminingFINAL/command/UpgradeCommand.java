package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.UpgradeMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpgradeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) new UpgradeMenu(((Player) sender).getPlayer());
        return true;
    }
}
