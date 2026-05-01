package com.quest.questcraft.quests;

/**
 * Quest type for enchanting items
 */
public class EnchantQuest extends Quest {
    private final String enchantmentType; // e.g., "PROTECTION", "SHARPNESS"
    private final int amount; // Number of items to enchant

    public EnchantQuest(String questId, String name, String description, String enchantmentType, 
                        int amount, int rewardXp, int rewardCredits) {
        super(questId, name, description, amount, rewardXp, rewardCredits);
        this.enchantmentType = enchantmentType;
        this.amount = amount;
    }

    public String getEnchantmentType() {
        return enchantmentType;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String getQuestType() {
        return "ENCHANT";
    }

    @Override
    public String getObjective() {
        return String.format("Enchant %d item(s) with %s", amount, enchantmentType);
    }
}
