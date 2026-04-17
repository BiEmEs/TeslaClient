package com.bieme.tesla.modules.utils.player;

import java.util.*;

public class FriendUtil {

    public static ArrayList<Friend> friends = new ArrayList<>();

    public static boolean isFriend(String name) {
        return friends.stream().anyMatch(f -> f.username.equalsIgnoreCase(name));
    }

    public static class Friend {
        private final String username;
        private final UUID uuid;

        public Friend(String username, UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }

        public UUID getUuid() {
            return uuid;
        }
    }

    public static void addFriend(String username) {
        friends.add(new Friend(username, UUID.randomUUID()));
    }

    public static void addFriend(String username, UUID uuid) {
        friends.add(new Friend(username, uuid));
    }

    public static void removeFriend(String username) {
        friends.removeIf(f -> f.username.equalsIgnoreCase(username));
    }

    public static ArrayList<Friend> getFriends() {
        return friends;
    }
}