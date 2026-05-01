package com.quest.questcraft.quests;

/**
 * Quest type for collecting specific items
 */
public class CollectQuest extends Quest {
    private final String itemType; // e.g., "DIAMOND", "IRON_ORE"
    private final int amount;

    public CollectQuest(String questId, String name, String description, String itemType, 
                        int amount, int rewardXp, int rewardCredits) {
        super(questId, name, description, amount, rewardXp, rewardCredits);
        this.itemType = itemType;
        this.amount = amount;
    }

    public String getItemType() {
        return itemType;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String getQuestType() {
        return "COLLECT";
    }

    @Override
    public String getObjective() {
        return String.format("Collect %d %s(s)", amount, itemType);
    }
}
