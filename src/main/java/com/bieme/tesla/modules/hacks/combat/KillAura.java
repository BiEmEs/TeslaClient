package com.bieme.tesla.modules.hacks.combat;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.guiscreen.settings.Setting;

public class KillAura extends Module {
    
    public static double range = 4.5;
    public static int hitDelay = 11;
    public static boolean autoSwitch = false;
    public static boolean rotate = true;
    public static boolean enemies = true;
    public static boolean mobs = false;
    public static boolean players = true;

    public KillAura() {
        super("KillAura", "Automatically attacks entities", Category.COMBAT);
        
        create("Range", "Range", 4.5, 1.0, 8.0);
        create("Hit Delay", "HitDelay", 11, 0, 40);
        create("Auto Switch", "AutoSwitch", false);
        create("Rotate", "Rotate", true);
        create("Attack Players", "AttackPlayers", true);
        create("Attack Mobs", "AttackMobs", false);
        create("Attack Enemies", "AttackEnemies", true);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onUpdate() {
    }
}