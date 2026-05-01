package com.quest.questcraft.quests;

/**
 * Quest type for mining specific ore/blocks
 */
public class MineQuest extends Quest {
    private final String blockType; // e.g., "GOLD_ORE", "IRON_ORE", "DIAMOND_ORE"
    private final int amount;

    public MineQuest(String questId, String name, String description, String blockType, 
                     int amount, int rewardXp, int rewardCredits) {
        super(questId, name, description, amount, rewardXp, rewardCredits);
        this.blockType = blockType;
        this.amount = amount;
    }

    public String getBlockType() {
        return blockType;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String getQuestType() {
        return "MINE";
    }

    @Override
    public String getObjective() {
        return String.format("Mine %d %s", amount, blockType);
    }
}
