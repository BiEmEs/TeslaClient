package com.bieme.tesla.other.guiscreen.hud;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class Coordinates extends Pinnable {

    // Cache por bloque + dimension + toggle: solo se regeneran los strings cuando
    // algo de esto cambia. El render normal es 1-2 create_line sin crear strings.
    private int cachedX = Integer.MIN_VALUE;
    private int cachedY = Integer.MIN_VALUE;
    private int cachedZ = Integer.MIN_VALUE;
    private ResourceKey<Level> cachedDim = null;
    private boolean cachedDimToggle = false;

    private String line1 = "XYZ: 0 0 0";
    private String line2 = "";
    private boolean showSecondLine = false;

    public Coordinates() {
        super("Coordinates", "Coordinates", 2, 40);
        set_height(get_title_height() + 2);
        set_width(get_text_width(line1) + 4);
    }

    public static String getCoords() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return "0 0 0";
        return (int) Math.floor(mc.player.getX()) + " "
                + (int) Math.floor(mc.player.getY()) + " "
                + (int) Math.floor(mc.player.getZ());
    }

    private static boolean isDimensionCoordsEnabled() {
        if (Client.getSettingManager() == null) return true;
        Setting s = Client.getSettingManager().getSettingByTag("HUD", "HUDDimensionCoords");
        if (s == null) return true;
        return s.getBoolValue();
    }

    @Override
    public void render(GuiGraphics gui) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            create_line(gui, line1, 2, 1);
            return;
        }

        int x = (int) Math.floor(mc.player.getX());
        int y = (int) Math.floor(mc.player.getY());
        int z = (int) Math.floor(mc.player.getZ());
        ResourceKey<Level> dim = mc.level.dimension();
        boolean dimToggle = isDimensionCoordsEnabled();

        if (x != cachedX || y != cachedY || z != cachedZ
                || dim != cachedDim || dimToggle != cachedDimToggle) {
            cachedX = x;
            cachedY = y;
            cachedZ = z;
            cachedDim = dim;
            cachedDimToggle = dimToggle;

            line1 = "XYZ: " + x + " " + y + " " + z;

            if (!dimToggle) {
                line2 = "";
                showSecondLine = false;
            } else if (dim == Level.NETHER) {
                line2 = "OW: " + (x * 8) + " " + y + " " + (z * 8);
                showSecondLine = true;
            } else if (dim == Level.OVERWORLD) {
                line2 = "N: " + (x / 8) + " " + y + " " + (z / 8);
                showSecondLine = true;
            } else {
                line2 = "";
                showSecondLine = false;
            }

            int w1 = get_text_width(line1);
            int w2 = get_text_width(line2);
            set_width(Math.max(w1, w2) + 4);
            set_height((showSecondLine ? get_title_height() * 2 + 2 : get_title_height()) + 2);
        }

        create_line(gui, line1, 2, 1);
        if (showSecondLine) {
            create_line(gui, line2, 2, 1 + get_title_height());
        }
    }
}