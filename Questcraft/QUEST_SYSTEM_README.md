# Questcraft - Quest System Documentation

A comprehensive quest system for Minecraft Bukkit/Purpur servers with support for multiple quest types, daily quests, a credit-based economy, and an in-game shop.

## Features

- **Multiple Quest Types**: Kill, Collect, Mine, Enchant, Plant, Reach Location, and Daily quests
- **Player Progress Tracking**: Track individual player progress on active quests
- **Daily Quest System**: Automatically assigns 5 random daily quests to each player every 24 hours
- **Credit System**: Players earn credits by completing quests and can spend them in the shop
- **In-Game Shop**: Purchase building materials with credits
- **Event-Driven**: Automatically updates quest progress on relevant in-game events
- **Command Interface**: Full command suite for quest management
- **Player List Integration**: Shows player credits in the tab menu

## Quest Types

### 1. KillQuest
Players must kill a specific number of entities.

Example:
```java
KillQuest quest = new KillQuest(
    "kill_zombies_1",
    "Zombie Slayer",
    "Defeat 10 zombies",
    "ZOMBIE",
    10,
    100, // XP reward
    50   // Credits reward
);
```

### 2. CollectQuest
Players must collect a specific number of items (by dropping them).

Example:
```java
CollectQuest quest = new CollectQuest(
    "collect_diamonds_1",
    "Diamond Collector",
    "Collect 10 diamonds",
    "DIAMOND",
    10,
    200,
    100
);
```

### 3. MineQuest
Players must mine a specific number of ore/block types.

Example:
```java
MineQuest quest = new MineQuest(
    "mine_gold_1",
    "Gold Miner",
    "Mine 20 gold ores",
    "GOLD_ORE",
    20,
    150,
    75
);
```

### 4. EnchantQuest
Players must enchant items at an enchantment table.

Example:
```java
EnchantQuest quest = new EnchantQuest(
    "enchant_items_1",
    "Enchanter",
    "Enchant items",
    "PROTECTION",
    5,
    100,
    50
);
```

### 5. PlantQuest
Players must place specific blocks (trees, flowers, etc).

Example:
```java
PlantQuest quest = new PlantQuest(
    "plant_flowers_1",
    "Florist",
    "Plant 20 flowers",
    "OAK_SAPLING",
    20,
    100,
    50
);
```

### 6. ObtainQuest
Players must pick up/obtain specific items.

Example:
```java
ObtainQuest quest = new ObtainQuest(
    "obtain_obsidian_1",
    "Obsidian Collector",
    "Obtain 10 obsidian blocks",
    "OBSIDIAN",
    10,
    150,
    75
);
```

### 7. ReachLocationQuest
Players must reach a specific location within a radius.

Example:
```java
ReachLocationQuest quest = new ReachLocationQuest(
    "reach_spawn_1",
    "Return to Spawn",
    "Return to the server spawn",
    spawnLocation,
    5, // radius in blocks
    50,
    25
);
```

### 8. DailyQuest
A quest that resets daily for players. Automatically assigned to players.

## Daily Quest System

Every 24 hours (real-life day), players receive 5 randomly selected daily quests. The system:
- Automatically assigns new quests at midnight
- Checks every 5 minutes if a player needs new daily quests
- Sends notifications when new daily quests are assigned
- Allows players to view their assigned daily quests

**Pre-configured Daily Quests:**
- Daily: Kill 10 Mobs
- Daily: Zombie Slayer (Kill 5 zombies)
- Daily: Gold Miner (Mine 20 gold ores)
- Daily: Stone Collector (Mine 32 stone blocks)
- Daily: Enchanter (Enchant 1 book)
- Daily: Florist (Plant 20 flowers)
- Daily: Obsidian Collector (Obtain 10 obsidian blocks)

## Credits System

Players earn credits by completing quests. Credits can be spent in the quest shop to purchase building materials. The credit system includes:
- Automatic credit awards on quest completion
- Credit balance tracking per player
- Credits displayed in the player list (tab menu)

## Shop System

Players can access the quest shop using `/quest shop` command, which opens an inventory GUI where they can purchase building materials with credits.

### Available Shop Items
- **Concrete Blocks**: White, Red, Blue, Green, Yellow (10 credits per stack)
- **Wool Blocks**: White, Red, Blue, Black (8 credits per stack)
- **Wood Logs**: Oak, Birch, Spruce, Dark Oak (5-6 credits per stack)
- **Stone Variants**: Stone, Cobblestone, Stone Brick, Mossy Stone Brick (6-9 credits per stack)
- **Bricks**: Brick Block, Nether Brick (10-12 credits per stack)
- **Lighting Blocks**: Glowstone, Lantern, Sea Lantern (20-30 credits per stack)
- **Glass**: Glass, Tinted Glass (7-8 credits per stack)
- **Leaves**: Oak, Birch, Spruce (4 credits per stack)

## Commands

### /quest help
Shows all available quest commands and their usage.

### /quest list
Displays active quests and completed quests for the player.

### /quest start <questId>
Start a new quest. Players cannot start quests they've already completed.

### /quest progress <questId>
Shows current progress on a specific quest.

### /quest abandon <questId>
Abandon an active quest (resets progress).

### /quest complete <questId>
Manually complete a quest (mostly for testing/admin use).

### /quest day
Review assigned daily quests.

### /quest credits
Check current credit balance.

### /quest shop
Open the quest shop inventory GUI.

## Programmatic Usage

### Register a Quest
```java
Quest myQuest = new KillQuest("my_quest", "My Quest", "Description", "ZOMBIE", 5, 100, 50);
QuestManager.getInstance().registerQuest(myQuest);
```

### Register a Daily Quest
```java
Quest dailyQuest = new KillQuest("daily_quest", "Daily Quest", "Description", "ZOMBIE", 5, 50, 25);
DailyQuestManager.getInstance().registerDailyQuest(dailyQuest);
```

### Start a Quest for a Player
```java
UUID playerId = player.getUniqueId();
QuestManager.getInstance().startQuestForPlayer(playerId, "quest_id");
```

### Update Quest Progress
```java
UUID playerId = player.getUniqueId();
QuestManager.getInstance().updateQuestProgress(playerId, "quest_id", 1);
```

### Get Player's Credits
```java
long credits = CreditManager.getInstance().getCredits(playerId);
```

### Add Credits
```java
CreditManager.getInstance().addCredits(playerId, 50);
```

### Remove Credits
```java
CreditManager.getInstance().removeCredits(playerId, 50);
```

### Check Player's Active Quests
```java
UUID playerId = player.getUniqueId();
Set<Quest> activeQuests = QuestManager.getInstance().getPlayerActiveQuests(playerId);
```

### Check Player's Daily Quests
```java
Set<Quest> dailyQuests = DailyQuestManager.getInstance().getPlayerDailyQuests(playerId);
```

## Architecture

```
quests/
  ├── Quest.java (abstract base class)
  ├── KillQuest.java
  ├── CollectQuest.java
  ├── MineQuest.java
  ├── EnchantQuest.java
  ├── PlantQuest.java
  ├── ObtainQuest.java
  └── ReachLocationQuest.java

player/
  ├── PlayerQuestData.java (tracks active/completed quests)
  └── PlayerCreditData.java (tracks player credits)

shop/
  ├── ShopItem.java (purchasable item data)
  └── ShopManager.java (manages all shop items)

managers/
  ├── QuestManager.java (singleton managing all quests)
  ├── DailyQuestManager.java (manages daily quests)
  ├── CreditManager.java (manages player credits)
  └── PluginManager.java (initializes plugin components)

listeners/
  ├── QuestJoinListener.java (player join/daily quest setup)
  ├── QuestListener.java (quest progress events)
  ├── ShopListener.java (shop inventory click events)
  └── PlayerListListener.java (player list header updates)

commands/
  ├── QuestCommand.java (main quest command handler)
  └── ShopCommand.java (shop command handler)
```

## Data Storage

Currently, quest and credit data is stored in-memory using `QuestManager` and `CreditManager`. For production use, consider implementing:
- Database persistence (MySQL, MongoDB)
- File-based storage (YAML, JSON)
- Save/load on player join/leave

## Configuration

Daily quests can be customized in `PluginManager.initializeDailyQuests()` method.
Shop items can be customized in `ShopManager.initializeShop()` method.

## Future Enhancements

- [ ] Database persistence
- [ ] Quest chains (complete Quest A to unlock Quest B)
- [ ] Repeatable quests with cooldowns
- [ ] Quest branching (multiple objectives)
- [ ] NPC quest givers
- [ ] GUI for quest selection
- [ ] Permission-based access to quests
- [ ] Custom quest rewards
- [ ] Leaderboards

