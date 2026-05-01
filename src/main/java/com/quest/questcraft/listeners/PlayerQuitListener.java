package com.quest.questcraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import com.quest.questcraft.managers.CreditManager;

/*
 Saves player data when they leave the server
 */
public class PlayerQuitListener implements Listener {
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Save credits to file before player leaves
        CreditManager.getInstance().savePlayerCredits(player.getUniqueId());
        
        // Remove from memory to free up space
        CreditManager.getInstance().removePlayerData(player.getUniqueId());
    }
}
