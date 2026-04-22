package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.command.Command;
import java.util.List;

public class Help extends Command {

    public Help() {
        super("help", "Show help");
    }

    @Override
    public void execute(String[] args) {
        sendMessage("TeslaClient Commands:");
        sendMessage("+toggle <module> - Toggle module on/off");
        sendMessage("+bind <module> <key> - Bind key to module");
        sendMessage("+help - Show this help");
        sendMessage("+friend <add/remove/list> - Manage friends");
        sendMessage("+config <load/save> - Manage config");

        if (args.length > 0 && args[0].equalsIgnoreCase("modules")) {
            List<Module> modules = Client.getHackManager().get_modules();
            sendMessage("Available modules:");
            for (Module m : modules) {
                String state = m.isEnabled() ? "§aON" : "§cOFF";
                sendMessage("§7- " + m.getName() + " §8[" + state + "§8]");
            }
        }
    }
}