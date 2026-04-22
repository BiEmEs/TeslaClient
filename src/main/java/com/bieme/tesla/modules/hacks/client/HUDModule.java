package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import net.minecraft.client.Minecraft;

public class HUDModule extends Module {

    public HUDModule() {
        super("HUD", "gui for pinnables", Category.CLIENT);

        // GENERAL
        create("24h",           "Time24h",       true);
        create("Dim Coords",    "NetherCoords",  true);
        create("All Potions",   "AllPotions", false);

        create("Compass Scale", "CompassScale", 16, 1, 60);
        create("Max Players",   "MaxPlayers",   24, 1, 64);

        // HUD
        create("Color R",       "HUDStringsColorR", 255, 0, 255);
        create("Color G",       "HUDStringsColorG", 255, 0, 255);
        create("Color B",       "HUDStringsColorB", 255, 0, 255);
        create("Alpha",         "HUDStringsColorA", 230, 0, 255);

        // HSB
        create("Saturation",    "Saturation",   100, 0, 100);
        create("Brightness",    "Brightness",   100, 0, 100);

    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level != null && mc.player != null) {
            Module guiMod = Client.getHackManager().get_module_with_tag("GUI");
            if (guiMod != null) guiMod.setActive(false);

            if (Client.clickHud != null) {
                Client.clickHud.back = false;
                mc.setScreen(Client.clickHud);
            }
        }
    }
}