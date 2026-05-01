package com.quest.questcraft.quests;

import org.bukkit.Location;

/**
 * Quest type for reaching a specific location
 */
public class ReachLocationQuest extends Quest {
    private final Location targetLocation;
    private final int radius;

    public ReachLocationQuest(String questId, String name, String description, 
                            Location targetLocation, int radius, int rewardXp, int rewardCredits) {
        super(questId, name, description, 1, rewardXp, rewardCredits);
        this.targetLocation = targetLocation;
        this.radius = radius;
    }

    public Location getTargetLocation() {
        return targetLocation.clone();
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String getQuestType() {
        return "REACH";
    }

    @Override
    public String getObjective() {
        Location loc = getTargetLocation();
        return String.format("Reach location X: %d, Y: %d, Z: %d", 
                           loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}
