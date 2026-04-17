package com.bieme.tesla.other.guiscreen.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class ClientDraw {
    private static Font font;
    private float size;

    @SuppressWarnings("null")
    public static Font getFont() {
        if (font == null) {
            Minecraft mc = Minecraft.getInstance();
            if (mc != null) {
                font = mc.font;
            }
        }
        return font;
    }

    public ClientDraw(float size) {
        this.size = size;
    }

    public static void drawRect(GuiGraphics gui, int x1, int y1, int x2, int y2, Color color) {
        gui.fill(x1, y1, x2, y2, color.getRGB());
    }

    public static void drawBorderedRect(GuiGraphics gui, int x, int y, int w, int h, int border, Color color, String sides) {
        if (sides.contains("up")) drawRect(gui, x, y, x + w, y + border, color);
        if (sides.contains("down")) drawRect(gui, x, y + h - border, x + w, y + h, color);
        if (sides.contains("left")) drawRect(gui, x, y, x + border, y + h, color);
        if (sides.contains("right")) drawRect(gui, x + w - border, y, x + w, y + h, color);
    }

    public static void drawString(GuiGraphics gui, String text, int x, int y, Color color) {
        gui.drawString(getFont(), text, x, y, color.getRGB());
    }

    public int getStringWidth(String string) {
        Font f = getFont();
        if (f == null) return 50;
        return (int) (f.width(string) * size);
    }

    public int getStringHeight() {
        Font f = getFont();
        if (f == null) return 10;
        return (int) (f.lineHeight * size);
    }

    public static void draw_rect(GuiGraphics gui, int x, int y, int width, int height, int r, int g, int b, int a) {
        gui.fill(x, y, width, height, new Color(r, g, b, a).getRGB());
    }

    public static void draw_rect(int x, int y, int width, int height, int r, int g, int b, int a, int border, String sides) {
    }

    public static void draw_rect(GuiGraphics gui, int x, int y, int width, int height, int r, int g, int b, int a, int border, String sides) {
        int color = new Color(r, g, b, a).getRGB();
        if (sides.contains("left")) {
            gui.fill(x - border, y, x, y + height, color);
        }
        if (sides.contains("right")) {
            gui.fill(x + width, y, x + width + border, y + height, color);
        }
        if (sides.contains("up")) {
            gui.fill(x, y - border, x + width, y, color);
        }
        if (sides.contains("down")) {
            gui.fill(x, y + height, x + width, y + height + border, color);
        }
        if (sides.contains("left-right")) {
            gui.fill(x - border, y, x, y + height, color);
            gui.fill(x + width, y, x + width + border, y + height, color);
        }
    }

    public static void draw_string(GuiGraphics gui, String text, int x, int y, int r, int g, int b, int a) {
        int color = (a << 24) | (r << 16) | (g << 8) | b;
        gui.drawString(getFont(), text, x, y, color);
    }

    public static void draw_string(String text, int x, int y, int r, int g, int b, int a) {
    }

    public static class TravisColor {
        private int r, g, b, a;

        public TravisColor(int r, int g, int b, int a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public int hex() {
            return (a << 24) | (r << 16) | (g << 8) | b;
        }
    }
}
