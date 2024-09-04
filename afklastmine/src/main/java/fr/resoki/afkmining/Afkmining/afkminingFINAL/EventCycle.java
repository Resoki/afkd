/* Decompiler 100ms, total 249ms, lines 218 */
package fr.resoki.afkmining.Afkmining.afkminingFINAL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class EventCycle {
   private final JavaPlugin plugin;
   private BossBar bossBar;
   private String currentEvent;
   private long eventDurationSeconds;
   private long startTimeMillis;
   private BukkitRunnable eventTask;
   private BukkitRunnable timerTask;
   private List<String> previousEvents;

   String serverName = ServerConfig.getServerName();

   public EventCycle(JavaPlugin plugin) {
      this.plugin = plugin;
      this.bossBar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID, new BarFlag[0]);
      this.previousEvents = new ArrayList();
   }

   public void startEventCycle() {
      this.eventTask = new BukkitRunnable() {
         public void run() {
            EventCycle.this.triggerRandomEvent();
         }
      };
      this.eventTask.runTaskTimer(this.plugin, 0L, 3600L);
      Bukkit.getLogger().info("Event cycle started.");
   }

   public void stopEventCycle() {
      if (this.eventTask != null) {
         this.eventTask.cancel();
      }

      if (this.timerTask != null) {
         this.timerTask.cancel();
      }

      Bukkit.getLogger().info("Event cycle stopped.");
   }

   private void triggerRandomEvent() {
      Random random = new Random();
      String nextEvent = null;

      do {
         int eventIndex = random.nextInt(2);
         switch(eventIndex) {
         case 0:
            nextEvent = ChatColor.translateAlternateColorCodes('&', "&b&lXps Boost");
            this.eventDurationSeconds = 180L;
            break;
         case 1:
            nextEvent = ChatColor.translateAlternateColorCodes('&', "&a&lMoney Boost");
            this.eventDurationSeconds = 180L;
            this.bossBar.setColor(BarColor.GREEN);
            break;
         }
      } while(nextEvent != null && this.previousEvents.contains(nextEvent));

      if (nextEvent != null) {
         this.previousEvents.add(nextEvent);
         if (this.previousEvents.size() > 1) this.previousEvents.remove(0);
      }

      this.currentEvent = nextEvent;
      this.startTimeMillis = System.currentTimeMillis();
      this.startTimerTask();
      this.updateBossBar();

      this.bossBar.setColor(BarColor.BLUE);
       String dashedLine = ChatColor.BLUE + "---------------------------";
      String chatgameTitle = ChatColor.translateAlternateColorCodes('&', "         &e&l⭐    EVENT    ⭐");
      String chatgameMessage = ChatColor.translateAlternateColorCodes('&',  nextEvent+ " &r&bevent just started ! ");
      String formattedMessage = dashedLine + "\n" + chatgameTitle + "\n" + chatgameMessage + "\n" + dashedLine;

      Bukkit.broadcastMessage(formattedMessage);
   }

   private void updateBossBar() {
      long elapsedMillis = System.currentTimeMillis() - this.startTimeMillis;
      long remainingMillis = this.eventDurationSeconds * 1000L - elapsedMillis;
      if (remainingMillis <= 0L) {
         remainingMillis = 0L;
      }

      String var10002;
      if (Objects.equals(this.currentEvent, ChatColor.translateAlternateColorCodes('&', "&b&lXps Boost"))) {
         var10002 = this.currentEvent;
         this.bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', "&e&l★ " + var10002 + " - (" + this.formatTime(remainingMillis) + ") &e&l★"));
      }

      if (Objects.equals(this.currentEvent, ChatColor.translateAlternateColorCodes('&', "&a&lMoney Boost"))) {
         var10002 = this.currentEvent;
         this.bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', "&e&l$ " + var10002 + " - (" + this.formatTime(remainingMillis) + ") &e&l$"));
      }

      if (Objects.equals(this.currentEvent, ChatColor.translateAlternateColorCodes('&', "&c&lStrength Boost"))) {
         var10002 = this.currentEvent;
         this.bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', "&e&l★ " + var10002 + " - (" + this.formatTime(remainingMillis) + ") &e&l★"));
      }

      if (Objects.equals(this.currentEvent, ChatColor.translateAlternateColorCodes('&', "&e&lTokens Boost"))) {
         var10002 = this.currentEvent;
         this.bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', "&e&l★ " + var10002 + " - (" + this.formatTime(remainingMillis) + ") &e&l★"));
      }

      this.bossBar.setProgress((double)remainingMillis / (double)(this.eventDurationSeconds * 1000L));
      Iterator var5 = Bukkit.getOnlinePlayers().iterator();

      while(var5.hasNext()) {
         Player player = (Player)var5.next();
         this.bossBar.addPlayer(player);
      }

   }

   private void startTimerTask() {
      if (this.timerTask != null) this.timerTask.cancel();

      this.timerTask = new BukkitRunnable() {
         public void run() {
            EventCycle.this.updateBossBar();
            long elapsedMillis = System.currentTimeMillis() - EventCycle.this.startTimeMillis;
            if (elapsedMillis >= EventCycle.this.eventDurationSeconds * 1000L) {
               this.cancel();
               EventCycle.this.resetEvent();
               Bukkit.getLogger().info("Timer task ended: " + EventCycle.this.currentEvent);
            }

         }
      };
      this.timerTask.runTaskTimer(this.plugin, 0L, 20L);
      Bukkit.getLogger().info("Timer task started.");
   }

   private void resetEvent() {
      this.bossBar.setTitle("");
      this.bossBar.setProgress(0.0D);
      if (this.currentEvent != null && this.currentEvent.contains("Strength")) {
         this.removeStrengthEffectFromPlayers();
      }

      this.currentEvent = null;
      Bukkit.getLogger().info("Event reset.");
   }

   private String formatTime(long milliseconds) {
      long seconds = milliseconds / 1000L;
      long minutes = seconds / 60L;
      seconds %= 60L;
      return String.format("%02d:%02d", minutes, seconds);
   }

   public boolean isEventActive() {
      return this.currentEvent != null;
   }

   public String getCurrentEvent() {
      return this.currentEvent;
   }

   public BossBar getBossBar() {
      return this.bossBar;
   }

   private void applyStrengthEffectToPlayers() {
      Iterator var1 = Bukkit.getOnlinePlayers().iterator();

      while(var1.hasNext()) {
         Player player = (Player)var1.next();
         player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int)this.eventDurationSeconds * 20, 1, true, false));
      }

      Bukkit.getLogger().info("Applied Strength effect to players.");
   }

   public void applyStrengthEffectToPlayer(Player player) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int)this.eventDurationSeconds * 20, 0));
   }

   private void removeStrengthEffectFromPlayers() {
      Iterator var1 = Bukkit.getOnlinePlayers().iterator();

      while(var1.hasNext()) {
         Player player = (Player)var1.next();
         player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
      }

      Bukkit.getLogger().info("Removed Strength effect from players.");
   }
}
