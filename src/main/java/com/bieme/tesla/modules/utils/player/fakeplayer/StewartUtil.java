package com.bieme.tesla.modules.utils.player.fakeplayer;

import net.minecraft.client.Minecraft;

public class StewartUtil {

    private static StewartEntity stewart = null;
    private static boolean spawned = false;
    private static final Minecraft mc = Minecraft.getInstance();

    public static void spawn() {
        stewart = new StewartEntity();
        stewart.spawn();
        spawned = true;
    }

    public static void despawn() {
        if (stewart != null) {
            stewart.despawn();
            stewart = null;
        }
        spawned = false;
    }

    public static boolean isSpawned() {
        return spawned && stewart != null && stewart.isActive();
    }

    public static void update() {
        if (!isSpawned()) return;
        stewart.update();
    }

    public static StewartEntity getStewart() {
        return stewart;
    }

    public static void kill() {
        if (stewart != null) {
            stewart.damage(1000);
        }
    }

    public static float getHealth() {
        return stewart != null ? stewart.getHealth() : 0;
    }

    public static void follow() {
        if (stewart != null && mc.player != null) {
            stewart.follow(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        }
    }
}