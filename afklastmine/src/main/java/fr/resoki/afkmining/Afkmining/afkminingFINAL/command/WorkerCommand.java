package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.Menu.WorkerMenu;

public class WorkerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            new WorkerMenu(player);
            return true;
        }
        return false;
    }
}
