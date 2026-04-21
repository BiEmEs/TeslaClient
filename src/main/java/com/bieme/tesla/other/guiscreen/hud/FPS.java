package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class FPS extends Pinnable {

    private int cachedFps = -1;
    private String cachedText = "FPS: 0";

    public FPS() {
        super("FPS", "FPS", 2, 2);
        set_height(get_title_height() + 2);
        set_width(get_text_width(cachedText) + 4);
    }

    public static int getFPS() {
        return Minecraft.getInstance().getFps();
    }

    @Override
    public void render(GuiGraphics gui) {
        int fps = Minecraft.getInstance().getFps();

        if (fps != cachedFps) {
            cachedFps = fps;
            cachedText = "FPS: " + fps;
            set_width(get_text_width(cachedText) + 4);
        }

        create_line(gui, cachedText, 2, 1);
    }
}