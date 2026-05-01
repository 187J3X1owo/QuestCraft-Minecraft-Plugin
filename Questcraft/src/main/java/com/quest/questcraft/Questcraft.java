package com.quest.questcraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import com.quest.questcraft.managers.PluginManager;
import com.quest.questcraft.managers.DailyQuestManager;
import com.quest.questcraft.managers.CreditManager;
import com.quest.questcraft.listeners.*;
import com.quest.questcraft.commands.QuestCommand;

public class Questcraft extends JavaPlugin {
    private BukkitTask dailyResetTask;
    
    @Override
    public void onEnable() {
        
        // Initialize managers
        PluginManager.getInstance().initialize();
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new QuestJoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuestListener(), this);
        getServer().getPluginManager().registerEvents(new ShopListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        
        // Register commands
        getCommand("quest").setExecutor(new QuestCommand());
        
        // Schedule daily quest reset task (runs every 20 minutes to check)
        scheduleDailyQuestReset();
        
        getLogger().info("Questcraft has been enabled!");
    }

    @Override
    public void onDisable() {
        // Cancel scheduled tasks
        if (dailyResetTask != null) {
            dailyResetTask.cancel();
        }
        
        // Save all player credits before shutting down
        CreditManager.getInstance().saveAllPlayerCredits();
        
        getLogger().info("Questcraft has been disabled!");
    }
    
    /**
     * Schedule a task to check and reset daily quests for all players
     */
    private void scheduleDailyQuestReset() {
        dailyResetTask = Bukkit.getScheduler().runTaskTimer(this, () -> {
            DailyQuestManager dqm = DailyQuestManager.getInstance();
            
            // Check each online player
            for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
                if (dqm.shouldResetDailyQuests(player.getUniqueId())) {
                    dqm.resetDailyQuestsForPlayer(player.getUniqueId());
                    dqm.notifyPlayerDailyQuests(player);
                    player.sendMessage("§e[Quest System] §aNew daily quests available! Type /quest day to view them.");
                }
            }
            
            // Update player list header for all players
            PlayerListListener.updateAllPlayersHeader();
        }, 0L, 6000L); // Run every 5 minutes (6000 ticks = 5 minutes)
    }
}