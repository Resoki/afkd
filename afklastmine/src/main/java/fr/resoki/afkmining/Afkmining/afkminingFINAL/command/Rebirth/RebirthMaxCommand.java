package fr.resoki.afkmining.Afkmining.afkminingFINAL.command.Rebirth;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthPoints;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class RebirthMaxCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int levelPlayer = PlayerLevel.getPlayerData(player.getName());
            int rebirthPlayer = (int) SaveRebirthsDatas.getPlayerData(player.getName());
            int rebirthsPossible = 0;
            int rebirthCost = 100 + (rebirthPlayer * 50);
            double totalMultiplierToGive = 0.0;

            while (levelPlayer >= rebirthCost) {
                rebirthsPossible++;
                levelPlayer -= rebirthCost; // Réduire le niveau du joueur
                rebirthPlayer++; // Passer au prochain rebirth
                rebirthCost = 100 + (rebirthPlayer * 50); // Calculer le coût du prochain rebirth

                // Calcul du multiplicateur à ajouter pour ce rebirth
                totalMultiplierToGive += calculateMultiplier(rebirthPlayer);
            }

            double currentMultiplier = SaveMultiplyDatas.getPlayerData(player.getName());
            SaveMultiplyDatas.savePlayerDataAsync(player, currentMultiplier + totalMultiplierToGive);

            if (rebirthsPossible > 0) {
                int currentRebirth = (int) SaveRebirthsDatas.getPlayerData(player.getName());
                int newRebirth = currentRebirth + rebirthsPossible;

                int rebirthPointsGained = 0;
                for (int i = currentRebirth + 1; i <= newRebirth; i++) rebirthPointsGained += i;

                SaveRebirthsDatas.savePlayerDataAsync(player, newRebirth);
                PlayerLevel.savePlayerDataAsync(player, 0); // Réinitialiser le niveau du joueur
                //  PlayerBalance.savePlayerData(player, 0); // Réinitialiser le solde du joueur

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&bYou just rebirthed to &l" + newRebirth +
                                "&d(+" + rebirthPointsGained + " &r&drebirth points)"));

                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        "&b&l" + player.getName() + " just rebirthed to R" + newRebirth + "!"));
                SaveRebirthPoints.savePlayerDataAsync(player, SaveRebirthPoints.getPlayerData(player.getName()) + rebirthPointsGained);

                // Envoi du titre au joueur
                String title = ChatColor.translateAlternateColorCodes('&', "&b&lYou just rebirthed to &d&lR" + newRebirth);
                String subtitle = ChatColor.translateAlternateColorCodes('&', "&d(+" + rebirthPointsGained + " rebirths points)");
                player.sendTitle(title, subtitle, 10, 70, 20); // 10 ticks fade-in, 70 ticks stay, 20 ticks fade-out
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                launchFirework(player.getLocation());
            } else if (command != null){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c&lYou don't have enough levels for a rebirth."));
            }
        }
        return true;
    }

    private double calculateMultiplier(int rebirthLevel) {
        return 0.1 + ((rebirthLevel - 1) * 0.15);
    }

    private void launchFirework(Location location) {
        Firework firework = (Firework) location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .with(Type.BALL)
                .withColor(Color.GREEN)
                .build();
        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(1);
        firework.setFireworkMeta(fireworkMeta);
    }
}
