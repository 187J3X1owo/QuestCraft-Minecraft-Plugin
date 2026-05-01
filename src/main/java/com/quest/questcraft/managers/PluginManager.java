package com.quest.questcraft.managers;

import com.quest.questcraft.quests.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PluginManager {
    private static PluginManager instance;
    
    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }
    
    public void initialize() {
        // Initialize QuestManager with sample quests
        initializeQuests();
        
        // Initialize daily quests
        initializeDailyQuests();
        
        Bukkit.getLogger().info("PluginManager initialized successfully!");
    }

    private void initializeQuests() {
        QuestManager qm = QuestManager.getInstance();
        
        // ===== KILL QUESTS =====
        KillQuest killZombies = new KillQuest(
            "kill_zombies_1",
            "Zombie Slayer",
            "Prove your combat skills by defeating zombies",
            "ZOMBIE",
            10,
            100, // XP reward
            50   // Credits reward
        );
        qm.registerQuest(killZombies);
        
        KillQuest killCreepers = new KillQuest(
            "kill_creepers_1",
            "Creeper Hunter",
            "Hunt down and eliminate creepers",
            "CREEPER",
            5,
            150,
            75
        );
        qm.registerQuest(killCreepers);
        
        KillQuest killSkeletons = new KillQuest(
            "kill_skeletons_1",
            "Skeleton Destroyer",
            "Defeat 12 skeletons to prove your archery defense",
            "SKELETON",
            12,
            120,
            60
        );
        qm.registerQuest(killSkeletons);
        
        KillQuest killSpiders = new KillQuest(
            "kill_spiders_1",
            "Spider Exterminator",
            "Eliminate 8 spiders in combat",
            "SPIDER",
            8,
            110,
            55
        );
        qm.registerQuest(killSpiders);
        
        KillQuest killEndermen = new KillQuest(
            "kill_endermen_1",
            "Enderman Slayer",
            "Defeat 6 endermen - challenging foes await",
            "ENDERMAN",
            6,
            200,
            100
        );
        qm.registerQuest(killEndermen);
        
        KillQuest killWithers = new KillQuest(
            "kill_withers_1",
            "The Wither Bane",
            "Defeat 2 withers - the ultimate challenge",
            "WITHER",
            2,
            500,
            250
        );
        qm.registerQuest(killWithers);
        
        // ===== COLLECT QUESTS =====
        CollectQuest collectDiamonds = new CollectQuest(
            "collect_diamonds_1",
            "Diamond Collector",
            "Gather diamonds to test your mining skills",
            "DIAMOND",
            10,
            200,
            100
        );
        qm.registerQuest(collectDiamonds);
        
        CollectQuest collectEmeralds = new CollectQuest(
            "collect_emeralds_1",
            "Emerald Seeker",
            "Collect 5 emeralds through mining and trading",
            "EMERALD",
            5,
            180,
            90
        );
        qm.registerQuest(collectEmeralds);
        
        CollectQuest collectPearls = new CollectQuest(
            "collect_pearls_1",
            "Pearl Hunter",
            "Collect 12 ender pearls from defeated endermen",
            "ENDER_PEARL",
            12,
            220,
            110
        );
        qm.registerQuest(collectPearls);
        
        // ===== MINE QUESTS =====
        MineQuest mineGold = new MineQuest(
            "mine_gold_1",
            "Gold Miner",
            "Mine 20 gold ores to practice your mining",
            "GOLD_ORE",
            20,
            150,
            75
        );
        qm.registerQuest(mineGold);
        
        MineQuest mineIron = new MineQuest(
            "mine_iron_1",
            "Iron Extractor",
            "Mine 30 iron ore blocks",
            "IRON_ORE",
            30,
            160,
            80
        );
        qm.registerQuest(mineIron);
        
        MineQuest mineDiamond = new MineQuest(
            "mine_diamond_1",
            "Diamond Miner",
            "Mine 8 diamond ore blocks - rare and valuable",
            "DIAMOND_ORE",
            8,
            250,
            125
        );
        qm.registerQuest(mineDiamond);
        
        MineQuest mineEmerald = new MineQuest(
            "mine_emerald_1",
            "Emerald Excavator",
            "Mine 6 emerald ore blocks",
            "EMERALD_ORE",
            6,
            230,
            115
        );
        qm.registerQuest(mineEmerald);
        
        MineQuest mineDeepslate = new MineQuest(
            "mine_deepslate_1",
            "Deep Explorer",
            "Mine 50 deepslate blocks from the deep caves",
            "DEEPSLATE",
            50,
            140,
            70
        );
        qm.registerQuest(mineDeepslate);
        
        // ===== ENCHANT QUESTS =====
        EnchantQuest enchantProtection = new EnchantQuest(
            "enchant_protection_1",
            "Protection Enchanter",
            "Enchant an item with protection spell",
            "PROTECTION",
            1,
            100,
            50
        );
        qm.registerQuest(enchantProtection);
        
        EnchantQuest enchantSharpness = new EnchantQuest(
            "enchant_sharpness_1",
            "Sharpness Expert",
            "Apply sharpness enchantment to 3 weapons",
            "SHARPNESS",
            3,
            180,
            90
        );
        qm.registerQuest(enchantSharpness);
        
        // ===== PLANT QUESTS =====
        PlantQuest plantTrees = new PlantQuest(
            "plant_trees_1",
            "Forester",
            "Plant 15 tree saplings to restore nature",
            "OAK_SAPLING",
            15,
            110,
            55
        );
        qm.registerQuest(plantTrees);
        
        PlantQuest plantFlowers = new PlantQuest(
            "plant_flowers_1",
            "Florist",
            "Plant 25 flowers to beautify the world",
            "POPPY",
            25,
            100,
            50
        );
        qm.registerQuest(plantFlowers);
        
        // ===== OBTAIN QUESTS =====
        ObtainQuest obtainObsidian = new ObtainQuest(
            "obtain_obsidian_1",
            "Obsidian Collector",
            "Obtain 15 obsidian blocks",
            "OBSIDIAN",
            15,
            140,
            70
        );
        qm.registerQuest(obtainObsidian);
        
        ObtainQuest obtainGlowstone = new ObtainQuest(
            "obtain_glowstone_1",
            "Light Bringer",
            "Obtain 10 glowstone blocks for illumination",
            "GLOWSTONE",
            10,
            120,
            60
        );
        qm.registerQuest(obtainGlowstone);
    }

    private void initializeDailyQuests() {
        DailyQuestManager dqm = DailyQuestManager.getInstance();
        QuestManager qm = QuestManager.getInstance();
        
        // ===== KILL QUEST DAILY VARIANTS =====
        KillQuest dailyKill10 = new KillQuest(
            "daily_kill_10_mobs",
            "Daily: Kill 10 Mobs",
            "Defeat any 10 hostile mobs",
            "SPIDER",
            10,
            50,
            20
        );
        dqm.registerDailyQuest(dailyKill10);
        qm.registerQuest(dailyKill10);
        
        KillQuest dailyKillZombies = new KillQuest(
            "daily_kill_zombies",
            "Daily: Zombie Slayer",
            "Kill 5 zombies",
            "ZOMBIE",
            5,
            50,
            25
        );
        dqm.registerDailyQuest(dailyKillZombies);
        qm.registerQuest(dailyKillZombies);
        
        KillQuest dailyKillCreepers = new KillQuest(
            "daily_kill_creepers",
            "Daily: Creeper Hunter",
            "Kill 3 creepers",
            "CREEPER",
            3,
            55,
            30
        );
        dqm.registerDailyQuest(dailyKillCreepers);
        qm.registerQuest(dailyKillCreepers);
        
        KillQuest dailyKillSkeletons = new KillQuest(
            "daily_kill_skeletons",
            "Daily: Skeleton Slayer",
            "Kill 4 skeletons",
            "SKELETON",
            4,
            60,
            35
        );
        dqm.registerDailyQuest(dailyKillSkeletons);
        qm.registerQuest(dailyKillSkeletons);
        
        // ===== MINE QUEST DAILY VARIANTS =====
        MineQuest dailyMineGold = new MineQuest(
            "daily_mine_gold",
            "Daily: Gold Miner",
            "Mine 20 gold ores",
            "GOLD_ORE",
            20,
            50,
            30
        );
        dqm.registerDailyQuest(dailyMineGold);
        qm.registerQuest(dailyMineGold);
        
        MineQuest dailyMineStone = new MineQuest(
            "daily_mine_stone",
            "Daily: Stone Collector",
            "Mine 32 stone blocks",
            "STONE",
            32,
            40,
            20
        );
        dqm.registerDailyQuest(dailyMineStone);
        qm.registerQuest(dailyMineStone);
        
        MineQuest dailyMineIron = new MineQuest(
            "daily_mine_iron",
            "Daily: Iron Extractor",
            "Mine 25 iron ore blocks",
            "IRON_ORE",
            25,
            55,
            32
        );
        dqm.registerDailyQuest(dailyMineIron);
        qm.registerQuest(dailyMineIron);
        
        MineQuest dailyMineCoal = new MineQuest(
            "daily_mine_coal",
            "Daily: Coal Collector",
            "Mine 30 coal ore blocks",
            "COAL_ORE",
            30,
            45,
            22
        );
        dqm.registerDailyQuest(dailyMineCoal);
        qm.registerQuest(dailyMineCoal);
        
        // ===== COLLECT QUEST DAILY VARIANTS =====
        CollectQuest dailyCollectEmeralds = new CollectQuest(
            "daily_collect_emeralds",
            "Daily: Emerald Hunter",
            "Collect 3 emeralds",
            "EMERALD",
            3,
            65,
            40
        );
        dqm.registerDailyQuest(dailyCollectEmeralds);
        qm.registerQuest(dailyCollectEmeralds);
        
        // ===== ENCHANT QUEST DAILY VARIANTS =====
        EnchantQuest dailyEnchant = new EnchantQuest(
            "daily_enchant_items",
            "Daily: Enchanter",
            "Enchant a book at an enchantment table",
            "PROTECTION",
            1,
            60,
            35
        );
        dqm.registerDailyQuest(dailyEnchant);
        qm.registerQuest(dailyEnchant);
        
        EnchantQuest dailyEnchantTools = new EnchantQuest(
            "daily_enchant_tools",
            "Daily: Tool Master",
            "Enchant 2 tools",
            "EFFICIENCY",
            2,
            70,
            45
        );
        dqm.registerDailyQuest(dailyEnchantTools);
        qm.registerQuest(dailyEnchantTools);
        
        // ===== PLANT QUEST DAILY VARIANTS =====
        PlantQuest dailyPlant = new PlantQuest(
            "daily_plant_flowers",
            "Daily: Florist",
            "Plant 20 flowers",
            "POPPY",
            20,
            50,
            25
        );
        dqm.registerDailyQuest(dailyPlant);
        qm.registerQuest(dailyPlant);
        
        PlantQuest dailyPlantTrees = new PlantQuest(
            "daily_plant_trees",
            "Daily: Forester",
            "Plant 10 saplings",
            "OAK_SAPLING",
            10,
            55,
            30
        );
        dqm.registerDailyQuest(dailyPlantTrees);
        qm.registerQuest(dailyPlantTrees);
        
        // ===== OBTAIN QUEST DAILY VARIANTS =====
        ObtainQuest dailyObtain = new ObtainQuest(
            "daily_obtain_obsidian",
            "Daily: Obsidian Collector",
            "Obtain 10 obsidian blocks",
            "OBSIDIAN",
            10,
            60,
            40
        );
        dqm.registerDailyQuest(dailyObtain);
        qm.registerQuest(dailyObtain);
        
        ObtainQuest dailyObtainGlowstone = new ObtainQuest(
            "daily_obtain_glowstone",
            "Daily: Light Bringer",
            "Obtain 5 glowstone blocks",
            "GLOWSTONE",
            5,
            55,
            35
        );
        dqm.registerDailyQuest(dailyObtainGlowstone);
        qm.registerQuest(dailyObtainGlowstone);
        
        Bukkit.getLogger().info("Registered " + dqm.getDailyQuestPool().size() + " daily quests");
    }
}