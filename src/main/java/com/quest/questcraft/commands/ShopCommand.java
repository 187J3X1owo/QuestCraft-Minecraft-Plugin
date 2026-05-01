package com.quest.questcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.quest.questcraft.managers.CreditManager;
import com.quest.questcraft.shop.ShopManager;
import com.quest.questcraft.shop.ShopItem;
import com.quest.questcraft.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import java.util.*;

/**
 * Handles shop commands for buying items with credits
 */
public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players!");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            openShop(player);
            return true;
        }

        return false;
    }

    /**
     * Open the shop inventory GUI
     */
    public void openShop(Player player) {
        ShopManager shopManager = ShopManager.getInstance();
        CreditManager creditManager = CreditManager.getInstance();
        long playerCredits = creditManager.getCredits(player.getUniqueId());

        // Create inventory (36 slots for various blocks + info)
        Inventory shopInventory = Bukkit.createInventory(null, 54, 
            Component.text("Quest Shop - Credits: " + playerCredits)
                .color(TextColor.fromHexString("#FFD700")));

        // Add shop items to inventory
        int slot = 0;
        for (ShopItem item : shopManager.getAllItems()) {
            if (slot >= 54) break;

            ItemStack itemStack = new ItemStack(item.getMaterial(), item.getStackSize());
            ItemMeta meta = itemStack.getItemMeta();

            if (meta != null) {
                meta.displayName(Component.text(item.getDisplayName())
                    .color(TextColor.fromHexString("#00FF00")));

                List<Component> lore = new ArrayList<>();
                lore.add(Component.text("Price: " + item.getPricePerStack() + " credits")
                    .color(TextColor.fromHexString("#FFD700")));
                lore.add(Component.text("Stack Size: " + item.getStackSize())
                    .color(TextColor.fromHexString("#AAAAAA")));
                lore.add(Component.text(" "));
                lore.add(Component.text("Click to buy a stack")
                    .color(TextColor.fromHexString("#FFFF55")));

                meta.lore(lore);
                itemStack.setItemMeta(meta);
            }

            shopInventory.setItem(slot, itemStack);
            slot++;
        }

        player.openInventory(shopInventory);
        player.sendMessage(Utils.colorize("<gold>Shop opened! Click on items to buy them."));
    }
}
