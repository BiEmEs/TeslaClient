package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.modules.utils.player.FriendUtil;
import com.bieme.tesla.other.command.Command;

public class Friend extends Command {

    public Friend() {
        super("friend", "Manage friends");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            sendMessage("Usage: +friend <add/remove/list> [name]");
            return;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "add":
                if (args.length < 2) {
                    sendError("Usage: +friend add <name>");
                    return;
                }
                String addName = args[1];
                if (FriendUtil.isFriend(addName)) {
                    sendError(addName + " is already your friend");
                } else {
                    FriendUtil.addFriend(addName);
                    sendSuccess("Added " + addName + " to friends");
                }
                break;

            case "remove":
            case "del":
                if (args.length < 2) {
                    sendError("Usage: +friend remove <name>");
                    return;
                }
                String removeName = args[1];
                if (!FriendUtil.isFriend(removeName)) {
                    sendError(removeName + " is not your friend");
                } else {
                    FriendUtil.removeFriend(removeName);
                    sendSuccess("Removed " + removeName + " from friends");
                }
                break;

            case "list":
                var friends = FriendUtil.getFriends();
                if (friends.isEmpty()) {
                    sendMessage("No friends added");
                } else {
                    sendMessage("Friends (" + friends.size() + "):");
                    for (var f : friends) {
                        sendMessage("§7- " + f.getUsername());
                    }
                }
                break;

            default:
                sendError("Unknown action: " + action);
                sendMessage("Usage: +friend <add/remove/list> [name]");
        }
    }
}