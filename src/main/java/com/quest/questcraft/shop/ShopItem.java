package com.quest.questcraft.shop;

import org.bukkit.Material;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

/**
 * Represents an item that can be purchased in the quest shop
 */
public class ShopItem {
    private final Material material;
    private final long pricePerStack;
    private final int stackSize;
    private final String displayName;
    private final String description;

    public ShopItem(Material material, long pricePerStack, int stackSize, String displayName, String description) {
        this.material = material;
        this.pricePerStack = pricePerStack;
        this.stackSize = stackSize;
        this.displayName = displayName;
        this.description = description;
    }

    public Material getMaterial() {
        return material;
    }

    public long getPricePerStack() {
        return pricePerStack;
    }

    public int getStackSize() {
        return stackSize;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Component getDisplayComponent() {
        return Component.text(displayName)
            .color(TextColor.fromHexString("#00FF00"));
    }

    @Override
    public String toString() {
        return String.format("%s - %d credits per stack", displayName, pricePerStack);
    }
}
