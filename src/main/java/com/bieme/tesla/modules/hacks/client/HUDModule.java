package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import net.minecraft.client.Minecraft;

public class HUDModule extends Module {

    public HUDModule() {
        super("HUD", "gui for pinnables", Category.CLIENT);

        // General
        create("24h",            "HUDTime24h",         true);
        create("Dim Coords",     "HUDDimensionCoords",  true);

        // Frame
        create("Title R",        "HUDFrameNameR",   255, 0, 255);
        create("Title G",        "HUDFrameNameG",   105, 0, 255);
        create("Title B",        "HUDFrameNameB",   180, 0, 255);

        create("Bg R",           "HUDFrameBgR",      30, 0, 255);
        create("Bg G",           "HUDFrameBgG",      20, 0, 255);
        create("Bg B",           "HUDFrameBgB",      30, 0, 255);
        create("Bg A",           "HUDFrameBgA",     240, 0, 255);

        create("Border R",       "HUDFrameBorderR",   0, 0, 255);
        create("Border G",       "HUDFrameBorderG",   0, 0, 255);
        create("Border B",       "HUDFrameBorderB",   0, 0, 255);

        // Buttons
        create("Btn R",          "HUDBtnNameR",     255, 0, 255);
        create("Btn G",          "HUDBtnNameG",     255, 0, 255);
        create("Btn B",          "HUDBtnNameB",     255, 0, 255);

        create("Btn Bg R",       "HUDBtnBgR",        45, 0, 255);
        create("Btn Bg G",       "HUDBtnBgG",        30, 0, 255);
        create("Btn Bg B",       "HUDBtnBgB",        45, 0, 255);
        create("Btn Bg A",       "HUDBtnBgA",       200, 0, 255);

        create("Btn Border R",   "HUDBtnBorderR",   255, 0, 255);
        create("Btn Border G",   "HUDBtnBorderG",   105, 0, 255);
        create("Btn Border B",   "HUDBtnBorderB",   180, 0, 255);
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