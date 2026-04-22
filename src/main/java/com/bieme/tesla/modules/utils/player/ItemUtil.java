package com.bieme.tesla.modules.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Inventory;

import java.lang.reflect.Field;

public class ItemUtil {

    public static long windowHandle = 0L;

    private static final Minecraft mc = Minecraft.getInstance();
    private static Field selectedField;

    static {
        try {
            selectedField = Inventory.class.getDeclaredField("selected");
            selectedField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            selectedField = null;
        }
    }

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

    public static int findItemSlot(Item item) {
        if (mc.player == null) return -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    public static int getSelectedSlot() {
        if (mc.player == null || selectedField == null) return 0;
        try {
            return selectedField.getInt(mc.player.getInventory());
        } catch (IllegalAccessException e) {
            return 0;
        }
    }

    public static void swapToHotbarSlot(int slot, boolean shift) {
        if (mc.player == null || selectedField == null || slot < 0 || slot > 8) return;
        try {
            selectedField.setInt(mc.player.getInventory(), slot);
            mc.player.connection.send(new ServerboundSetCarriedItemPacket(slot));
        } catch (IllegalAccessException e) {
        }
    }

    public static void useItem(InteractionHand hand) {
        if (mc.player == null || mc.gameMode == null) return;
        mc.gameMode.useItem(mc.player, hand);
    }
}
