/* Decompiler 61ms, total 224ms, lines 342 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL.listener;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.ServerConfig;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.CurrentXp;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SeedBreakListener implements Listener {
    private static final long SELL_INTERVAL = 200L;
    private final Plugin plugin;
    private final Map<Player, Integer> wheatCount = new HashMap();
    private final Map<Player, Integer> carrotCount = new HashMap();
    private final Map<Player, Integer> melonCount = new HashMap();
    private final Map<Player, Integer> wartCount = new HashMap();
    private final String serverName = ServerConfig.getServerName();
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public SeedBreakListener(Plugin plugin) {
        this.plugin = plugin;
        this.startSellTask();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        int tier = (int) TiersDatas.getPlayerData(player.getName());
        if (block.getType() == Material.WHEAT) {
            if (tier < 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must be &6&ltier 1 &r&cfor break &e&lwheat !"));
                return;
            }
            this.handleWheatBreak(event, block, player, tool);
        } else {
            if (block.getType() == Material.CARROTS) {
                tier = (int) TiersDatas.getPlayerData(player.getName());
                if (tier < 5) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must be &6&ltier 5 &r&cfor break &6&lcarrot !"));
                    return;
                }

                this.handleCarrotBreak(event, block, player, tool);
            }  else if (block.getType() == Material.POTATOES) {
                if (tier < 10) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must be &a&ltier 10 &r&cfor break &e&lpotatoes !"));
                    return;
                }

                this.handlePotatoeBreak(event, block, player, tool);
            }
            else if (block.getType() == Material.BEETROOTS) {
                if (tier < 25) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must be &a&ltier 25 &r&cfor break &e&lbetroots !"));
                    return;
                }

                this.handleBeetRootBreak(event, block, player, tool);
            }
        }

    }

    private void handlePotatoeBreak(BlockBreakEvent event, final Block block, Player player, ItemStack tool) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.sendMessage("Impossible to break melons in creative!");
            event.setCancelled(true);
        } else if (!this.isHoe(tool.getType())) {
            if (this.isNotWorldMap(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must use a hoe to break this!"));
            }
        } else {
            if (this.isHoe(tool.getType())) {
                if (!this.isNotWorldMap(player)) return;

                block.setType(Material.AIR);
                this.incrementMelonCount(player);
                (new BukkitRunnable() {
                    public void run() {block.setType(Material.POTATOES);
                    }
                }).runTaskLater(this.plugin, 20L);
            }

        }
    }

    private void handleBeetRootBreak(BlockBreakEvent event, final Block block, Player player, ItemStack tool) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.sendMessage("Impossible to break warts in creative!");
            event.setCancelled(true);
        } else if (!this.isHoe(tool.getType())) {
            if (this.isNotWorldMap(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must use a hoe to break this!"));
            }
        } else {
            if (this.isHoe(tool.getType())) {
                if (!this.isNotWorldMap(player)) return;

                block.setType(Material.AIR);
                this.incrementWartCount(player);
                (new BukkitRunnable() {
                    public void run() {block.setType(Material.BEETROOT_SEEDS);
                    }
                }).runTaskLater(this.plugin, 20L);
            }

        }
    }

    private void handleWheatBreak(BlockBreakEvent event, final Block block, Player player, ItemStack tool) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.sendMessage("Impossible to break wheat in creative!");
            event.setCancelled(true);
        } else if (!this.isHoe(tool.getType())) {
            if (this.isNotWorldMap(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must use a hoe to break this!"));
            }
        } else {
            if (this.isHoe(tool.getType())) {
                if (!this.isNotWorldMap(player)) return;

                block.setType(Material.AIR);
                this.incrementWheatCount(player);
                (new BukkitRunnable() {public void run() {block.setType(Material.WHEAT);}}).runTaskLater(this.plugin, 20L);
            }

        }
    }

    private void handleCarrotBreak(BlockBreakEvent event, final Block block, Player player, ItemStack tool) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.sendMessage("Impossible to break carrots in creative!");
            event.setCancelled(true);
        } else if (!this.isHoe(tool.getType())) {
            if (this.isNotWorldMap(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &cYou must use a hoe to break this!"));
            }
        } else {
            if (this.isHoe(tool.getType())) {
                if (!this.isNotWorldMap(player)) {
                    return;
                }

                block.setType(Material.AIR);
                this.incrementCarrotCount(player);
                (new BukkitRunnable() {
                    public void run() {
                        block.setType(Material.CARROTS);
                    }
                }).runTaskLater(this.plugin, 20L);
            }

        }
    }

    private void incrementWheatCount(Player player) {
        this.wheatCount.put(player, (Integer)this.wheatCount.getOrDefault(player, 0) + 1);
    }

    private void incrementMelonCount(Player player) {
        this.melonCount.put(player, (Integer)this.melonCount.getOrDefault(player, 0) + 1);
    }

    private void incrementCarrotCount(Player player) {
        this.carrotCount.put(player, (Integer)this.carrotCount.getOrDefault(player, 0) + 1);
    }

    private void incrementWartCount(Player player) {
        this.wartCount.put(player, (Integer)this.wartCount.getOrDefault(player, 0) + 1);
    }

    private void startSellTask() {
        (new BukkitRunnable() {
            public void run() {
                String var10001;
                Iterator var2;
                String var10002;
                Entry entry;
                Player player;
                int count;
                double playerMultiply;
                double amountToGive;
                double xpToGive;
                String actionBarMessage;
                synchronized(SeedBreakListener.this.wheatCount) {
                    var2 = SeedBreakListener.this.wheatCount.entrySet().iterator();

                    while(var2.hasNext()) {
                        entry = (Entry)var2.next();
                        player = (Player)entry.getKey();
                        count = (Integer)entry.getValue();
                        if (count > 0) {
                            playerMultiply = SaveMultiplyDatas.getPlayerData(player.getName());
                            amountToGive = (double)count * 0.2D * playerMultiply;
                            xpToGive = (double)count * 0.15D;
                            var10001 = SeedBreakListener.this.decimalFormat.format(amountToGive);
                            actionBarMessage = ChatColor.translateAlternateColorCodes('&', "&eYou get &l$" + var10001 + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &lwheat");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBarMessage));
                            var10002 = SeedBreakListener.this.serverName;
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', var10002 + " → &eYou get &l$" + SeedBreakListener.this.decimalFormat.format(amountToGive) + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &lwheat"));
                            PlayerBalance.savePlayerData(player, amountToGive);
                            SeedBreakListener.this.rewardXp(player, xpToGive);
                            SeedBreakListener.this.wheatCount.put(player, 0);
                        }
                    }
                }

                synchronized(SeedBreakListener.this.carrotCount) {
                    var2 = SeedBreakListener.this.carrotCount.entrySet().iterator();

                    while(var2.hasNext()) {
                        entry = (Entry)var2.next();
                        player = (Player)entry.getKey();
                        count = (Integer)entry.getValue();
                        if (count > 0) {
                            playerMultiply = SaveMultiplyDatas.getPlayerData(player.getName());
                            amountToGive = (double)count * 0.3D * playerMultiply;
                            xpToGive = (double)count * 0.2D;
                            var10001 = SeedBreakListener.this.decimalFormat.format(amountToGive);
                            actionBarMessage = ChatColor.translateAlternateColorCodes('&', "&eYou get &l$" + var10001 + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &6&lcarrots");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBarMessage));
                            var10002 = SeedBreakListener.this.serverName;
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', var10002 + " → &eYou get &l$" + SeedBreakListener.this.decimalFormat.format(amountToGive) + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &6&lcarrots"));
                            PlayerBalance.savePlayerData(player, amountToGive);
                            SeedBreakListener.this.rewardXp(player, xpToGive);
                            SeedBreakListener.this.carrotCount.put(player, 0);
                        }
                    }
                }

                synchronized(SeedBreakListener.this.wartCount) {
                    var2 = SeedBreakListener.this.wartCount.entrySet().iterator();

                    while(true) {
                        if (!var2.hasNext()) {
                            break;
                        }

                        entry = (Entry)var2.next();
                        player = (Player)entry.getKey();
                        count = (Integer)entry.getValue();
                        if (count > 0) {
                            playerMultiply = SaveMultiplyDatas.getPlayerData(player.getName());
                            amountToGive = (double)count * 0.5D * playerMultiply;
                            xpToGive = (double)count * 0.5D;
                            var10001 = SeedBreakListener.this.decimalFormat.format(amountToGive);
                            actionBarMessage = ChatColor.translateAlternateColorCodes('&', "&eYou get &l$" + var10001 + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &c&lwart");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBarMessage));
                            var10002 = SeedBreakListener.this.serverName;
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', var10002 + " → &eYou get &l$" + SeedBreakListener.this.decimalFormat.format(amountToGive) + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &c&lwart"));
                            PlayerBalance.savePlayerData(player, amountToGive);
                            SeedBreakListener.this.rewardXp(player, xpToGive);
                            SeedBreakListener.this.wartCount.put(player, 0);
                        }
                    }
                }

                synchronized(SeedBreakListener.this.melonCount) {
                    var2 = SeedBreakListener.this.melonCount.entrySet().iterator();

                    while(var2.hasNext()) {
                        entry = (Entry)var2.next();
                        player = (Player)entry.getKey();
                        count = (Integer)entry.getValue();
                        if (count > 0) {
                            playerMultiply = SaveMultiplyDatas.getPlayerData(player.getName());
                            amountToGive = (double)count * 0.45D * playerMultiply;
                            xpToGive = (double)count * 0.45D;
                            var10001 = SeedBreakListener.this.decimalFormat.format(amountToGive);
                            actionBarMessage = ChatColor.translateAlternateColorCodes('&', "&eYou get &l$" + var10001 + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &a&lmelons");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBarMessage));
                            var10002 = SeedBreakListener.this.serverName;
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', var10002 + " → &eYou get &l$" + SeedBreakListener.this.decimalFormat.format(amountToGive) + " &r&eand &b&l" + SeedBreakListener.this.decimalFormat.format(xpToGive) + " XP &r&efrom selling &a&lmelons"));
                            PlayerBalance.savePlayerData(player, amountToGive);
                            SeedBreakListener.this.rewardXp(player, xpToGive);
                            SeedBreakListener.this.melonCount.put(player, 0);
                        }
                    }

                }
            }
        }).runTaskTimer(this.plugin, 0L, 200L);
    }

    private void rewardXp(Player player, double amountXp) {
        double getPlayerCurrentXp = CurrentXp.getPlayerData(player);
        int playerLevel = PlayerLevel.getPlayerData(player.getName());
        int xpNeeded = 100 + (playerLevel - 1) * 10;
        synchronized(this) {
            double newXp = getPlayerCurrentXp + amountXp;
            if (newXp >= (double)xpNeeded) {
                int newLevel = playerLevel + 1;
                newXp = 0.0D;
                PlayerLevel.savePlayerDataAsync(player, newLevel);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.serverName + " → &b&lYou just upgraded to level " + newLevel + "!"));
            }

            CurrentXp.savePlayerDataAsync(player, (double)((int)newXp));
        }
    }

    private boolean isHoe(Material material) {
        return material == Material.WOODEN_HOE || material == Material.STONE_HOE || material == Material.IRON_HOE || material == Material.GOLDEN_HOE || material == Material.DIAMOND_HOE;
    }

    private boolean isNotWorldMap(Player player) {
        return player.getWorld().getName().equalsIgnoreCase("world");
    }
}
