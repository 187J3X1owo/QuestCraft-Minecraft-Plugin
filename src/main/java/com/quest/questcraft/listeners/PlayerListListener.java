package com.quest.questcraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import com.quest.questcraft.managers.CreditManager;

/**
 * Updates player list header to show credits
 */
public class PlayerListListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updatePlayerListHeader(player);
    }
    
    /**
     * Update the player list header to show credits
     */
    public static void updatePlayerListHeader(Player player) {
        long credits = CreditManager.getInstance().getCredits(player.getUniqueId());
        
        Component headerText = Component.text(
            String.format("Questcraft Server - Credits: %d", credits)
        ).color(TextColor.fromHexString("#FFD700"));
        
        Component footerText = Component.text(
            "Complete quests to earn credits"
        ).color(TextColor.fromHexString("#AAAAAA"));
        
        player.sendPlayerListHeader(headerText);
        player.sendPlayerListFooter(footerText);
    }
    
    /**
     * Update header for all online players
     */
    public static void updateAllPlayersHeader() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerListHeader(player);
        }
    }
}
