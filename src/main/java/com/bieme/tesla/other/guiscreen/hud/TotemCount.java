package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Items;

public class TotemCount extends Pinnable {

    private String cachedText = "Totems: 0";

    public TotemCount() {
        super("Totem Count", "TotemCount", 2, 80);
        set_height(get_title_height() + 2);
        set_width(get_text_width(cachedText) + 4);
    }

    public static int getCount() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        int count = 0;
        for (int i = 0; i < mc.player.getInventory().getContainerSize(); i++) {
            if (mc.player.getInventory().getItem(i).getItem() == Items.TOTEM_OF_UNDYING) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void render(GuiGraphics gui) {
        String text = "Totems: " + getCount();
        if (!text.equals(cachedText)) {
            cachedText = text;
            set_width(get_text_width(cachedText) + 4);
        }
        create_line(gui, cachedText, 2, 1);
    }
}
