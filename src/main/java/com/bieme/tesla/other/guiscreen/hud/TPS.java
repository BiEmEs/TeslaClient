package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class TPS extends Pinnable {

    private static final int SAMPLES = 20;
    private static final long[] tickTimestamps = new long[SAMPLES];
    private static int sampleIndex = 0;
    private static int sampleCount = 0;
    private static long lastTickTime = 0L;

    // Cache: un decimal de TPS solo cambia unas veces por segundo.
    private double cachedTps = -1.0;
    private String cachedText = "TPS: 20.0";

    public TPS() {
        super("TPS", "TPS", 2, 26);
        set_height(get_title_height() + 2);
        set_width(get_text_width(cachedText) + 4);
    }

    public static void onServerTick() {
        long now = System.currentTimeMillis();
        if (lastTickTime != 0L) {
            tickTimestamps[sampleIndex] = now - lastTickTime;
            sampleIndex = (sampleIndex + 1) % SAMPLES;
            if (sampleCount < SAMPLES) sampleCount++;
        }
        lastTickTime = now;
    }

    public static double getTPS() {
        if (sampleCount == 0) return 20.0;

        long sum = 0L;
        for (int i = 0; i < sampleCount; i++) sum += tickTimestamps[i];
        double avgMs = sum / (double) sampleCount;

        if (avgMs <= 0.0) return 20.0;
        double tps = 1000.0 / avgMs;
        if (tps > 20.0) tps = 20.0;
        return tps;
    }

    @Override
    public void render(GuiGraphics gui) {
        Minecraft mc = Minecraft.getInstance();

        double tps = (mc.level == null) ? 20.0 : getTPS();

        double rounded = Math.round(tps * 10.0) / 10.0;

        if (rounded != cachedTps) {
            cachedTps = rounded;
            cachedText = "TPS: " + String.format("%.1f", rounded);
            set_width(get_text_width(cachedText) + 4);
        }

        create_line(gui, cachedText, 2, 1);
    }
}