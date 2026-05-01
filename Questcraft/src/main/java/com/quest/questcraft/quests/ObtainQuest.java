package com.quest.questcraft.quests;

/**
 * Quest type for obtaining specific items
 */
public class ObtainQuest extends Quest {
    private final String itemType; // e.g., "OBSIDIAN", "DIAMOND"
    private final int amount;

    public ObtainQuest(String questId, String name, String description, String itemType, 
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
        return "OBTAIN";
    }

    @Override
    public String getObjective() {
        return String.format("Obtain %d %s", amount, itemType);
    }
}
