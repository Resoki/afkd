package fr.resoki.afkmining.Afkmining.afkminingFINAL.command.Rebirth;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu.FurnanceMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RebirthShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) new FurnanceMenu(((Player) sender).getPlayer());
        return true;
    }
}
