package com.quest.questcraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import com.quest.questcraft.managers.QuestManager;
import com.quest.questcraft.managers.CreditManager;
import com.quest.questcraft.managers.DailyQuestManager;

/**
 * Handles player join events for quest system
 */
public class QuestJoinListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Load credit data from file first
        CreditManager.getInstance().loadPlayerCredits(player.getUniqueId());
        
        // Initialize quest data for player
        QuestManager.getInstance().getPlayerData(player.getUniqueId());
        
        // Check if player needs daily quests
        DailyQuestManager dqm = DailyQuestManager.getInstance();
        if (dqm.shouldResetDailyQuests(player.getUniqueId())) {
            dqm.resetDailyQuestsForPlayer(player.getUniqueId());
            //dqm.notifyPlayerDailyQuests(player);
        }
        
        player.sendMessage("§6Welcome to Questcraft! Type /quest help for more information.");
        
        // Send active quest list to player
        DailyQuestManager.getInstance().notifyPlayerDailyQuests(player);
    }
    
    /**
     * Send the list of active quests to the player in chat
     */
    /*
    private void sendQuestListInChat(Player player) {
        player.sendMessage("§e========== Your Active Quests ==========");
        
        java.util.Set<com.quest.questcraft.quests.Quest> activeQuests = 
            QuestManager.getInstance().getPlayerActiveQuests(player.getUniqueId());
        
        if (activeQuests.isEmpty()) {
            player.sendMessage("§7You have no active quests. Type /quest to view available quests.");
        } else {
            for (com.quest.questcraft.quests.Quest quest : activeQuests) {
                int progress = QuestManager.getInstance().getPlayerData(player.getUniqueId()).getProgress(quest.getQuestId());
                player.sendMessage("§6• " + quest.getName() + " §7- Progress: §a" + progress);
            }
        }
        
        player.sendMessage("§e========================================");
    }
    */
}
