package com.bieme.tesla.other.guiscreen;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.hacks.client.HUDModule;
import com.bieme.tesla.modules.hacks.client.GUI;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer {

    private static final Minecraft MC = Minecraft.getInstance();
    private static List<Module> cachedActive = new ArrayList<>();
    private static int updateCounter = 0;

    public static void render(GuiGraphics gui) {
        if (MC == null) return;
        if (MC.player == null || MC.level == null) return;
        if (MC.font == null) return;
        if (gui == null) return;
        if (MC.options.hideGui) return;

        HUDModule hud = getHudModule();
        if (hud == null || !hud.isEnabled()) return;

        for (Setting s : hud.getSettings()) {
            switch (s.getTag()) {
                case "HUDHackList": if (s.getBoolValue()) renderHackList(gui); break;
                case "HUDWatermark": if (s.getBoolValue()) renderWatermark(gui); break;
                case "HUDFps": if (s.getBoolValue()) renderFps(gui); break;
                case "HUDCoords": if (s.getBoolValue()) renderCoords(gui); break;
                case "HUDDirection": if (s.getBoolValue()) renderDirection(gui); break;
            }
        }
    }

    private static HUDModule getHudModule() {
        if (Client.getHackManager() == null) return null;
        return (HUDModule) Client.getHackManager().get_module_with_tag("HUD");
    }

private static int getAccentColor() {
        try {
            GUI gui = (GUI) Client.getHackManager().get_module_with_tag("GUI");
            if (gui == null) return 0xFFFF3EA5;

            for (var s : gui.getSettings()) {
                if (s.getTag().equals("GUITFrameNameR")) {
                    int r = (int) s.getSliderValue();
                    int g = 0, b = 0;
                    for (var s2 : gui.getSettings()) {
                        if (s2.getTag().equals("GUITFrameNameG")) g = (int) s2.getSliderValue();
                        if (s2.getTag().equals("GUITFrameNameB")) b = (int) s2.getSliderValue();
                    }
                    return 0xFF000000 | (r << 16) | (g << 8) | b;
                }
            }
            return 0xFFFF3EA5;
        } catch (Exception e) {
            return 0xFFFF3EA5;
        }
    }

    private static void renderHackList(GuiGraphics gui) {
        updateCounter++;
        if (updateCounter >= 5) {
            List<Module> all = Client.getHackManager().getModules();
            cachedActive = new ArrayList<>();
            for (Module m : all) {
                if (m.isEnabled() && !(m instanceof HUDModule)) {
                    cachedActive.add(m);
                }
            }
            cachedActive.sort((a, b) -> Integer.compare(
                MC.font.width(b.getName()),
                MC.font.width(a.getName())
            ));
            updateCounter = 0;
        }

        int y = 2;
        int screenWidth = MC.getWindow().getGuiScaledWidth();
        int color = getAccentColor();

        for (Module m : cachedActive) {
            String name = m.getName();
            int width = MC.font.width(name);
            int x = screenWidth - width - 2;
            gui.drawString(MC.font, name, x, y, color, true);
            y += MC.font.lineHeight + 1;
        }
    }

    private static void renderWatermark(GuiGraphics gui) {
        gui.drawString(MC.font, "Tesla", 2, 2, getAccentColor(), true);
    }

    private static void renderFps(GuiGraphics gui) {
        int fps = 60;
        try {
            java.lang.reflect.Method m = MC.getClass().getMethod("getCurrentFps");
            fps = (int) m.invoke(MC);
        } catch (Exception e) {
            fps = 60;
        }
        gui.drawString(MC.font, "FPS: " + fps, 2, 12, 0xFFFFFFFF, true);
    }

    private static void renderCoords(GuiGraphics gui) {
        int x = (int) MC.player.getX();
        int y = (int) MC.player.getY();
        int z = (int) MC.player.getZ();
        gui.drawString(MC.font, "XYZ: " + x + " " + y + " " + z, 2, 22, 0xFFFFFFFF, true);
    }

    private static void renderDirection(GuiGraphics gui) {
        float yaw = MC.player.getYRot() % 360;
        if (yaw < 0) yaw += 360;
        String dir;
        if (yaw >= 337.5 || yaw < 22.5) dir = "South";
        else if (yaw < 67.5) dir = "SW";
        else if (yaw < 112.5) dir = "West";
        else if (yaw < 157.5) dir = "NW";
        else if (yaw < 202.5) dir = "North";
        else if (yaw < 247.5) dir = "NE";
        else if (yaw < 292.5) dir = "East";
        else dir = "SE";
        gui.drawString(MC.font, "Facing: " + dir, 2, 32, 0xFFFFFFFF, true);
    }
}