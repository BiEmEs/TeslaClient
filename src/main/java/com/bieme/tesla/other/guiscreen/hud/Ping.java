package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;

public class Ping extends Pinnable {

    private int cachedPing = -1;
    private String cachedText = "Ping: 0ms";

    public Ping() {
        super("Ping", "Ping", 2, 14);
        set_height(get_title_height() + 2);
        set_width(get_text_width(cachedText) + 4);
    }

    public static int getPing() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return 0;
        PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());
        return info != null ? info.getLatency() : 0;
    }

    @Override
    public void render(GuiGraphics gui) {
        int ping = getPing();

        if (ping != cachedPing) {
            cachedPing = ping;
            cachedText = "Ping: " + ping + "ms";
            set_width(get_text_width(cachedText) + 4);
        }

        create_line(gui, cachedText, 2, 1);
    }
}