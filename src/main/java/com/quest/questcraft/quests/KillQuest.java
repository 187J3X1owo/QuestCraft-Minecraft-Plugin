package com.quest.questcraft.quests;

/**
 * Quest type for killing specific entities
 */
public class KillQuest extends Quest {
    private final String entityType; // e.g., "ZOMBIE", "CREEPER", "ENDERMAN"
    private final int killCount;

    public KillQuest(String questId, String name, String description, String entityType, 
                     int killCount, int rewardXp, int rewardCredits) {
        super(questId, name, description, killCount, rewardXp, rewardCredits);
        this.entityType = entityType;
        this.killCount = killCount;
    }

    public String getEntityType() {
        return entityType;
    }

    public int getKillCount() {
        return killCount;
    }

    @Override
    public String getQuestType() {
        return "KILL";
    }

    @Override
    public String getObjective() {
        return String.format("Kill %d %s(s)", killCount, entityType);
    }
}
