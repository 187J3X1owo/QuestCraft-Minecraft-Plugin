package com.quest.questcraft.player;

import com.quest.questcraft.quests.Quest;
import java.util.*;

/**
 * Tracks quest-related data for a player
 */
public class PlayerQuestData {
    private final UUID playerId;
    private final Map<String, Integer> questProgress; // questId -> current progress
    private final Set<String> completedQuests;
    private final Set<String> activeQuests; // Currently active quest IDs
    private final Map<String, Set<String>> questCountedItems; // questId -> set of counted item hashes

    public PlayerQuestData(UUID playerId) {
        this.playerId = playerId;
        this.questProgress = new HashMap<>();
        this.completedQuests = new HashSet<>();
        this.activeQuests = new HashSet<>();
        this.questCountedItems = new HashMap<>();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Start a new quest for the player
     */
    public void startQuest(String questId) {
        activeQuests.add(questId);
        questProgress.put(questId, 0);
    }

    /**
     * Update progress on an active quest
     */
    public void updateProgress(String questId, int amount) {
        if (activeQuests.contains(questId)) {
            int current = questProgress.getOrDefault(questId, 0);
            questProgress.put(questId, current + amount);
        }
    }

    /**
     * Set progress to a specific value
     */
    public void setProgress(String questId, int progress) {
        if (activeQuests.contains(questId)) {
            questProgress.put(questId, progress);
        }
    }

    /**
     * Get current progress on a quest
     */
    public int getProgress(String questId) {
        return questProgress.getOrDefault(questId, 0);
    }

    /**
     * Complete a quest
     */
    public void completeQuest(String questId) {
        activeQuests.remove(questId);
        completedQuests.add(questId);
        questProgress.remove(questId);
        clearCountedItems(questId);
    }

    /**
     * Abandon a quest
     */
    public void abandonQuest(String questId) {
        activeQuests.remove(questId);
        questProgress.remove(questId);
        clearCountedItems(questId);
    }

    /**
     * Check if player has completed a quest
     */
    public boolean hasCompletedQuest(String questId) {
        return completedQuests.contains(questId);
    }

    /**
     * Check if player has an active quest
     */
    public boolean hasActiveQuest(String questId) {
        return activeQuests.contains(questId);
    }

    /**
     * Get all active quests
     */
    public Set<String> getActiveQuests() {
        return new HashSet<>(activeQuests);
    }

    /**
     * Get all completed quests
     */
    public Set<String> getCompletedQuests() {
        return new HashSet<>(completedQuests);
    }

    /**
     * Reset all quest data
     */
    public void resetAllData() {
        questProgress.clear();
        completedQuests.clear();
        activeQuests.clear();
    }

    /**
     * Check if an item has already been counted for a specific quest
     */
    public boolean hasCountedItem(String questId, String itemHash) {
        Set<String> countedItems = questCountedItems.getOrDefault(questId, new HashSet<>());
        return countedItems.contains(itemHash);
    }

    /**
     * Mark an item as counted for a specific quest
     */
    public void markItemCounted(String questId, String itemHash) {
        Set<String> countedItems = questCountedItems.computeIfAbsent(questId, k -> new HashSet<>());
        countedItems.add(itemHash);
    }

    /**
     * Clear counted items when quest is abandoned or completed
     */
    public void clearCountedItems(String questId) {
        questCountedItems.remove(questId);
    }
}
