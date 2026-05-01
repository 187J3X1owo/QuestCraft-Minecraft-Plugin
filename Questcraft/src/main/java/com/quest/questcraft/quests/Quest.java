package com.quest.questcraft.quests;

import java.util.*;

/**
 * Abstract base class for all quest types
 */
public abstract class Quest {
    private final String questId;
    private final String name;
    private final String description;
    private final int rewardXp;
    private final int rewardCredits;
    private final List<String> rewardItems;
    private final int maxProgress;

    public Quest(String questId, String name, String description, int maxProgress, 
                 int rewardXp, int rewardCredits) {
        this.questId = questId;
        this.name = name;
        this.description = description;
        this.maxProgress = maxProgress;
        this.rewardXp = rewardXp;
        this.rewardCredits = rewardCredits;
        this.rewardItems = new ArrayList<>();
    }

    public String getQuestId() {
        return questId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getRewardXp() {
        return rewardXp;
    }

    public int getRewardCredits() {
        return rewardCredits;
    }

    public List<String> getRewardItems() {
        return new ArrayList<>(rewardItems);
    }

    public void addRewardItem(String item) {
        rewardItems.add(item);
    }

    /**
     * Get the type of quest (e.g., "KILL", "COLLECT", "REACH")
     */
    public abstract String getQuestType();

    /**
     * Get a description of what the player needs to do
     */
    public abstract String getObjective();

    @Override
    public String toString() {
        return String.format("Quest[id=%s, name=%s, type=%s]", questId, name, getQuestType());
    }
}
