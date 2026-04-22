package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import net.minecraft.client.Minecraft;

public class GUI extends Module {

    public GUI() {
        super("GUI", "Click GUI", Category.CLIENT);

        create("Frame Name R", "NameFrameR", 255, 0, 255);
        create("Frame Name G", "NameFrameG", 105, 0, 255);
        create("Frame Name B", "NameFrameB", 180, 0, 255);

        create("Frame Bg R",   "BackgroundFrameR",  30, 0, 255);
        create("Frame Bg G",   "BackgroundFrameG",  20, 0, 255);
        create("Frame Bg B",   "BackgroundFrameB",  30, 0, 255);
        create("Frame Bg A",   "BackgroundFrameA", 240, 0, 255);

        create("Frame Border R", "BorderFrameR", 255, 0, 255);
        create("Frame Border G", "BorderFrameG", 105, 0, 255);
        create("Frame Border B", "BorderFrameB", 180, 0, 255);

        create("Widget Name R", "NameWidgetR", 255, 0, 255);
        create("Widget Name G", "NameWidgetG", 255, 0, 255);
        create("Widget Name B", "NameWidgetB", 255, 0, 255);

        create("Widget Bg R", "BackgroundWidgetR",  45, 0, 255);
        create("Widget Bg G", "BackgroundWidgetG",  30, 0, 255);
        create("Widget Bg B", "BackgroundWidgetB",  45, 0, 255);
        create("Widget Bg A", "BackgroundWidgetA", 200, 0, 255);

        create("Widget Border R", "BorderWidgetR", 255, 0, 255);
        create("Widget Border G", "BorderWidgetG", 105, 0, 255);
        create("Widget Border B", "BorderWidgetB", 180, 0, 255);

        create("Background Blur", "Blur", false);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level != null && mc.player != null) {
            if (Client.clickGui != null) {
                mc.setScreen(Client.clickGui);
            }
        }
    }

    @Override
    public void onDisable() {
    }
}