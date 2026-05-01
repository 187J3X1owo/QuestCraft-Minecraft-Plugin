package com.quest.questcraft.managers;

import com.quest.questcraft.quests.Quest;
import com.quest.questcraft.player.PlayerQuestData;
import java.util.*;

/**
 * Manages all quests and player quest data
 */
public class QuestManager {
    private final Map<String, Quest> quests; // questId -> Quest
    private final Map<UUID, PlayerQuestData> playerData; // playerId -> PlayerQuestData
    private static QuestManager instance;

    private QuestManager() {
        this.quests = new HashMap<>();
        this.playerData = new HashMap<>();
    }

    public static QuestManager getInstance() {
        if (instance == null) {
            instance = new QuestManager();
        }
        return instance;
    }

    /**
     * Register a quest
     */
    public void registerQuest(Quest quest) {
        quests.put(quest.getQuestId(), quest);
    }

    /**
     * Get a quest by ID
     */
    public Quest getQuest(String questId) {
        return quests.get(questId);
    }

    /**
     * Get all registered quests
     */
    public Collection<Quest> getAllQuests() {
        return new ArrayList<>(quests.values());
    }

    /**
     * Get player quest data, creating it if it doesn't exist
     */
    public PlayerQuestData getPlayerData(UUID playerId) {
        return playerData.computeIfAbsent(playerId, PlayerQuestData::new);
    }

    /**
     * Start a quest for a player
     */
    public void startQuestForPlayer(UUID playerId, String questId) {
        Quest quest = getQuest(questId);
        if (quest == null) {
            throw new IllegalArgumentException("Quest not found: " + questId);
        }

        PlayerQuestData data = getPlayerData(playerId);
        if (!data.hasCompletedQuest(questId)) {
            data.startQuest(questId);
        }
    }

    /**
     * Get a player's active quests
     */
    public Set<Quest> getPlayerActiveQuests(UUID playerId) {
        PlayerQuestData data = getPlayerData(playerId);
        Set<Quest> activeQuests = new HashSet<>();

        for (String questId : data.getActiveQuests()) {
            Quest quest = getQuest(questId);
            if (quest != null) {
                activeQuests.add(quest);
            }
        }

        return activeQuests;
    }

    /**
     * Get a player's completed quests
     */
    public Set<Quest> getPlayerCompletedQuests(UUID playerId) {
        PlayerQuestData data = getPlayerData(playerId);
        Set<Quest> completedQuests = new HashSet<>();

        for (String questId : data.getCompletedQuests()) {
            Quest quest = getQuest(questId);
            if (quest != null) {
                completedQuests.add(quest);
            }
        }

        return completedQuests;
    }

    /**
     * Update quest progress for a player
     */
    public void updateQuestProgress(UUID playerId, String questId, int amount) {
        getPlayerData(playerId).updateProgress(questId, amount);
    }

    /**
     * Complete a quest for a player
     */
    public void completeQuest(UUID playerId, String questId) {
        getPlayerData(playerId).completeQuest(questId);
    }

    /**
     * Abandon a quest for a player
     */
    public void abandonQuest(UUID playerId, String questId) {
        getPlayerData(playerId).abandonQuest(questId);
    }

    /**
     * Check if player has completed a quest
     */
    public boolean hasPlayerCompletedQuest(UUID playerId, String questId) {
        return getPlayerData(playerId).hasCompletedQuest(questId);
    }

    /**
     * Reset a player's quest data
     */
    public void resetPlayerData(UUID playerId) {
        playerData.remove(playerId);
    }
}
