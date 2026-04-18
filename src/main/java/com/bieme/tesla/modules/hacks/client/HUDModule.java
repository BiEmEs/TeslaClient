package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import net.minecraft.client.Minecraft;

public class HUDModule extends Module {

    public HUDModule() {
        super("HUD", "gui for pinnables", Category.CLIENT);

        create("info", "HUDStringsList", "Strings");

        create("Color R", "HUDStringsColorR", 255, 0, 255);
        create("Color G", "HUDStringsColorG", 255, 0, 255);
        create("Color B", "HUDStringsColorB", 255, 0, 255);
        create("Alpha",   "HUDStringsColorA", 230, 0, 255);

        create("Compass Scale", "HUDCompassScale", 16, 1, 60);

        create("ArrayList", "HUDArrayList", "Free",
                combobox("Free", "Top R", "Top L", "Bottom R", "Bottom L"));

        create("All Potions", "HUDAllPotions", false);

        create("Max Players", "HUDMaxPlayers", 24, 1, 64);
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

    @Override
    public void onDisable() {
    }
}