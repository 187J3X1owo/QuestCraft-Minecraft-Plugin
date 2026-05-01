package com.quest.questcraft.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class Utils {
    
    public static Component colorize(String msg) {
        // Paper recommends using MiniMessage or Adventure Components
        if (msg.contains("<") && msg.contains(">")) {
            return MiniMessage.miniMessage().deserialize(msg);
        }
        return LegacyComponentSerializer.legacyAmpersand().deserialize(msg);
    }
    
    public static String legacyColorize(String msg) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(
            LegacyComponentSerializer.legacyAmpersand().deserialize(msg)
        );
    }
}