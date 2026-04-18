package com.bieme.tesla;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeslaMod implements ModInitializer, ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean lastKeyState = false;

    @Override
    public void onInitialize() {
        LOGGER.info("TeslaClient initializing!");
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("TeslaClient loaded on client!");
        Client.init();
    }

    public static void onKeyPress() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.screen == null) {
            mc.setScreen(new com.bieme.tesla.other.guiscreen.ClientGui());
            LOGGER.info("Opening Tesla GUI!");
        } else {
            mc.screen.onClose();
            LOGGER.info("Closing GUI!");
        }
    }

    public static void setBindingInGUI(boolean value) {
    }

    public static boolean isBindingInGUI() {
        return false;
    }
}