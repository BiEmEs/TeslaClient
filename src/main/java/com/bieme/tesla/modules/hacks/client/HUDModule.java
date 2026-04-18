package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;

public class HUDModule extends Module {

    public HUDModule() {
        super("HUD", "Shows client HUD overlay on screen", Category.CLIENT);

        create("HackList", "HUDHackList", true);
        create("Watermark", "HUDWatermark", true);
        create("FPS", "HUDFps", true);
        create("Coordinates", "HUDCoords", true);
        create("Direction", "HUDDirection", true);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}