package com.bieme.tesla.modules.utils.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class MessageUtil {

    private static final String PREFIX_PURPLE = "§d";
    private static final String PREFIX_DARK_GRAY = "§8";
    private static final String PREFIX_GRAY = "§7";

    private static String getPrefix() {
        return PREFIX_PURPLE + "Tesla" + PREFIX_DARK_GRAY + ">" + PREFIX_GRAY + ">";
    }

    private static Component getTextComponent(String message) {
        String full = getPrefix() + "§f " + message;
        return Component.literal(full);
    }

    public static void sendMessage(String message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && message != null) {
            mc.gui.getChat().addMessage(getTextComponent(message));
        }
    }

    public static void sendClientMessage(String message) {
        sendMessage(message);
    }

    public static void sendChatMessage(String message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && message != null) {
            try {
                mc.player.getClass().getMethod("sendChatMessage", String.class).invoke(mc.player, message);
            } catch (Exception e) {
                sendMessage(message);
            }
        }
    }

    public static void printChat(String message) {
        sendMessage(message);
    }

    public static void printError(String message) {
        sendMessage("§c" + message);
    }

    public static void printSuccess(String message) {
        sendMessage("§a" + message);
    }
}