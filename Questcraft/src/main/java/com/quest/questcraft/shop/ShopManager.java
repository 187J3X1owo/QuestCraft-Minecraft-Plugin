package com.quest.questcraft.shop;

import org.bukkit.Material;
import java.util.*;

/**
 * Manages the quest shop and available items
 */
public class ShopManager {
    private final Map<Material, ShopItem> shopItems;
    private static ShopManager instance;

    private ShopManager() {
        this.shopItems = new LinkedHashMap<>();
        initializeShop();
    }

    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    /**
     * Initialize shop with default building materials
     */
    private void initializeShop() {
        // ===== CONCRETE BLOCKS =====
        addItem(new ShopItem(Material.WHITE_CONCRETE, 10, 64, "White Concrete", "Basic white concrete block"));
        addItem(new ShopItem(Material.RED_CONCRETE, 10, 64, "Red Concrete", "Basic red concrete block"));
        addItem(new ShopItem(Material.BLUE_CONCRETE, 10, 64, "Blue Concrete", "Basic blue concrete block"));
        addItem(new ShopItem(Material.GREEN_CONCRETE, 10, 64, "Green Concrete", "Basic green concrete block"));
        addItem(new ShopItem(Material.YELLOW_CONCRETE, 10, 64, "Yellow Concrete", "Basic yellow concrete block"));
        addItem(new ShopItem(Material.BLACK_CONCRETE, 10, 64, "Black Concrete", "Sleek black concrete block"));
        addItem(new ShopItem(Material.ORANGE_CONCRETE, 10, 64, "Orange Concrete", "Vibrant orange concrete block"));
        addItem(new ShopItem(Material.PURPLE_CONCRETE, 10, 64, "Purple Concrete", "Purple concrete block"));
        addItem(new ShopItem(Material.CYAN_CONCRETE, 10, 64, "Cyan Concrete", "Cyan concrete block"));
        addItem(new ShopItem(Material.GRAY_CONCRETE, 10, 64, "Gray Concrete", "Gray concrete block"));

        // ===== WOOL BLOCKS =====
        addItem(new ShopItem(Material.WHITE_WOOL, 8, 64, "White Wool", "Soft white wool block"));
        addItem(new ShopItem(Material.RED_WOOL, 8, 64, "Red Wool", "Soft red wool block"));
        addItem(new ShopItem(Material.BLUE_WOOL, 8, 64, "Blue Wool", "Soft blue wool block"));
        addItem(new ShopItem(Material.BLACK_WOOL, 8, 64, "Black Wool", "Soft black wool block"));
        addItem(new ShopItem(Material.ORANGE_WOOL, 8, 64, "Orange Wool", "Soft orange wool block"));
        addItem(new ShopItem(Material.PURPLE_WOOL, 8, 64, "Purple Wool", "Soft purple wool block"));
        addItem(new ShopItem(Material.GREEN_WOOL, 8, 64, "Green Wool", "Soft green wool block"));

        // ===== WOOD LOGS =====
        addItem(new ShopItem(Material.OAK_LOG, 5, 64, "Oak Log", "Oak wood log"));
        addItem(new ShopItem(Material.BIRCH_LOG, 5, 64, "Birch Log", "Birch wood log"));
        addItem(new ShopItem(Material.SPRUCE_LOG, 5, 64, "Spruce Log", "Spruce wood log"));
        addItem(new ShopItem(Material.DARK_OAK_LOG, 6, 64, "Dark Oak Log", "Dark oak wood log"));
        addItem(new ShopItem(Material.JUNGLE_LOG, 5, 64, "Jungle Log", "Jungle wood log"));
        addItem(new ShopItem(Material.ACACIA_LOG, 5, 64, "Acacia Log", "Acacia wood log"));
        addItem(new ShopItem(Material.CRIMSON_STEM, 8, 64, "Crimson Stem", "Nether crimson wood log"));
        addItem(new ShopItem(Material.WARPED_STEM, 8, 64, "Warped Stem", "Nether warped wood log"));

        // ===== WOOD PLANKS =====
        addItem(new ShopItem(Material.OAK_PLANKS, 2, 64, "Oak Planks", "Oak wood planks"));
        addItem(new ShopItem(Material.BIRCH_PLANKS, 2, 64, "Birch Planks", "Birch wood planks"));
        addItem(new ShopItem(Material.SPRUCE_PLANKS, 2, 64, "Spruce Planks", "Spruce wood planks"));
        addItem(new ShopItem(Material.DARK_OAK_PLANKS, 2, 64, "Dark Oak Planks", "Dark oak wood planks"));
        addItem(new ShopItem(Material.JUNGLE_PLANKS, 2, 64, "Jungle Planks", "Jungle wood planks"));
        addItem(new ShopItem(Material.CRIMSON_PLANKS, 3, 64, "Crimson Planks", "Crimson wood planks"));
        addItem(new ShopItem(Material.WARPED_PLANKS, 3, 64, "Warped Planks", "Warped wood planks"));

        // ===== STONE VARIANTS =====
        addItem(new ShopItem(Material.STONE, 6, 64, "Stone", "Classic stone block"));
        addItem(new ShopItem(Material.COBBLESTONE, 5, 64, "Cobblestone", "Rough cobblestone block"));
        addItem(new ShopItem(Material.STONE_BRICKS, 8, 64, "Stone Brick", "Polished stone bricks"));
        addItem(new ShopItem(Material.MOSSY_STONE_BRICKS, 9, 64, "Mossy Stone Brick", "Ancient mossy stone bricks"));
        addItem(new ShopItem(Material.CRACKED_STONE_BRICKS, 8, 64, "Cracked Stone Brick", "Damaged stone bricks"));
        addItem(new ShopItem(Material.CHISELED_STONE_BRICKS, 10, 64, "Chiseled Stone Brick", "Ornate stone bricks"));
        addItem(new ShopItem(Material.DEEPSLATE, 7, 64, "Deepslate", "Deep cave stone"));
        addItem(new ShopItem(Material.DEEPSLATE_BRICKS, 9, 64, "Deepslate Bricks", "Processed deepslate bricks"));
        addItem(new ShopItem(Material.DRIPSTONE_BLOCK, 8, 64, "Dripstone Block", "Mineral dripstone block"));

        // ===== BRICKS & NETHER =====
        addItem(new ShopItem(Material.BRICKS, 12, 64, "Brick Block", "Red brick blocks"));
        addItem(new ShopItem(Material.NETHER_BRICKS, 10, 64, "Nether Brick", "Dark nether brick"));
        addItem(new ShopItem(Material.RED_NETHER_BRICKS, 10, 64, "Red Nether Brick", "Red nether brick blocks"));
        addItem(new ShopItem(Material.BLACKSTONE, 7, 64, "Blackstone", "Dark nether stone"));
        addItem(new ShopItem(Material.BASALT, 7, 64, "Basalt", "Volcanic basalt block"));
        addItem(new ShopItem(Material.OBSIDIAN, 15, 64, "Obsidian", "Ultra-hard obsidian block"));

        // ===== LIGHTING BLOCKS =====
        addItem(new ShopItem(Material.GLOWSTONE, 20, 64, "Glowstone", "Bright glowing block"));
        addItem(new ShopItem(Material.LANTERN, 25, 64, "Lantern", "Decorative lantern"));
        addItem(new ShopItem(Material.SEA_LANTERN, 30, 64, "Sea Lantern", "Ocean-themed lantern"));
        addItem(new ShopItem(Material.AMETHYST_BLOCK, 22, 64, "Amethyst Block", "Purple crystal amethyst"));
        addItem(new ShopItem(Material.SOUL_LANTERN, 28, 64, "Soul Lantern", "Blue soul lantern"));
        addItem(new ShopItem(Material.SOUL_TORCH, 5, 64, "Soul Torch", "Blue burning torch"));

        // ===== GLASS VARIANTS =====
        addItem(new ShopItem(Material.GLASS, 7, 64, "Glass", "Clear glass block"));
        addItem(new ShopItem(Material.TINTED_GLASS, 8, 64, "Tinted Glass", "Dark tinted glass"));
        addItem(new ShopItem(Material.WHITE_STAINED_GLASS, 8, 64, "White Stained Glass", "White colored glass"));
        addItem(new ShopItem(Material.BLUE_STAINED_GLASS, 8, 64, "Blue Stained Glass", "Blue colored glass"));
        addItem(new ShopItem(Material.RED_STAINED_GLASS, 8, 64, "Red Stained Glass", "Red colored glass"));

        // ===== LEAVES & NATURE =====
        addItem(new ShopItem(Material.OAK_LEAVES, 4, 64, "Oak Leaves", "Leafy oak foliage"));
        addItem(new ShopItem(Material.BIRCH_LEAVES, 4, 64, "Birch Leaves", "Leafy birch foliage"));
        addItem(new ShopItem(Material.SPRUCE_LEAVES, 4, 64, "Spruce Leaves", "Leafy spruce foliage"));
        addItem(new ShopItem(Material.JUNGLE_LEAVES, 4, 64, "Jungle Leaves", "Dense jungle foliage"));
        addItem(new ShopItem(Material.DARK_OAK_LEAVES, 4, 64, "Dark Oak Leaves", "Dark oak foliage"));
        addItem(new ShopItem(Material.CRIMSON_NYLIUM, 12, 64, "Crimson Nylium", "Nether crimson vegetation"));
        addItem(new ShopItem(Material.WARPED_NYLIUM, 12, 64, "Warped Nylium", "Nether warped vegetation"));

        // ===== DECORATIVE BLOCKS =====
        addItem(new ShopItem(Material.SCAFFOLDING, 3, 64, "Scaffolding", "Temporary building structure"));
        addItem(new ShopItem(Material.HONEY_BLOCK, 10, 64, "Honey Block", "Sticky honey block"));
        addItem(new ShopItem(Material.SLIME_BLOCK, 11, 64, "Slime Block", "Bouncy slime block"));
        addItem(new ShopItem(Material.TERRACOTTA, 9, 64, "Terracotta", "Hardened clay block"));
        addItem(new ShopItem(Material.WHITE_TERRACOTTA, 9, 64, "White Terracotta", "White hardened clay"));
        addItem(new ShopItem(Material.ORANGE_TERRACOTTA, 9, 64, "Orange Terracotta", "Orange hardened clay"));
        addItem(new ShopItem(Material.MAGMA_BLOCK, 14, 64, "Magma Block", "Hot magma block"));
        addItem(new ShopItem(Material.IRON_BLOCK, 50, 64, "Iron Block", "Solid iron storage block"));
        addItem(new ShopItem(Material.GOLD_BLOCK, 75, 64, "Gold Block", "Solid gold storage block"));
        addItem(new ShopItem(Material.EMERALD_BLOCK, 100, 64, "Emerald Block", "Solid emerald storage block"));
        addItem(new ShopItem(Material.DIAMOND_BLOCK, 150, 64, "Diamond Block", "Solid diamond storage block"));

        // ===== SAND & GRAVEL =====
        addItem(new ShopItem(Material.SAND, 3, 64, "Sand", "Desert sand block"));
        addItem(new ShopItem(Material.RED_SAND, 3, 64, "Red Sand", "Mesa red sand block"));
        addItem(new ShopItem(Material.GRAVEL, 3, 64, "Gravel", "Loose gravel block"));
        addItem(new ShopItem(Material.SOUL_SAND, 5, 64, "Soul Sand", "Dark nether soul sand"));

        // ===== DIRT & GRASS =====
        addItem(new ShopItem(Material.DIRT, 1, 64, "Dirt", "Common dirt block"));
        addItem(new ShopItem(Material.GRASS_BLOCK, 2, 64, "Grass Block", "Grass-covered dirt"));
        addItem(new ShopItem(Material.PODZOL, 2, 64, "Podzol", "Forest floor material"));
        addItem(new ShopItem(Material.MYCELIUM, 4, 64, "Mycelium", "Mushroom biome floor"));

        // ===== SPECIAL BLOCKS =====
        addItem(new ShopItem(Material.SOUL_SOIL, 5, 64, "Soul Soil", "Nether soul soil"));
        addItem(new ShopItem(Material.SCULK_CATALYST, 80, 1, "Sculk Catalyst", "Ancient sculk catalyst block"));
        addItem(new ShopItem(Material.MOSS_BLOCK, 6, 64, "Moss Block", "Lush moss block"));
        addItem(new ShopItem(Material.BONE_BLOCK, 8, 64, "Bone Block", "Solid bone material"));
    }

    /**
     * Add a shop item
     */
    public void addItem(ShopItem item) {
        shopItems.put(item.getMaterial(), item);
    }

    /**
     * Get a shop item by material
     */
    public ShopItem getItem(Material material) {
        return shopItems.get(material);
    }

    /**
     * Get all shop items
     */
    public Collection<ShopItem> getAllItems() {
        return new ArrayList<>(shopItems.values());
    }

    /**
     * Check if an item is available in shop
     */
    public boolean isAvailable(Material material) {
        return shopItems.containsKey(material);
    }
}
