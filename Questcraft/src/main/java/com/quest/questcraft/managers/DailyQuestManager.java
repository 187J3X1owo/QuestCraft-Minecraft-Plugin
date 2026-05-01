package com.quest.questcraft.managers;

import com.quest.questcraft.quests.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.quest.questcraft.utils.Utils;
import java.util.*;

/**
 * Manages daily quests assignment and rotation
 */
public class DailyQuestManager {
    private static DailyQuestManager instance;
    private final List<Quest> dailyQuestPool;
    private final Map<UUID, Set<String>> playerDailyQuests; // playerId -> assigned daily quest IDs
    private final Map<UUID, Long> lastDailyResetTime; // playerId -> last reset timestamp
    private long serverStartTime;

    private DailyQuestManager() {
        this.dailyQuestPool = new ArrayList<>();
        this.playerDailyQuests = new HashMap<>();
        this.lastDailyResetTime = new HashMap<>();
        this.serverStartTime = System.currentTimeMillis();
    }

    public static DailyQuestManager getInstance() {
        if (instance == null) {
            instance = new DailyQuestManager();
        }
        return instance;
    }

    /**
     * Register a daily quest
     */
    public void registerDailyQuest(Quest quest) {
        dailyQuestPool.add(quest);
    }

    /**
     * Get all daily quests
     */
    public List<Quest> getDailyQuestPool() {
        return new ArrayList<>(dailyQuestPool);
    }

    /**
     * Assign daily quests to a player
     */
    public void assignDailyQuestsToPlayer(UUID playerId) {
        Set<String> assignedQuests = new HashSet<>();
        Random random = new Random();
        QuestManager qm = QuestManager.getInstance();

        // Randomly select 5 daily quests
        if (dailyQuestPool.size() >= 5) {
            List<Quest> pool = new ArrayList<>(dailyQuestPool);
            Collections.shuffle(pool);
            for (int i = 0; i < 5; i++) {
                Quest quest = pool.get(i);
                assignedQuests.add(quest.getQuestId());
                qm.startQuestForPlayer(playerId, quest.getQuestId());
            }
        } else {
            // If less than 5 quests, add all
            for (Quest quest : dailyQuestPool) {
                assignedQuests.add(quest.getQuestId());
                qm.startQuestForPlayer(playerId, quest.getQuestId());
            }
        }

        playerDailyQuests.put(playerId, assignedQuests);
        lastDailyResetTime.put(playerId, System.currentTimeMillis());
    }

    /**
     * Check if a player's daily quests need to be reset
     */
    public boolean shouldResetDailyQuests(UUID playerId) {
        Long lastReset = lastDailyResetTime.get(playerId);
        if (lastReset == null) {
            return true;
        }
        long currentTime = System.currentTimeMillis();
        long millisecondsPerDay = 24 * 60 * 60 * 1000L;
        return (currentTime - lastReset) >= millisecondsPerDay;
    }

    /**
     * Reset daily quests for a player
     */
    public void resetDailyQuestsForPlayer(UUID playerId) {
        QuestManager qm = QuestManager.getInstance();
        Set<String> currentQuests = playerDailyQuests.getOrDefault(playerId, new HashSet<>());

        // Abandon current daily quests
        for (String questId : currentQuests) {
            qm.abandonQuest(playerId, questId);
        }

        // Assign new daily quests
        assignDailyQuestsToPlayer(playerId);
    }

    /**
     * Get a player's assigned daily quests
     */
    public Set<Quest> getPlayerDailyQuests(UUID playerId) {
        Set<String> questIds = playerDailyQuests.getOrDefault(playerId, new HashSet<>());
        Set<Quest> quests = new HashSet<>();
        QuestManager qm = QuestManager.getInstance();

        for (String questId : questIds) {
            Quest quest = qm.getQuest(questId);
            if (quest != null) {
                quests.add(quest);
            }
        }

        return quests;
    }

    /**
     * Notify player about their daily quests
     */

    public void notifyPlayerDailyQuests(Player player) {
        UUID playerId = player.getUniqueId();
        Set<Quest> dailyQuests = getPlayerDailyQuests(playerId);

        if (dailyQuests.isEmpty()) {
            return;
        }

        player.sendMessage(Utils.colorize("<gold>=========== Daily Quests ==========="));
        player.sendMessage(Utils.colorize("<yellow>You have 5 daily quests to complete!"));

        int index = 1;
        for (Quest quest : dailyQuests) {
            player.sendMessage(Utils.colorize(String.format(
                "<white>%d. <aqua>%s <gray>- %s",
                index,
                quest.getName(),
                quest.getObjective()
            )));
            index++;
        }
        player.sendMessage(Utils.colorize("<gold>=================================="));
        player.sendMessage(Utils.colorize("<gray>Use /quest day to review your daily quests"));
        
    }
}
