package com.bieme.tesla;

import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.guiscreen.ClientGui;
import com.bieme.tesla.other.guiscreen.ClientHud;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeslaMod implements ModInitializer, ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean bindingInGUI = false;

    @Override
    public void onInitialize() {
        LOGGER.info("TeslaClient initializing!");
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("TeslaClient loaded on client!");
        Client.init();
    }

    /**
     * Toggle del ClickGUI via el módulo GUI.
     * Enciende/apaga el módulo y deja que su onEnable/onDisable abra o cierre la pantalla,
     * Esto mantiene sincronizado el estado "enabled" del módulo con
     * si la GUI está abierta, por lo que el botón del módulo se ve resaltado correctamente.
     */
    public static void onKeyPress() {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;

        // Si estamos escribiendo un bind, no hacer nada con este keypress.
        if (bindingInGUI) return;

        Module guiMod = null;
        if (Client.getHackManager() != null) {
            guiMod = Client.getHackManager().get_module_with_tag("GUI");
        }

        // Abrir: no hay pantalla activa
        if (mc.screen == null) {
            if (guiMod != null) {
                guiMod.setActive(true);            // -> GUI.onEnable() hace mc.setScreen(Client.clickGui)
            } else if (Client.clickGui != null) {
                mc.setScreen(Client.clickGui);     // fallback
            }
            LOGGER.info("Opening Tesla GUI!");
            return;
        }

        // Cerrar: si la pantalla actual es una de las nuestras, cerrarla.
        if (mc.screen instanceof ClientGui || mc.screen instanceof ClientHud) {
            mc.screen.onClose();                   // desactiva el(los) modulo(s) y guarda settings
            mc.setScreen(null);
            LOGGER.info("Closing GUI!");
        }
    }

    public static void setBindingInGUI(boolean value) {
        bindingInGUI = value;
    }

    public static boolean isBindingInGUI() {
        return bindingInGUI;
    }
}