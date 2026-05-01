package com.quest.questcraft.player;

import java.util.*;

/**
 * Manages player credits and other account data
 */
public class PlayerCreditData {
    private final UUID playerId;
    private long credits;

    public PlayerCreditData(UUID playerId) {
        this.playerId = playerId;
        this.credits = 0;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public long getCredits() {
        return credits;
    }

    /**
     * Set credits directly (used when loading from file)
     */
    public void setCredits(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot set negative credits");
        }
        this.credits = amount;
    }

    public void addCredits(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative credits");
        }
        this.credits += amount;
    }

    public void removeCredits(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot remove negative credits");
        }
        if (this.credits < amount) {
            throw new IllegalArgumentException("Insufficient credits");
        }
        this.credits -= amount;
    }

    public boolean hasEnoughCredits(long amount) {
        return this.credits >= amount;
    }

    public void resetCredits() {
        this.credits = 0;
    }

    @Override
    public String toString() {
        return String.format("Credits: %d", credits);
    }
}
