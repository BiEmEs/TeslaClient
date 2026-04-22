package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.command.Command;

public class Config extends Command {

    public Config() {
        super("config", "Manage config files");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            sendMessage("Usage: +config <load/save> [name]");
            return;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "save":
                if (args.length < 2) {
                    sendError("Usage: +config save <name>");
                    return;
                }
                String saveName = args[1];
                if (Client.getConfigManager() != null) {
                    Client.getConfigManager().saveConfig(saveName);
                    sendSuccess("Config saved: " + saveName);
                }
                break;

            case "load":
                if (args.length < 2) {
                    sendError("Usage: +config load <name>");
                    return;
                }
                String loadName = args[1];
                if (Client.getConfigManager() != null) {
                    Client.getConfigManager().loadConfig(loadName);
                    sendSuccess("Config loaded: " + loadName);
                }
                break;

            case "list":
                if (Client.getConfigManager() != null) {
                    var configs = Client.getConfigManager().getConfigs();
                    if (configs.isEmpty()) {
                        sendMessage("No configs found");
                    } else {
                        sendMessage("Available configs:");
                        for (String c : configs) {
                            sendMessage("§7- " + c);
                        }
                    }
                }
                break;

            default:
                sendError("Unknown action: " + action);
                sendMessage("Usage: +config <load/save/list> [name]");
        }
    }
}