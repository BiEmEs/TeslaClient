package com.bieme.tesla;

import com.bieme.tesla.other.manager.ManagerHack;
import com.bieme.tesla.other.manager.ManagerCommand;
import com.bieme.tesla.other.manager.ManagerConfig;
import com.bieme.tesla.other.manager.ManagerSetting;
import com.bieme.tesla.other.manager.ManagerHud;
import com.bieme.tesla.other.guiscreen.ClientGui;
import com.bieme.tesla.other.guiscreen.ClientHud;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.hacks.Category;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String CLIENT_NAME = "TeslaClient";
    public static final String CLIENT_VERSION = "0.1";
    public static final String CLIENT_AUTHOR = "BiEmE";

    public static Minecraft mc;

    public static final String g = "§7";
    public static final String r = "§c";

    private static ManagerHack hackManager;
    private static ManagerCommand commandManager;
    private static ManagerConfig configManager;
    private static ManagerSetting settingManager;
    private static ManagerHud hudManager;

    public static ClientGui clickGui;
    public static ClientHud clickHud;

    public static void init() {
        mc = Minecraft.getInstance();

        configManager = new ManagerConfig();
        hackManager = new ManagerHack();
        commandManager = new ManagerCommand();
        settingManager = new ManagerSetting();

        clickGui = new ClientGui();
        clickHud = new ClientHud();

        commandManager.init();
        hackManager.init();

        // ManagerHud se inicializa DESPUES de hackManager.init() para que los pinnables
        // puedan leer settings del modulo HUD si los necesitan al construirse.
        hudManager = new ManagerHud();

        LOGGER.info("TeslaClient {} by {} initialized!", CLIENT_NAME, CLIENT_AUTHOR);
    }

    public static ManagerHack getHackManager() {
        return hackManager;
    }

    public static ManagerCommand getCommandManager() {
        return commandManager;
    }

    public static ManagerConfig getConfigManager() {
        return configManager;
    }

    public static ManagerSetting getSettingManager() {
        return settingManager;
    }

    public static ManagerHud getHudManager() {
        return hudManager;
    }
}