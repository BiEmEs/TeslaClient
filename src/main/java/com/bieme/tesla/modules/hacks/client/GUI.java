package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;

public class GUI extends Module {

    public GUI() {
        super("GUI", "Click GUI", Category.CLIENT);

        create("Frame Name R", "GUITFrameNameR", 255, 0, 255);
        create("Frame Name G", "GUITFrameNameG", 105, 0, 255);
        create("Frame Name B", "GUITFrameNameB", 180, 0, 255);

        create("Frame Background R", "GUITFrameBackgroundR", 30, 0, 255);
        create("Frame Background G", "GUITFrameBackgroundG", 20, 0, 255);
        create("Frame Background B", "GUITFrameBackgroundB", 30, 0, 255);

        create("Widget Name R", "GUITWidgetNameR", 255, 0, 255);
        create("Widget Name G", "GUITWidgetNameG", 255, 0, 255);
        create("Widget Name B", "GUITWidgetNameB", 255, 0, 255);

        create("Widget Background R", "GUITWidgetBackgroundR", 45, 0, 255);
        create("Widget Background G", "GUITWidgetBackgroundG", 30, 0, 255);
        create("Widget Background B", "GUITWidgetBackgroundB", 45, 0, 255);

        create("Background Blur", "GUITBackgroundBlur", false);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}