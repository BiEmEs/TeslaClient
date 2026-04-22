package com.bieme.tesla.other.manager;

import com.bieme.tesla.other.command.Command;
import com.bieme.tesla.other.command.commands.Bind;
import com.bieme.tesla.other.command.commands.Config;
import com.bieme.tesla.other.command.commands.Friend;
import com.bieme.tesla.other.command.commands.Help;
import com.bieme.tesla.other.command.commands.Toggle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerCommand {

    private final List<Command> commands = new ArrayList<>();

    public ManagerCommand() {
    }

    public void init() {
        register(new Bind());
        register(new Config());
        register(new Friend());
        register(new Help());
        register(new Toggle());
    }

    public void register(Command command) {
        if (command != null && !commands.contains(command)) {
            commands.add(command);
        }
    }

    public void execute(String input) {
        if (input == null || input.isEmpty()) return;

        String[] parts = input.split(" ");
        String commandName = parts[0].toLowerCase();
        String[] args = parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0];

        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(commandName)) {
                command.execute(args);
                return;
            }
            if (command.getAliases() != null) {
                for (String alias : command.getAliases()) {
                    if (alias.equalsIgnoreCase(commandName)) {
                        command.execute(args);
                        return;
                    }
                }
            }
        }

        com.bieme.tesla.modules.utils.chat.MessageUtil.sendMessage("Unknown command. Use +help");
    }

    public List<Command> getCommands() {
        return commands;
    }
}