package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.gui.GuiGraphics;

import java.time.LocalTime;

public class Time extends Pinnable {

    private int cachedHour = -1;
    private int cachedMinute = -1;
    private boolean cached24h = true;
    private String cachedText = "00:00";

    public Time() {
        super("Time", "Time", 2, 52);
        set_height(get_title_height() + 2);
        set_width(get_text_width(cachedText) + 4);
    }

    public static String getTime() {
        LocalTime now = LocalTime.now();
        return format2(now.getHour()) + ":" + format2(now.getMinute());
    }

    private static String format2(int v) {
        return v < 10 ? "0" + v : Integer.toString(v);
    }

    private static boolean is24h() {
        if (Client.getSettingManager() == null) return true;
        Setting s = Client.getSettingManager().getSettingByTag("HUD", "HUDTime24h");
        if (s == null) return true;
        return s.getBoolValue();
    }

    @Override
    public void render(GuiGraphics gui) {
        LocalTime now = LocalTime.now();
        int h = now.getHour();
        int m = now.getMinute();
        boolean h24 = is24h();

        if (h != cachedHour || m != cachedMinute || h24 != cached24h) {
            cachedHour = h;
            cachedMinute = m;
            cached24h = h24;

            if (h24) {
                cachedText = format2(h) + ":" + format2(m);
            } else {
                int h12 = h % 12;
                if (h12 == 0) h12 = 12;
                String suffix = h < 12 ? " AM" : " PM";
                cachedText = h12 + ":" + format2(m) + suffix;
            }

            set_width(get_text_width(cachedText) + 4);
        }

        create_line(gui, cachedText, 2, 1);
    }
}