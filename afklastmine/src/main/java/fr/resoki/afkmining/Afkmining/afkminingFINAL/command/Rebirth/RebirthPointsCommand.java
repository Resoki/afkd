package fr.resoki.afkmining.Afkmining.afkminingFINAL.command.Rebirth;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthPoints;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RebirthPointsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            int points = (int) SaveRebirthPoints.getPlayerData(((Player) sender).getPlayer().getName());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() +  " → &dYou have &l" + String.valueOf(points)) + " rebirth points");
        }
        return true;
    }
}
