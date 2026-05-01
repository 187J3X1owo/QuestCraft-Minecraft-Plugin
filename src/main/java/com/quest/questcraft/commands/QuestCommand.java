package com.quest.questcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.quest.questcraft.managers.QuestManager;
import com.quest.questcraft.managers.DailyQuestManager;
import com.quest.questcraft.managers.CreditManager;
import com.quest.questcraft.quests.Quest;
import com.quest.questcraft.utils.Utils;
import java.util.Set;

/**
 * Main command handler for quest system
 */
public class QuestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players!");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelpMessage(player);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "list":
                return handleList(player);
            case "start":
                return handleStart(player, args);
            case "progress":
                return handleProgress(player, args);
            case "abandon":
                return handleAbandon(player, args);
            case "complete":
                return handleComplete(player, args);
            case "day":
                return handleDay(player);
            case "credits":
                return handleCredits(player);
            case "shop":
                return handleShop(player);
            case "reset":
                return handleReset(sender, player);
            default:
                sendHelpMessage(player);
                return false;
        }
    }

    private boolean handleList(Player player) {
        QuestManager manager = QuestManager.getInstance();
        Set<Quest> activeQuests = manager.getPlayerActiveQuests(player.getUniqueId());
        Set<Quest> completedQuests = manager.getPlayerCompletedQuests(player.getUniqueId());

        player.sendMessage(Utils.colorize("<gold>========== Your Quests =========="));

        if (activeQuests.isEmpty()) {
            player.sendMessage(Utils.colorize("<gray>No active quests"));
        } else {
            player.sendMessage(Utils.colorize("<yellow>Active Quests:"));
            for (Quest quest : activeQuests) {
                int progress = manager.getPlayerData(player.getUniqueId()).getProgress(quest.getQuestId());
                player.sendMessage(Utils.colorize(String.format(
                    "<white>  • <aqua>%s <gray>(%d/%d) <dark_gray>%s",
                    quest.getName(),
                    progress,
                    quest.getMaxProgress(),
                    quest.getObjective()
                )));
            }
        }

        if (!completedQuests.isEmpty()) {
            player.sendMessage(Utils.colorize("<yellow>Completed Quests:"));
            for (Quest quest : completedQuests) {
                player.sendMessage(Utils.colorize("<white>  • <green>✓ " + quest.getName()));
            }
        }

        return true;
    }

    private boolean handleStart(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Utils.colorize("<red>Usage: /quest start <questId>"));
            return false;
        }

        String questId = args[1];
        Quest quest = QuestManager.getInstance().getQuest(questId);

        if (quest == null) {
            player.sendMessage(Utils.colorize("<red>Quest not found: " + questId));
            return false;
        }

        if (QuestManager.getInstance().getPlayerData(player.getUniqueId()).hasCompletedQuest(questId)) {
            player.sendMessage(Utils.colorize("<red>You have already completed this quest!"));
            return false;
        }

        try {
            QuestManager.getInstance().startQuestForPlayer(player.getUniqueId(), questId);
            player.sendMessage(Utils.colorize("<green>✓ Quest started: <yellow>" + quest.getName()));
            player.sendMessage(Utils.colorize("<gray>" + quest.getDescription()));
            player.sendMessage(Utils.colorize("<gray>Objective: " + quest.getObjective()));
            return true;
        } catch (IllegalArgumentException e) {
            player.sendMessage(Utils.colorize("<red>" + e.getMessage()));
            return false;
        }
    }

    private boolean handleProgress(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Utils.colorize("<red>Usage: /quest progress <questId>"));
            return false;
        }

        String questId = args[1];
        QuestManager manager = QuestManager.getInstance();
        Quest quest = manager.getQuest(questId);

        if (quest == null) {
            player.sendMessage(Utils.colorize("<red>Quest not found: " + questId));
            return false;
        }

        if (!manager.getPlayerData(player.getUniqueId()).hasActiveQuest(questId)) {
            player.sendMessage(Utils.colorize("<red>You don't have this quest active!"));
            return false;
        }

        int progress = manager.getPlayerData(player.getUniqueId()).getProgress(questId);
        player.sendMessage(Utils.colorize(String.format(
            "<yellow>%s<gray>: <aqua>%d<gray>/<aqua>%d",
            quest.getName(),
            progress,
            quest.getMaxProgress()
        )));
        return true;
    }

    private boolean handleAbandon(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Utils.colorize("<red>Usage: /quest abandon <questId>"));
            return false;
        }

        String questId = args[1];
        QuestManager manager = QuestManager.getInstance();

        if (!manager.getPlayerData(player.getUniqueId()).hasActiveQuest(questId)) {
            player.sendMessage(Utils.colorize("<red>You don't have this quest active!"));
            return false;
        }

        manager.abandonQuest(player.getUniqueId(), questId);
        player.sendMessage(Utils.colorize("<red>✗ Quest abandoned: " + manager.getQuest(questId).getName()));
        return true;
    }

    private boolean handleComplete(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Utils.colorize("<red>Usage: /quest complete <questId>"));
            return false;
        }

        String questId = args[1];
        QuestManager manager = QuestManager.getInstance();

        if (!manager.getPlayerData(player.getUniqueId()).hasActiveQuest(questId)) {
            player.sendMessage(Utils.colorize("<red>You don't have this quest active!"));
            return false;
        }

        Quest quest = manager.getQuest(questId);
        manager.completeQuest(player.getUniqueId(), questId);
        
        // Award credits
        CreditManager creditManager = CreditManager.getInstance();
        creditManager.addCredits(player.getUniqueId(), quest.getRewardCredits());
        
        player.sendMessage(Utils.colorize("<green>✓ Quest completed: " + quest.getName()));
        player.sendMessage(Utils.colorize("<gray>Rewards: <yellow>" + quest.getRewardXp() + " XP, <gold>" + quest.getRewardCredits() + " Credits"));
        return true;
    }

    private boolean handleDay(Player player) {
        DailyQuestManager dailyQuestManager = DailyQuestManager.getInstance();
        Set<Quest> dailyQuests = dailyQuestManager.getPlayerDailyQuests(player.getUniqueId());

        player.sendMessage(Utils.colorize("<gold>========== Your Daily Quests =========="));

        if (dailyQuests.isEmpty()) {
            player.sendMessage(Utils.colorize("<gray>No daily quests assigned. Come back later!"));
            return true;
        }

        int index = 1;
        QuestManager qm = QuestManager.getInstance();
        for (Quest quest : dailyQuests) {
            int progress = qm.getPlayerData(player.getUniqueId()).getProgress(quest.getQuestId());
            player.sendMessage(Utils.colorize(String.format(
                "<white>%d. <aqua>%s <gray>(%d/%d)",
                index,
                quest.getName(),
                progress,
                quest.getMaxProgress()
            )));
            index++;
        }

        return true;
    }

    private boolean handleCredits(Player player) {
        CreditManager creditManager = CreditManager.getInstance();
        long credits = creditManager.getCredits(player.getUniqueId());

        player.sendMessage(Utils.colorize(String.format(
            "<gold>========== Your Credits ==========<yellow> Balance: <gold>%d",
            credits
        )));
        return true;
    }

    private boolean handleShop(Player player) {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.openShop(player);
        return true;
    }

    private boolean handleReset(CommandSender sender, Player player) {
        // Only allow OP players to reset quests
        if (!sender.isOp()) {
            player.sendMessage(Utils.colorize("<red>You don't have permission to use this command!"));
            return false;
        }

        // Reset all online players' quests
        QuestManager qm = QuestManager.getInstance();
        DailyQuestManager dqm = DailyQuestManager.getInstance();
        CreditManager cm = CreditManager.getInstance();

        int resetCount = 0;
        for (org.bukkit.entity.Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            qm.resetPlayerData(onlinePlayer.getUniqueId());
            dqm.resetDailyQuestsForPlayer(onlinePlayer.getUniqueId());
            cm.resetCredits(onlinePlayer.getUniqueId());
            dqm.notifyPlayerDailyQuests(onlinePlayer);
            onlinePlayer.sendMessage(Utils.colorize("<yellow>⚡ Quest system has been reset by an admin!"));
            resetCount++;
        }

        player.sendMessage(Utils.colorize(String.format(
            "<green>✓ Reset quests for <yellow>%d <green>players!",
            resetCount
        )));
        return true;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(Utils.colorize("<gold>========== Quest System Help =========="));
        player.sendMessage(Utils.colorize("<yellow>/quest list <gray>- Show all quests"));
        player.sendMessage(Utils.colorize("<yellow>/quest start <questId> <gray>- Start a quest"));
        player.sendMessage(Utils.colorize("<yellow>/quest progress <questId> <gray>- Check quest progress"));
        player.sendMessage(Utils.colorize("<yellow>/quest abandon <questId> <gray>- Abandon a quest"));
        player.sendMessage(Utils.colorize("<yellow>/quest day <gray>- View daily quests"));
        player.sendMessage(Utils.colorize("<yellow>/quest credits <gray>- Check your credit balance"));
        player.sendMessage(Utils.colorize("<yellow>/quest shop <gray>- Open the shop"));
        player.sendMessage(Utils.colorize("<yellow>/quest reset <gray>- Reset all quests (OP only)"));
    }
}
