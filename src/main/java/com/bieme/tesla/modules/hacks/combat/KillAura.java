package com.bieme.tesla.modules.hacks.combat;

import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.utils.player.EnemyUtil;
import com.bieme.tesla.modules.utils.player.FriendUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

public class KillAura extends Module {

    private int tickCounter = 0;

    public KillAura() {
        super("KillAura", "Automatically attacks entities", Category.COMBAT);
        create("Range",           "Range",         4.5,  1.0, 8.0);
        create("Hit Delay",       "HitDelay",      11,   0,   40);
        create("Rotate",          "Rotate",        true);
        create("Attack Players",  "AttackPlayers", true);
        create("Attack Mobs",     "AttackMobs",    false);
        create("Attack Enemies",  "AttackEnemies", true);
    }

    @Override
    public void onDisable() {
        tickCounter = 0;
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) return;

        // Leer settings
        double range = 4.5;
        int hitDelay = 11;
        boolean rotate = true;
        boolean attackPlayers = true;
        boolean attackMobs = false;
        boolean attackEnemies = true;

        for (var s : getSettings()) {
            switch (s.getName()) {
                case "Range"         -> range         = s.getSliderValue();
                case "HitDelay"      -> hitDelay      = (int) s.getSliderValue();
                case "Rotate"        -> rotate        = s.getBoolValue();
                case "AttackPlayers" -> attackPlayers = s.getBoolValue();
                case "AttackMobs"    -> attackMobs    = s.getBoolValue();
                case "AttackEnemies" -> attackEnemies = s.getBoolValue();
            }
        }

        tickCounter++;
        if (tickCounter < hitDelay) return;

        // Buscar target más cercano dentro del rango
        final double finalRange = range;
        final boolean finalAttackPlayers = attackPlayers;
        final boolean finalAttackMobs = attackMobs;
        final boolean finalAttackEnemies = attackEnemies;

        AABB box = mc.player.getBoundingBox().inflate(finalRange);
        List<LivingEntity> targets = mc.level.getEntitiesOfClass(LivingEntity.class, box, e -> {
            if (e == mc.player) return false;
            if (!e.isAlive()) return false;
            if (mc.player.distanceTo(e) > finalRange) return false;

            if (e instanceof Player p) {
                String name = p.getName().getString();
                if (FriendUtil.isFriend(name)) return false;
                if (finalAttackEnemies && EnemyUtil.isEnemy(name)) return true;
                return finalAttackPlayers;
            }

            if (e instanceof Monster) return finalAttackMobs;

            return false;
        });

        if (targets.isEmpty()) return;

        LivingEntity target = targets.stream()
                .min(Comparator.comparingDouble(e -> mc.player.distanceTo(e)))
                .orElse(null);

        if (target == null) return;

        // Rotar hacia el target
        if (rotate) {
            double dx = target.getX() - mc.player.getX();
            double dy = target.getEyeY() - mc.player.getEyeY();
            double dz = target.getZ() - mc.player.getZ();
            double dist = Math.sqrt(dx * dx + dz * dz);
            float yaw   = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90);
            float pitch = (float) (-Math.toDegrees(Math.atan2(dy, dist)));
            mc.player.setYRot(yaw);
            mc.player.setXRot(pitch);
        }

        // Atacar
        mc.gameMode.attack(mc.player, target);
        mc.player.swing(InteractionHand.MAIN_HAND);
        tickCounter = 0;
    }
}
