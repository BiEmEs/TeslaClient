package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import net.minecraft.client.Minecraft;

public class GUI extends Module {

    public GUI() {
        super("GUI", "Click GUI", Category.CLIENT);

        create("Frame Name R", "ClickGUINameFrameR", 255, 0, 255);
        create("Frame Name G", "ClickGUINameFrameG", 105, 0, 255);
        create("Frame Name B", "ClickGUINameFrameB", 180, 0, 255);

        create("Frame Bg R",   "ClickGUIBackgroundFrameR",  30, 0, 255);
        create("Frame Bg G",   "ClickGUIBackgroundFrameG",  20, 0, 255);
        create("Frame Bg B",   "ClickGUIBackgroundFrameB",  30, 0, 255);
        create("Frame Bg A",   "ClickGUIBackgroundFrameA", 240, 0, 255);

        create("Frame Border R", "ClickGUIBorderFrameR", 255, 0, 255);
        create("Frame Border G", "ClickGUIBorderFrameG", 105, 0, 255);
        create("Frame Border B", "ClickGUIBorderFrameB", 180, 0, 255);

        // --- Widgets (settings inline) ---
        create("Widget Name R", "ClickGUINameWidgetR", 255, 0, 255);
        create("Widget Name G", "ClickGUINameWidgetG", 255, 0, 255);
        create("Widget Name B", "ClickGUINameWidgetB", 255, 0, 255);

        create("Widget Bg R", "ClickGUIBackgroundWidgetR",  45, 0, 255);
        create("Widget Bg G", "ClickGUIBackgroundWidgetG",  30, 0, 255);
        create("Widget Bg B", "ClickGUIBackgroundWidgetB",  45, 0, 255);
        create("Widget Bg A", "ClickGUIBackgroundWidgetA", 200, 0, 255);

        create("Widget Border R", "ClickGUIBorderWidgetR", 255, 0, 255);
        create("Widget Border G", "ClickGUIBorderWidgetG", 105, 0, 255);
        create("Widget Border B", "ClickGUIBorderWidgetB", 180, 0, 255);

        create("Background Blur", "ClickGUIBackgroundBlur", false);
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