package com.bieme.tesla;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeslaMod implements ModInitializer, ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("TeslaClient initializing!");
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("TeslaClient loaded on client!");
        Client.init();
    }
    
    private static boolean bindingInGUI = false;

    public static void setBindingInGUI(boolean value) { bindingInGUI = value; }
    public static boolean isBindingInGUI() { return bindingInGUI; }

    public static void toggleGui() {
        Minecraft mc = Minecraft.getInstance();
        
        if (mc.player == null) return;
        
        if (mc.screen == null) {
            com.bieme.tesla.other.guiscreen.ClientGui gui = new com.bieme.tesla.other.guiscreen.ClientGui();
            mc.setScreen(gui);
            LOGGER.info("Opening Tesla GUI!");
        } else {
            mc.screen.onClose();
            LOGGER.info("Closing GUI!");
        }
    }
}