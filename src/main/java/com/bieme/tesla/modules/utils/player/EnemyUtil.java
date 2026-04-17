package com.bieme.tesla.modules.utils.player;

import java.util.*;

public class EnemyUtil {

    public static List<Enemy> enemies = new ArrayList<>();

    public static boolean isEnemy(String name) {
        return enemies.stream().anyMatch(e -> e.username.equalsIgnoreCase(name));
    }

    public static class Enemy {
        private final String username;
        private final UUID uuid;

        public Enemy(String username, UUID uuid) {
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

    public static void addEnemy(String username, UUID uuid) {
        enemies.add(new Enemy(username, uuid));
    }

    public static void removeEnemy(String username) {
        enemies.removeIf(e -> e.username.equalsIgnoreCase(username));
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }
}