package com.quest.questcraft.managers;

import com.quest.questcraft.player.PlayerCreditData;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Manages player credit data with file persistence
 */
public class CreditManager {
    private final Map<UUID, PlayerCreditData> creditData;
    private static CreditManager instance;
    private static final String CREDITS_FOLDER = "plugins/Questcraft/player_data";

    private CreditManager() {
        this.creditData = new HashMap<>();
        // Create the data folder if it doesn't exist
        try {
            Files.createDirectories(Paths.get(CREDITS_FOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CreditManager getInstance() {
        if (instance == null) {
            instance = new CreditManager();
        }
        return instance;
    }

    /**
     * Get or create credit data for a player
     */
    public PlayerCreditData getPlayerCreditData(UUID playerId) {
        return creditData.computeIfAbsent(playerId, PlayerCreditData::new);
    }

    /**
     * Get a player's credits
     */
    public long getCredits(UUID playerId) {
        return getPlayerCreditData(playerId).getCredits();
    }

    /**
     * Add credits to a player
     */
    public void addCredits(UUID playerId, long amount) {
        getPlayerCreditData(playerId).addCredits(amount);
    }

    /**
     * Remove credits from a player
     */
    public void removeCredits(UUID playerId, long amount) {
        getPlayerCreditData(playerId).removeCredits(amount);
    }

    /**
     * Check if player has enough credits
     */
    public boolean hasEnoughCredits(UUID playerId, long amount) {
        return getPlayerCreditData(playerId).hasEnoughCredits(amount);
    }

    /**
     * Reset credits for a player
     */
    public void resetCredits(UUID playerId) {
        getPlayerCreditData(playerId).resetCredits();
    }

    /**
     * Remove player data from memory (but keep saved file)
     */
    public void removePlayerData(UUID playerId) {
        creditData.remove(playerId);
    }

    /**
     * Load player credits from file
     */
    public void loadPlayerCredits(UUID playerId) {
        try {
            Path filePath = Paths.get(CREDITS_FOLDER, playerId.toString() + ".json");
            
            if (Files.exists(filePath)) {
                String content = new String(Files.readAllBytes(filePath));
                JSONObject json = new JSONObject(content);
                
                long credits = json.optLong("credits", 0);
                PlayerCreditData data = new PlayerCreditData(playerId);
                // Set credits directly through reflection or add a setter
                data.setCredits(credits);
                creditData.put(playerId, data);
            } else {
                // File doesn't exist, create new player data
                creditData.computeIfAbsent(playerId, PlayerCreditData::new);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Fallback to empty data
            creditData.computeIfAbsent(playerId, PlayerCreditData::new);
        }
    }

    /**
     * Save player credits to file
     */
    public void savePlayerCredits(UUID playerId) {
        try {
            PlayerCreditData data = creditData.get(playerId);
            if (data == null) {
                return;
            }
            
            Path filePath = Paths.get(CREDITS_FOLDER, playerId.toString() + ".json");
            JSONObject json = new JSONObject();
            json.put("playerId", playerId.toString());
            json.put("credits", data.getCredits());
            json.put("lastSaved", System.currentTimeMillis());
            
            Files.write(filePath, json.toString(4).getBytes());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save all player credits to files
     */
    public void saveAllPlayerCredits() {
        for (UUID playerId : creditData.keySet()) {
            savePlayerCredits(playerId);
        }
    }
}
