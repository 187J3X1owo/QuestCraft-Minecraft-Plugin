package com.quest.questcraft.quests;

/**
 * Quest type for planting blocks
 */
public class PlantQuest extends Quest {
    private final String blockType; // e.g., "OAK_SAPLING", "ROSE_BUSH"
    private final int amount;

    public PlantQuest(String questId, String name, String description, String blockType, 
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
        return "PLANT";
    }

    @Override
    public String getObjective() {
        return String.format("Plant %d %s(s)", amount, blockType);
    }
}
