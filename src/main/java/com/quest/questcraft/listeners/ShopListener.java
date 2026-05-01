package com.quest.questcraft.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.quest.questcraft.managers.CreditManager;
import com.quest.questcraft.shop.ShopManager;
import com.quest.questcraft.shop.ShopItem;
import com.quest.questcraft.utils.Utils;
import net.kyori.adventure.text.Component;

/*
  Handles shop inventory clicks
 */
public class ShopListener implements Listener {

    @EventHandler
    public void onShopInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Component title = event.getView().title();

        // Check if this is the quest shop inventory
        if (!title.toString().contains("Quest Shop")) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        // Check if the clicked item is from the player's own inventory (bottom inventory slot)
        // Top inventory (0-26) is shop, bottom inventory (27-35) is player's hotbar
        if (event.getSlot() >= 27 || (event.getClickedInventory() != null && event.getClickedInventory() == player.getInventory())) {
            player.sendMessage(Utils.colorize("<red>You can only buy items from the shop, not from your inventory!"));
            return;
        }

        Material material = clickedItem.getType();
        ShopManager shopManager = ShopManager.getInstance();
        ShopItem shopItem = shopManager.getItem(material);

        if (shopItem == null) {
            player.sendMessage(Utils.colorize("<red>This item is not available for purchase!"));
            return;
        }

        CreditManager creditManager = CreditManager.getInstance();
        long price = shopItem.getPricePerStack();
        long playerCredits = creditManager.getCredits(player.getUniqueId());

        // Check if player has enough credits
        if (playerCredits < price) {
            player.sendMessage(Utils.colorize(String.format(
                "<red>You need %d credits to buy this item! You only have %d credits.",
                price - playerCredits,
                playerCredits
            )));
            return;
        }

        // Remove credits
        try {
            creditManager.removeCredits(player.getUniqueId(), price);
            ItemStack purchasedItem = new ItemStack(material, shopItem.getStackSize());
            
            // Try to add to inventory, if full, drop on ground
            if (player.getInventory().firstEmpty() == -1) {
                // Inventory is full, drop items on ground
                player.getWorld().dropItem(player.getLocation(), purchasedItem);
                player.sendMessage(Utils.colorize(String.format(
                    "<yellow>Your inventory is full! Items dropped at your feet.\\n<green>✓ Purchased <yellow>%s <green>for <gold>%d <green>credits!",
                    shopItem.getDisplayName(),
                    price
                )));
            } else {
                // Add to inventory normally
                player.getInventory().addItem(purchasedItem);
                player.sendMessage(Utils.colorize(String.format(
                    "<green>✓ Purchased <yellow>%s <green>for <gold>%d <green>credits!",
                    shopItem.getDisplayName(),
                    price
                )));
            }

            // Update player list header with new credit amount
            PlayerListListener.updatePlayerListHeader(player);
            
            // Play bell sound effect for purchase confirmation
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.0f);
        } catch (IllegalArgumentException e) {
            player.sendMessage(Utils.colorize("<red>Transaction failed: " + e.getMessage()));
        }
    }
}
