package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public class PvpHud extends Pinnable {

    private String cachedText = "HP: 0.0";

    public PvpHud() {
        super("PvP HUD", "PvpHud", 2, 100);
        set_height(get_title_height() + 2);
        set_width(get_text_width(cachedText) + 4);
    }

    public static String getInfo() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return "";
        Player p = mc.player;
        float hp = p.getHealth();
        float armor = p.getArmorValue();
        return String.format("HP: %.1f | Armor: %.0f", hp, armor);
    }

    @Override
    public void render(GuiGraphics gui) {
        String text = getInfo();
        if (text.isEmpty()) return;
        if (!text.equals(cachedText)) {
            cachedText = text;
            set_width(get_text_width(cachedText) + 4);
        }
        create_line(gui, cachedText, 2, 1);
    }
}
