package com.quest.questcraft.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.Bukkit;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import com.quest.questcraft.managers.QuestManager;
import com.quest.questcraft.managers.CreditManager;
import com.quest.questcraft.player.PlayerQuestData;
import com.quest.questcraft.quests.*;

/**
 * Listens to game events and updates quest progress
 */
public class QuestListener implements Listener {

    private static NamespacedKey PLAYER_PLACED_KEY = null;
    private static final long PLACED_ITEM_COOLDOWN = 1000; // 1 second

    /*
     * Get or initialize the NamespacedKey for tracking player-placed items
     */
    private static NamespacedKey getPlayerPlacedKey() {
        if (PLAYER_PLACED_KEY == null) {
            org.bukkit.plugin.Plugin plugin = Bukkit.getPluginManager().getPlugin("Questcraft");
            if (plugin == null) {
                throw new IllegalStateException("Plugin 'Questcraft' not found");
            }
            PLAYER_PLACED_KEY = new NamespacedKey(plugin, "player_placed");
        }
        return PLAYER_PLACED_KEY;
    }

    /**
     * Handle entity death for kill quests
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player killer = ((LivingEntity) entity).getKiller();

        if (killer == null) {
            return;
        }

        PlayerQuestData data = QuestManager.getInstance().getPlayerData(killer.getUniqueId());
        String entityType = entity.getType().toString();

        for (String questId : data.getActiveQuests()) {
            Quest quest = QuestManager.getInstance().getQuest(questId);
            if (quest instanceof KillQuest) {
                KillQuest killQuest = (KillQuest) quest;
                if (killQuest.getEntityType().equalsIgnoreCase(entityType)) {
                    int progress = data.getProgress(questId);
                    int newProgress = progress + 1;
                    data.setProgress(questId, newProgress);

                    if (newProgress >= killQuest.getKillCount()) {
                        QuestManager.getInstance().completeQuest(killer.getUniqueId(), questId);
                        CreditManager.getInstance().addCredits(killer.getUniqueId(), quest.getRewardCredits());
                        PlayerListListener.updatePlayerListHeader(killer);
                        triggerQuestCompletionEffects(killer, quest);
                        killer.sendMessage("§a✓ Quest completed: " + quest.getName());
                        killer.sendMessage("§6Reward: " + quest.getRewardCredits() + " Credits");
                    } else {
                        playProgressSound(killer);
                        killer.sendMessage("§6[" + quest.getName() + "] Progress: " + newProgress + "/" + killQuest.getKillCount());
                    }
                }
            }
        }
    }

    /**
     * Handle player movement for reach location quests
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerQuestData data = QuestManager.getInstance().getPlayerData(player.getUniqueId());

        for (String questId : data.getActiveQuests()) {
            Quest quest = QuestManager.getInstance().getQuest(questId);
            if (quest instanceof ReachLocationQuest) {
                ReachLocationQuest reachQuest = (ReachLocationQuest) quest;
                double distance = player.getLocation().distance(reachQuest.getTargetLocation());

                if (distance <= reachQuest.getRadius()) {
                    QuestManager.getInstance().completeQuest(player.getUniqueId(), questId);
                    CreditManager.getInstance().addCredits(player.getUniqueId(), quest.getRewardCredits());
                    PlayerListListener.updatePlayerListHeader(player);
                    triggerQuestCompletionEffects(player, quest);
                    player.sendMessage("§a✓ Quest completed: " + quest.getName());
                    player.sendMessage("§6Reward: " + quest.getRewardCredits() + " Credits");
                }
            }
        }
    }

    /**
     * Handle block break for mine quests
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        PlayerQuestData data = QuestManager.getInstance().getPlayerData(player.getUniqueId());
        
        // Create a unique hash for this block location
        String blockHash = event.getBlock().getWorld().getName() + ":" + 
                          event.getBlock().getX() + ":" + 
                          event.getBlock().getY() + ":" + 
                          event.getBlock().getZ();

        for (String questId : data.getActiveQuests()) {
            Quest quest = QuestManager.getInstance().getQuest(questId);
            if (quest instanceof MineQuest) {
                MineQuest mineQuest = (MineQuest) quest;
                if (mineQuest.getBlockType().equalsIgnoreCase(blockType.toString())) {
                    // Check if this block location has already been counted for this quest
                    if (data.hasCountedItem(questId, blockHash)) {
                        return; // Don't count recollected blocks
                    }
                    
                    int progress = data.getProgress(questId);
                    int newProgress = progress + 1;
                    data.setProgress(questId, newProgress);
                    data.markItemCounted(questId, blockHash);

                    if (newProgress >= mineQuest.getAmount()) {
                        QuestManager.getInstance().completeQuest(player.getUniqueId(), questId);
                        CreditManager.getInstance().addCredits(player.getUniqueId(), quest.getRewardCredits());
                        PlayerListListener.updatePlayerListHeader(player);
                        triggerQuestCompletionEffects(player, quest);
                        player.sendMessage("§a✓ Quest completed: " + quest.getName());
                        player.sendMessage("§6Reward: " + quest.getRewardCredits() + " Credits");
                    } else {
                        playProgressSound(player);
                        player.sendMessage("§6[" + quest.getName() + "] Progress: " + newProgress + "/" + mineQuest.getAmount());
                    }
                }
            }
        }
    }

    /**
     * Handle block place for plant quests
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        PlayerQuestData data = QuestManager.getInstance().getPlayerData(player.getUniqueId());
        
        // Create a unique hash for this block location
        String blockHash = event.getBlock().getWorld().getName() + ":" + 
                          event.getBlock().getX() + ":" + 
                          event.getBlock().getY() + ":" + 
                          event.getBlock().getZ();

        for (String questId : data.getActiveQuests()) {
            Quest quest = QuestManager.getInstance().getQuest(questId);
            if (quest instanceof PlantQuest) {
                PlantQuest plantQuest = (PlantQuest) quest;
                if (plantQuest.getBlockType().equalsIgnoreCase(blockType.toString())) {
                    // Check if this block location has already been counted for this quest
                    if (data.hasCountedItem(questId, blockHash)) {
                        return; // Don't count recollected blocks
                    }
                    
                    int progress = data.getProgress(questId);
                    int newProgress = progress + 1;
                    data.setProgress(questId, newProgress);
                    data.markItemCounted(questId, blockHash);

                    if (newProgress >= plantQuest.getAmount()) {
                        QuestManager.getInstance().completeQuest(player.getUniqueId(), questId);
                        CreditManager.getInstance().addCredits(player.getUniqueId(), quest.getRewardCredits());
                        PlayerListListener.updatePlayerListHeader(player);
                        triggerQuestCompletionEffects(player, quest);
                        player.sendMessage("§a✓ Quest completed: " + quest.getName());
                        player.sendMessage("§6Reward: " + quest.getRewardCredits() + " Credits");
                    } else {
                        playProgressSound(player);
                        player.sendMessage("§6[" + quest.getName() + "] Progress: " + newProgress + "/" + plantQuest.getAmount());
                    }
                }
            }
        }
    }

    /**
     * Handle item pickup for collect/obtain quests
     */
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();
        Material itemType = item.getItemStack().getType();
        int amount = item.getItemStack().getAmount();
        PlayerQuestData data = QuestManager.getInstance().getPlayerData(player.getUniqueId());
        
        // Create a unique hash for this item based on type and location (to prevent duplicates)
        String itemHash = itemType.toString() + "_" + 
                         item.getLocation().getWorld().getName() + "_" +
                         item.getLocation().getBlockX() + "_" + 
                         item.getLocation().getBlockY() + "_" + 
                         item.getLocation().getBlockZ();

        // Check if this item was placed by a player (ignore if true for collect quests)
        boolean isPlayerPlaced = item.getPersistentDataContainer().has(getPlayerPlacedKey(), PersistentDataType.LONG);
        if (isPlayerPlaced) {
            long placedTime = item.getPersistentDataContainer().get(getPlayerPlacedKey(), PersistentDataType.LONG);
            long currentTime = System.currentTimeMillis();
            // Only ignore if it was placed recently (within cooldown)
            if (currentTime - placedTime < PLACED_ITEM_COOLDOWN) {
                return;
            }
        }

        for (String questId : data.getActiveQuests()) {
            Quest quest = QuestManager.getInstance().getQuest(questId);

            // Handle collect quests
            if (quest instanceof CollectQuest) {
                CollectQuest collectQuest = (CollectQuest) quest;
                if (collectQuest.getItemType().equalsIgnoreCase(itemType.toString())) {
                    // Check if this item has already been counted for this quest
                    if (data.hasCountedItem(questId, itemHash)) {
                        continue; // Don't count recollected items
                    }
                    
                    int progress = data.getProgress(questId);
                    int newProgress = progress + amount;
                    data.setProgress(questId, newProgress);
                    data.markItemCounted(questId, itemHash);

                    if (newProgress >= collectQuest.getAmount()) {
                        QuestManager.getInstance().completeQuest(player.getUniqueId(), questId);
                        CreditManager.getInstance().addCredits(player.getUniqueId(), quest.getRewardCredits());
                        PlayerListListener.updatePlayerListHeader(player);
                        triggerQuestCompletionEffects(player, quest);
                        player.sendMessage("§a✓ Quest completed: " + quest.getName());
                        player.sendMessage("§6Reward: " + quest.getRewardCredits() + " Credits");
                    } else {
                        player.sendMessage("§6[" + quest.getName() + "] Progress: " + newProgress + "/" + collectQuest.getAmount());
                    }
                }
            }

            // Handle obtain quests
            if (quest instanceof ObtainQuest) {
                ObtainQuest obtainQuest = (ObtainQuest) quest;
                if (obtainQuest.getItemType().equalsIgnoreCase(itemType.toString())) {
                    // Check if this item has already been counted for this quest
                    if (data.hasCountedItem(questId, itemHash)) {
                        continue; // Don't count recollected items
                    }
                    
                    int progress = data.getProgress(questId);
                    int newProgress = progress + amount;
                    data.setProgress(questId, newProgress);
                    data.markItemCounted(questId, itemHash);

                    if (newProgress >= obtainQuest.getAmount()) {
                        QuestManager.getInstance().completeQuest(player.getUniqueId(), questId);
                        CreditManager.getInstance().addCredits(player.getUniqueId(), quest.getRewardCredits());
                        PlayerListListener.updatePlayerListHeader(player);
                        triggerQuestCompletionEffects(player, quest);
                        player.sendMessage("§a✓ Quest completed: " + quest.getName());
                        player.sendMessage("§6Reward: " + quest.getRewardCredits() + " Credits");
                    } else {
                        player.sendMessage("§6[" + quest.getName() + "] Progress: " + newProgress + "/" + obtainQuest.getAmount());
                    }
                }
            }
        }
    }

    /**
     * Mark items when they spawn (for tracking recently placed items)
     */
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Item) {
            Item item = (Item) event.getEntity();
            // Mark the item with current timestamp so we can check if it's recently spawned
            item.getPersistentDataContainer().set(getPlayerPlacedKey(), PersistentDataType.LONG, System.currentTimeMillis());
        }
    }

    /**
     * Trigger firework and sound effects when quest is completed
     */
    private void triggerQuestCompletionEffects(Player player, Quest quest) {
        // Play Bell sound effect (using BLOCK_NOTE_BLOCK_CHIME for bell sound)
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.0f);
        
        // Play additional celebratory sound effect
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.5f);
        
        // Spawn excessive firework particles spreading from player entity
        spawnCelebrationParticles(player);
    }
    
    /**
     * Spawn excessive firework particles spreading from player without damage
     */
    private void spawnCelebrationParticles(Player player) {
        // Spawn particles in multiple directions (no damage)
        for (int i = 0; i < 20; i++) {
            // Random direction
            double angle = Math.random() * 2 * Math.PI;
            double verticalAngle = Math.random() * Math.PI;
            
            double vx = Math.sin(verticalAngle) * Math.cos(angle) * 2;
            double vy = Math.cos(verticalAngle) * 2 + 1;
            double vz = Math.sin(verticalAngle) * Math.sin(angle) * 2;
            
            // Spawn particles at player location with velocity
            player.getWorld().spawnParticle(
                Particle.FIREWORK,
                player.getLocation().add(0, 1, 0),
                1,
                vx, vy, vz,
                0.5
            );
        }
    }
    
    /**
     * Play progress sound when player makes quest progress
     */
    private void playProgressSound(Player player) {
        // Play Bell (Chime) jukebox note for progress update
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.7f, 1.2f);
    }

    /**
     * Handle enchant item for enchant quests
     */
    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        Player player = (Player) event.getEnchanter();
        PlayerQuestData data = QuestManager.getInstance().getPlayerData(player.getUniqueId());

        for (String questId : data.getActiveQuests()) {
            Quest quest = QuestManager.getInstance().getQuest(questId);
            if (quest instanceof EnchantQuest) {
                EnchantQuest enchantQuest = (EnchantQuest) quest;
                // Progress on any enchantment (can be modified to check specific enchantments)
                int progress = data.getProgress(questId);
                int newProgress = progress + 1;
                data.setProgress(questId, newProgress);

                if (newProgress >= enchantQuest.getAmount()) {
                    QuestManager.getInstance().completeQuest(player.getUniqueId(), questId);
                    CreditManager.getInstance().addCredits(player.getUniqueId(), quest.getRewardCredits());
                    player.sendMessage("§a✓ Quest completed: " + quest.getName());
                    player.sendMessage("§6Reward: " + quest.getRewardCredits() + " Credits");
                } else {
                    player.sendMessage("§6Progress: " + newProgress + "/" + enchantQuest.getAmount());
                }
            }
        }
    }
}
