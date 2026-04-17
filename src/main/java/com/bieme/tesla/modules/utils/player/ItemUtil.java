package com.bieme.tesla.modules.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ItemUtil {

    private static final Minecraft mc = Minecraft.getInstance();

    public static int findItem(Class<?> itemClass) {
        if (mc.player == null) return -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty() && itemClass.isInstance(stack.getItem())) {
                return i;
            }
        }
        return -1;
    }

    public static int findItem(String itemName) {
        if (mc.player == null) return -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem().toString().contains(itemName)) {
                return i;
            }
        }
        return -1;
    }
}