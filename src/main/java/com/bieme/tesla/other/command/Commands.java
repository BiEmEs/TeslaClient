package com.bieme.tesla.other.command;

import net.minecraft.client.Minecraft;

public class Commands {

    public static void registerCommand(Command command) {
    }

    public static void handleCommand(String input) {
        if (input == null || input.isEmpty()) return;
        
        String[] parts = input.split(" ");
        String commandName = parts[0].toLowerCase();
        
        switch (commandName) {
            case "gui":
            case "tesla":
                openGui();
                break;
            case "help":
                sendMessage("TeslaClient Commands:");
                sendMessage("/gui - Open TeslaClient GUI");
                sendMessage("/killaura - Toggle KillAura");
                break;
            default:
                sendMessage("Unknown command. Use /help");
        }
    }

    public static void openGui() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.screen == null) {
            mc.setScreen(new com.bieme.tesla.other.guiscreen.ClientGui());
            sendMessage("TeslaClient GUI opened!");
        } else if (mc.screen != null) {
            mc.screen.onClose();
            sendMessage("GUI closed!");
        }
    }

    public static void sendMessage(String message) {
        if (message != null) {
            message = "TeslaClient: " + message;
        }
    }
}