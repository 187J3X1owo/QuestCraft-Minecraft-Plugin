package com.quest.questcraft.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import com.quest.questcraft.managers.QuestManager;
import com.quest.questcraft.managers.CreditManager;
import com.quest.questcraft.managers.DailyQuestManager;

/**
 * Deprecated: Use QuestJoinListener instead
 */
@Deprecated
public class PlayerListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // This is now handled by QuestJoinListener
    }
}