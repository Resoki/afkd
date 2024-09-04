package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import com.google.common.util.concurrent.AtomicDouble;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.CurrentXp;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class BlockBreak implements Listener {
    private JavaPlugin plugin;
    private static final Logger logger = Logger.getLogger("Minecraft");

    private final Map<Player, AtomicDouble> playerGainsMoney = new ConcurrentHashMap<>();

    private final Map<Player, BukkitRunnable> playerTasks = new ConcurrentHashMap<>();

    private final Map<Player, Double> moneyCount = new HashMap();

    public BlockBreak(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Location targetLocation = new Location(Bukkit.getWorld("world"), -59, 78, -138);

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Location blockLocation = block.getLocation();

        if (blockLocation.equals(targetLocation) && player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage(ChatColor.RED + "Can't break this block in creative mode!");
            return;
        }

        if (blockLocation.equals(targetLocation) && block.getType() != Material.BEDROCK) {
            event.setCancelled(true);
            player.sendBlockChange(block.getLocation(), Material.AIR.createBlockData());

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendBlockChange(block.getLocation(), Material.BEDROCK.createBlockData());
                    moveGrassBlock(player, block.getLocation().clone().add(new Vector(-1, 0, 0)), 6, 5);
                    long actual = DefaultSpeedRespawn.getPlayerData(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            double currentBlock = SaveActualBlockData.getPlayerData(player.getName());
                            Material[] blockList = ServerConfig.getBlockList();
                            player.sendBlockChange(block.getLocation(), blockList[(int) (currentBlock % blockList.length)].createBlockData());
                        }
                    }.runTaskLater(plugin, actual);
                }
            }.runTaskLater(this.plugin, 2L);
        }
    }


    private void moveGrassBlock(Player player, Location startLocation, int zDistance, int xDistance) {
        int totalSteps = zDistance + xDistance;
        double distancePerTick = 1.0;

        new BukkitRunnable() {
            double step = 0;
            boolean rewardGiven = false;

            @Override
            public void run() {
                if (step <= totalSteps) {
                    Location grassLocation;

                    if (step <= zDistance) grassLocation = startLocation.clone().add(new Vector(0, 0, -step));

                    else {
                        double xStep = step - zDistance; // Déplacement restant en x
                        grassLocation = startLocation.clone().add(new Vector(-xStep, 0, -zDistance));
                    }

                    double currentBlock = SaveActualBlockData.getPlayerData(player.getName());
                    Material[] blockList = ServerConfig.getBlockList();
                    player.sendBlockChange(grassLocation, blockList[(int) (currentBlock % blockList.length)].createBlockData());

                    if (step > 0) {
                        Location previousLocation;

                        if (step <= zDistance) {
                            previousLocation = startLocation.clone().add(new Vector(0, 0, -step + 1));
                        } else {
                            double xStep = step - zDistance;
                            previousLocation = startLocation.clone().add(new Vector(-xStep + 1, 0, -zDistance));
                        }

                        player.sendBlockChange(previousLocation, Material.AIR.createBlockData());
                    }

                    step += distancePerTick;
                } else {
                    // Terminer à la fin du chemin
                    Location finalLocation = startLocation.clone().add(new Vector(-xDistance, 0, -zDistance));
                    player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, finalLocation, 1);
                    player.sendBlockChange(finalLocation, Material.AIR.createBlockData());
                    cancel();

                    double xpToReward = 0;
                    double moneyReward = 0;
                    if (!rewardGiven) {
                        Random random = new Random();

                        double currentTier = SaveActualBlockData.getPlayerData(player.getName());
                        double currentM = SaveMultiplyDatas.getPlayerData(player.getName());

                        xpToReward = (currentTier + 1) * currentM;
                        moneyReward = xpToReward * 5;
                        if (currentTier * currentM == 1) {
                            rewardXp(player, 2);
                            PlayerBalance.savePlayerData(player, 10);
                        }

                        double furnaceMultiplier = FurnanceMultiplierDatas.getPlayerData(player);
                        rewardXp(player, xpToReward * furnaceMultiplier);
                        PlayerBalance.savePlayerData(player, moneyReward * furnaceMultiplier);

                        int chance2 = random.nextInt(150);
                        if (chance2 == 0) {
                            PlayerBalance.savePlayerData(player, moneyReward * 10);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &aYou just found &l$" + ServerConfig.formatAmount(moneyReward * 10)));
                        }
                        rewardGiven = true;
                    }

                    double playerLevel = PlayerLevel.getPlayerData(player.getName());
                    double currentXp = CurrentXp.getPlayerData(player);
                    int xpNeeded = (int) (100 + (playerLevel - 1) * 10);
                    double xpPercentage = (currentXp / xpNeeded) * 100;
                    double furnaceMultiplier = FurnanceMultiplierDatas.getPlayerData(player);
                    double currentGems = SaveGemsDatas.getPlayerData(player.getName());
                    Random random = new Random();
                    int gemsLuck = random.nextInt(250);

                    if (gemsLuck == 0) {
                        int chanceGems = random.nextInt(10);
                        SaveGemsDatas.savePlayerDataAsync(player, currentGems + chanceGems);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerConfig.getServerName() + " → &aYou just found &2&l" + String.valueOf(chanceGems) + " gems !"));

                        String title = org.bukkit.ChatColor.translateAlternateColorCodes('&', "&aYou just found &2&l" + String.valueOf(chanceGems) + " gems !");
                        player.sendTitle(title, "", 10, 70, 20); // 10 ticks fade-in, 70 ticks stay, 20 ticks fade-out
                    }

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    sendActionBar(player, ChatColor.translateAlternateColorCodes('&', "&bLevel &l[" + ServerConfig.formatAmount(playerLevel) + "] &r&b(" + String.format("%.2f", xpPercentage) + "%) &f&l| &a&l(+$" + ServerConfig.formatAmount(moneyReward * furnaceMultiplier) + ")"));
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }



    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    private void rewardXp(Player player, double amountXp) {
        double getPlayerCurrentXp = CurrentXp.getPlayerData(player);
        int playerLevel = PlayerLevel.getPlayerData(player.getName());
        int xpNeeded = 100 + (playerLevel - 1) * 10;

        synchronized (this) {
            double newXp = getPlayerCurrentXp + amountXp;

            while (newXp >= xpNeeded) {
                newXp -= xpNeeded;
                playerLevel++;
                xpNeeded = 100 + (playerLevel - 1) * 10;
                PlayerLevel.savePlayerDataAsync(player, playerLevel);
            }
            CurrentXp.savePlayerDataAsync(player, newXp);
        }
    }

    public void simulateBlockBreak(Player player, Location location) {
        Block block = location.getBlock();
        if (block.getType() != Material.BEDROCK) {
            player.sendBlockChange(location, Material.AIR.createBlockData());
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendBlockChange(location, Material.BEDROCK.createBlockData());
                    moveGrassBlock(player, location.clone().add(new Vector(0, 0, -1)), 21, 12);
                    long actual = DefaultSpeedRespawn.getPlayerData(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            double currentBlock = SaveActualBlockData.getPlayerData(player.getName());
                            Material[] blockList = ServerConfig.getBlockList();
                            player.sendBlockChange(location, blockList[(int) (currentBlock % blockList.length)].createBlockData());
                        }
                    }.runTaskLater(plugin, actual);
                }
            }.runTaskLater(this.plugin, 2L);
        }
    }
}
