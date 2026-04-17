package com.bieme.tesla.modules.utils.player.fakeplayer;

import com.bieme.tesla.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class StewartEntity {

    private static final Minecraft mc = Minecraft.getInstance();
    
    private double x, y, z;
    private double lastX, lastY, lastZ;
    private float health = 20.0f;
    private float maxHealth = 20.0f;
    private boolean active = false;
    private boolean dead = false;
    
    public StewartEntity() {
    }
    
    public void spawn() {
        if (mc.player == null) return;
        
        x = mc.player.getX();
        y = mc.player.getY();
        z = mc.player.getZ();
        
        x = mc.player.getX();
        y = mc.player.getY();
        z = mc.player.getZ();
        
        health = maxHealth;
        active = true;
        dead = false;
    }
    
    public void despawn() {
        active = false;
    }
    
    public void update() {
        if (!active || mc.player == null) return;
        
        lastX = x;
        lastY = y;
        lastZ = z;
        
        double speed = 0.5;
        
        double dx = mc.player.getX() - x;
        double dy = mc.player.getY() - y;
        double dz = mc.player.getZ() - z;
        
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        
        if (dist > 2.0) {
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
            z += (dz / dist) * speed;
        } else {
            x = mc.player.getX();
            y = mc.player.getY();
            z = mc.player.getZ();
        }
    }
    
    public void follow(double targetX, double targetY, double targetZ) {
        double speed = 0.3;
        
        double dx = targetX - x;
        double dy = targetY - y;
        double dz = targetZ - z;
        
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        
        if (dist > 0.1) {
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
            z += (dz / dist) * speed;
        }
    }
    
    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setHealth(float health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
        if (this.health <= 0) {
            dead = true;
            active = false;
        }
    }
    
    public void damage(float amount) {
        health -= amount;
        if (health <= 0) {
            dead = true;
            active = false;
            health = 0;
        }
    }
    
    public void heal(float amount) {
        if (dead) return;
        health = Math.min(health + amount, maxHealth);
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getLastX() { return lastX; }
    public double getLastY() { return lastY; }
    public double getLastZ() { return lastZ; }
    
    public float getHealth() { return health; }
    public float getMaxHealth() { return maxHealth; }
    public boolean isActive() { return active; }
    public boolean isDead() { return dead; }
}