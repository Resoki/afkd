package fr.resoki.afkmining.Afkmining.afkminingFINAL.command;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FactoryCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public FactoryCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location factoryLocation = new Location(player.getWorld(), 256, 98, -91);
            factoryLocation.setYaw(220);
            player.teleport(factoryLocation);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() +   " &aYou have been teleported to the factory!"));
            return true;
        } else {
            sender.sendMessage("This command can only be used by players.");
            return false;
        }
    }
}
