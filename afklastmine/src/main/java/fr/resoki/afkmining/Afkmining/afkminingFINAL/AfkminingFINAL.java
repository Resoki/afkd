package fr.resoki.afkmining.Afkmining.afkminingFINAL;

import fr.resoki.afkmining.Afkmining.afkminingFINAL.command.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.command.Rebirth.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.*;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.CurrentXp;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Level.PlayerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthPoints;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Rebirth.SaveRebirthsDatas;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.datas.Worker.WorkerLevel;
import fr.resoki.afkmining.Afkmining.afkminingFINAL.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public final class AfkminingFINAL extends JavaPlugin {
    private Object timerTaskId;
    private ChatWordListener chatWordListener;
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().enablePlugin(this);

        // Listener
        this.getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
        this.getServer().getPluginManager().registerEvents(new RightClickBlock(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new RebirthListener(), this);
        this.getServer().getPluginManager().registerEvents(new DisplayLevelListener(), this);
        this.getServer().getPluginManager().registerEvents(new UpgradeBlockListener(), this);
        this.getServer().getPluginManager().registerEvents(new FurnaceMenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new TiersMenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new SeedBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new IronBlockListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WorkerListener(), this);
        this.getServer().getPluginManager().registerEvents(new DyeListener(this), this);
        this.getServer().getPluginManager().registerEvents(new CompassListener(this), this);
        this.getServer().getPluginManager().registerEvents(new DyeProtectionListener(this), this);
        this.getServer().getPluginManager().registerEvents(new HelpMenuListener(), this);
        this.chatWordListener = new ChatWordListener(this);
        this.getServer().getPluginManager().registerEvents(this.chatWordListener, this);

        //Datas
        SaveMultiplyDatas.initialize(this.getDataFolder() + File.separator + "players-multiply.yml", this);
        SaveActualBlockData.initialize(this.getDataFolder() + File.separator + "players-current_block.yml", this);
        PlayerLevel.initialize(this.getDataFolder() + File.separator + "players-level.yml", this);
        CurrentXp.initialize(this.getDataFolder() + File.separator + "players-currentxp.yml", this);
        DefaultSpeedRespawn.initialize(this.getDataFolder() + File.separator + "players-defaultspeed.yml", this);
        SaveRebirthsDatas.initialize(this.getDataFolder() + File.separator + "players-rebirths.yml", this);
        SaveRebirthPoints.initialize(this.getDataFolder() + File.separator + "players-rebirths-points.yml", this);
        FurnanceMultiplierDatas.initialize(this.getDataFolder() + File.separator + "players-furnance-multi.yml", this);
        PlayerAlreadyClaimedDaily.initialize(this.getDataFolder() + File.separator + "players-daily-claimed.yml", this);
        SaveGemsDatas.initialize(this.getDataFolder() + File.separator + "players-gems.yml", this);
        TiersDatas.initialize(this.getDataFolder() + File.separator + "players-tiers.yml", this);
        WorkerLevel.initialize(this.getDataFolder() + File.separator + "players-worker-level.yml", this);
        TiersDatas.initialize(this.getDataFolder() + File.separator + "players-tiers.yml", this);

        PlayerBalance.setupEconomy();
        MyPlaceholderExpansion myExpansion = new MyPlaceholderExpansion();
        myExpansion.register();

        getCommand("ping").setExecutor(new PingCommand());
        getCommand("rebirth").setExecutor(new RebirthCommand());
        getCommand("rebirthmax").setExecutor(new RebirthMaxCommand());
        getCommand("givemultiplier").setExecutor(new GiveMultiplyCommand());
        getCommand("upgrade").setExecutor(new UpgradeCommand());
        getCommand("rebirthpoints").setExecutor(new RebirthPointsCommand());
        getCommand("rebirthshop").setExecutor(new RebirthShopCommand());
        getCommand("daily").setExecutor(new DailyCommand());
        getCommand("tiers").setExecutor(new TiersCommand());
        getCommand("multiplytop").setExecutor(new MultiplyTopCommand());
        getCommand("leveltop").setExecutor(new LevelTopCommand());
        getCommand("factory").setExecutor(new FactoryCommand(this));
        getCommand("worker").setExecutor(new WorkerCommand());
        getCommand("hide").setExecutor(new HideCommand(this));
        getCommand("autorebirth").setExecutor(new AutoRebirthCommand(new RebirthListener(), this));

        //Chat Game Task
        startChatGameTask();

        (new BukkitRunnable() {
            public void run() {
                if (AfkminingFINAL.this.isMidnightNow()) PlayerAlreadyClaimedDaily.resetForAll();
            }
        }).runTaskTimer(this, 0L, 10L);
    }

    private boolean isMidnightNow() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.get(11) == 0 && calendar.get(12) == 0;
    }

    @Override
    public void onDisable() {
        stopTimerTask();
    }

    private void stopTimerTask() {
        Bukkit.getScheduler().cancelTask((Integer)this.timerTaskId);
    }

    private String[] generateRandomCalculation() {
        Random random = new Random();
        int num1 = random.nextInt(50) + 1;
        int num2 = random.nextInt(50) + 1;
        String calculation = num1 + " + " + num2;
        int result = num1 + num2;
        return new String[]{calculation, String.valueOf(result)};
    }

    private String generateRandomWord() {
        String[] minecraftWords = new String[]{
                "Bedrock", "Conveyor", "Rebirth", "Skillpoints", "Factory", "Grind", "Drown", "Trend", "Dash",
                "Inflation", "Tier", "Mine", "Parkour", "Island", "Brick", "Path", "Villager", "Artifacts",
                "Keys", "Chips", "Chatgame", "Math", "Auto", "Npc", "Quartz", "Furnace", "Money", "Experience",
                "Reward", "Milestones", "Invisible", "Statistics", "Leaderboard", "Roof", "Players", "Owner",
                "Jump", "Staff", "Enchants", "Farm", "Upgrade", "Points", "Blocks", "Multiplier", "Numbers",
                "Gems", "Luck", "Wheat", "Ore", "Stone", "Worker", "Gems", "Calcul", "Path", "Afk", "Crate", "Helper", "Load",
                "Parkour", "Quartz"
        };
        Random random = new Random();
        return minecraftWords[random.nextInt(minecraftWords.length)];
    }

    private void startChatGameTask() {
        Random rand = new Random();
        this.timerTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            int n = rand.nextInt(100);
            String dashedLine;
            String chatgameMessage;
            String formattedMessage;
            if (n < 50) {
                String currentWord = this.generateRandomWord();
                this.chatWordListener.setCurrentWord(currentWord);
                dashedLine = ChatColor.GRAY + "---------------------------";
                String chatgameTitle = ChatColor.translateAlternateColorCodes('&', "         &e&l⭐    CHATGAME    ⭐");
                chatgameMessage = ChatColor.translateAlternateColorCodes('&', "  &eFirst to type &6&l" + currentWord + " &r&ewin x0.1 or more multiply!");
                formattedMessage = dashedLine + "\n" + chatgameTitle + "\n" + chatgameMessage + "\n" + dashedLine;
                Bukkit.broadcastMessage(formattedMessage);
            } else {
                String[] calculationAndResult = this.generateRandomCalculation();
                dashedLine = calculationAndResult[0];
                int currentResult = Integer.parseInt(calculationAndResult[1]);
                this.chatWordListener.setCurrentCalculation(dashedLine, currentResult);
                chatgameMessage = ChatColor.AQUA + "---------------------------";
                formattedMessage = ChatColor.translateAlternateColorCodes('&', "         &b&l⭐    CHATGAME    ⭐");
                String chatgameMessagex = ChatColor.translateAlternateColorCodes('&', "  &bFirst to solve &l" + dashedLine + " &r&bwin x0.1 or more multiply!");
                String formattedMessagex = chatgameMessage + "\n" + formattedMessage + "\n" + chatgameMessagex + "\n" + chatgameMessage;
                Bukkit.broadcastMessage(formattedMessagex);
            }

            this.getServer().getPluginManager().registerEvents(new ChatWordListener(this), this);
        }, 0L, 4800L);
    }

}
