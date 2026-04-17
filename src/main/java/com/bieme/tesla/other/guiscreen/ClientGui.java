package com.bieme.tesla.other.guiscreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;

public class ClientGui extends Screen {

    public enum ColorMode { STATIC, RAINBOW, SATURATION, BRIGHTNESS, HEIGHT }

    private final ArrayList<com.bieme.tesla.other.guiscreen.render.components.Frame> frame = new ArrayList<>();
    private final Minecraft mc;

    public boolean on_gui = true;

    // Color mode
    public ColorMode colorMode = ColorMode.STATIC;
    public float colorHue        = 0.72f;  // base hue (purple)
    public float colorSaturation = 1.0f;
    public float colorBrightness = 1.0f;
    public float colorSpeed      = 1.0f;

    // Frame theme
    public int theme_frame_name_r = 255;
    public int theme_frame_name_g = 105;
    public int theme_frame_name_b = 180;
    public int theme_frame_name_a = 255;

    public int theme_frame_background_r = 30;
    public int theme_frame_background_g = 20;
    public int theme_frame_background_b = 30;
    public int theme_frame_background_a = 240;

    // Widget theme
    public int theme_widget_background_r = 45;
    public int theme_widget_background_g = 30;
    public int theme_widget_background_b = 45;
    public int theme_widget_background_a = 200;

    public int theme_widget_name_r = 255;
    public int theme_widget_name_g = 255;
    public int theme_widget_name_b = 255;
    public int theme_widget_name_a = 255;

    public ClientGui() {
        super(Component.literal("Tesla GUI"));
        this.mc = Minecraft.getInstance();
    }

    /**
     * Returns the accent color for the current color mode.
     * @param y screen Y of the element (used by HEIGHT mode)
     * @param index element index for horizontal rainbow offset
     */
    public int getAccentColor(int y, int index) {
        float hue, sat, bri;
        long time = System.currentTimeMillis();

        switch (colorMode) {
            case RAINBOW -> {
                hue = ((time * colorSpeed * 0.0005f) + index * 0.05f) % 1.0f;
                sat = colorSaturation;
                bri = colorBrightness;
            }
            case SATURATION -> {
                hue = colorHue;
                sat = (float)(Math.sin(time * colorSpeed * 0.001f) * 0.5 + 0.5);
                bri = colorBrightness;
            }
            case BRIGHTNESS -> {
                hue = colorHue;
                sat = colorSaturation;
                bri = (float)(Math.sin(time * colorSpeed * 0.001f) * 0.4 + 0.6);
            }
            case HEIGHT -> {
                int screenH = mc.getWindow().getGuiScaledHeight();
                hue = (screenH > 0 ? (float) y / screenH : 0f) % 1.0f;
                sat = colorSaturation;
                bri = colorBrightness;
            }
            default -> { // STATIC
                return (theme_frame_name_a << 24)
                     | (theme_frame_name_r << 16)
                     | (theme_frame_name_g << 8)
                     |  theme_frame_name_b;
            }
        }

        return Color.HSBtoRGB(hue, sat, bri);
    }

    @Override
    public void init() {
        super.init();
        this.on_gui = true;

        if (frame.isEmpty()) {
            int x = 10;
            for (com.bieme.tesla.modules.hacks.Category category : com.bieme.tesla.modules.hacks.Category.values()) {
                frame.add(new com.bieme.tesla.other.guiscreen.render.components.Frame(category, x, 10));
                x += 110;
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.on_gui = false;
        this.mc.setScreen(null);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        for (com.bieme.tesla.other.guiscreen.render.components.Frame f : this.frame) {
            f.render(guiGraphics, mouseX, mouseY, 0);
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_ESCAPE) {
            mc.setScreen(null);
            return true;
        }
        for (com.bieme.tesla.other.guiscreen.render.components.Frame f : this.frame) {
            f.keyPressed(event.key(), event.scancode(), event.modifiers());
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        for (com.bieme.tesla.other.guiscreen.render.components.Frame f : this.frame) {
            f.mouseClicked((int) event.x(), (int) event.y(), event.button());
        }
        return super.mouseClicked(event, consumed);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        for (com.bieme.tesla.other.guiscreen.render.components.Frame f : this.frame) {
            f.mouseReleased((int) event.x(), (int) event.y(), event.button());
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        for (com.bieme.tesla.other.guiscreen.render.components.Frame f : this.frame) {
            f.mouseDragged((int) event.x(), (int) event.y());
        }
        return super.mouseDragged(event, deltaX, deltaY);
    }

    public Minecraft getMinecraft() { return mc; }
    public boolean isOnGui() { return on_gui; }
}
