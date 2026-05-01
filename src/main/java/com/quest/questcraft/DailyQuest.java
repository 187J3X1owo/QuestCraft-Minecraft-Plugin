package com.quest.questcraft;

import com.quest.questcraft.quests.Quest;

/**
 * Represents a quest that resets daily
 */
public class DailyQuest extends Quest {
    private final long dateCreatedMillis;
    private final String questCategory;

    public DailyQuest(String questId, String name, String description, int maxProgress, 
                      int rewardXp, int rewardCredits, String questCategory) {
        super(questId, name, description, maxProgress, rewardXp, rewardCredits);
        this.dateCreatedMillis = System.currentTimeMillis();
        this.questCategory = questCategory;
    }

    public long getDateCreatedMillis() {
        return dateCreatedMillis;
    }

    public String getQuestCategory() {
        return questCategory;
    }

    /**
     * Check if the quest has expired (more than 24 hours old)
     */
    public boolean isExpired() {
        long currentTimeMillis = System.currentTimeMillis();
        long millisecondsPerDay = 24 * 60 * 60 * 1000L;
        return (currentTimeMillis - dateCreatedMillis) >= millisecondsPerDay;
    }

    @Override
    public String getQuestType() {
        return "DAILY";
    }

    @Override
    public String getObjective() {
        return String.format("Complete this daily quest (Category: %s)", questCategory);
    }
}
