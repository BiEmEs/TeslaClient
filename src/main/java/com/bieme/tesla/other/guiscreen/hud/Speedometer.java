package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class Speedometer extends Pinnable {

    private String cachedText = "Speed: 0.0";

    public Speedometer() {
        super("Speedometer", "Speedometer", 2, 60);
        set_height(get_title_height() + 2);
        set_width(get_text_width(cachedText) + 4);
    }

    public static String getSpeed() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return "0.0";
        double dx = mc.player.getDeltaMovement().x;
        double dz = mc.player.getDeltaMovement().z;
        double speed = Math.sqrt(dx * dx + dz * dz);
        return String.format("%.2f", speed);
    }

    @Override
    public void render(GuiGraphics gui) {
        String text = "Speed: " + getSpeed();
        if (!text.equals(cachedText)) {
            cachedText = text;
            set_width(get_text_width(cachedText) + 4);
        }
        create_line(gui, cachedText, 2, 1);
    }
}
