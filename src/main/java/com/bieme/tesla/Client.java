package com.bieme.tesla;

import com.bieme.tesla.other.manager.ManagerHack;
import com.bieme.tesla.other.manager.ManagerCommand;
import com.bieme.tesla.other.manager.ManagerConfig;
import com.bieme.tesla.other.manager.ManagerSetting;
import com.bieme.tesla.other.guiscreen.ClientGui;
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
    
    public static ClientGui clickGui;
    public static ClientGui click_hud;

    public static void init() {
        mc = Minecraft.getInstance();

        configManager = new ManagerConfig();
        hackManager = new ManagerHack();
        commandManager = new ManagerCommand();
        settingManager = new ManagerSetting();

        // Register modules
        hackManager.register(new com.bieme.tesla.modules.hacks.combat.KillAura());
        hackManager.register(new com.bieme.tesla.modules.utils.player.fakeplayer.Stewart());
        
        clickGui = new ClientGui();
        click_hud = clickGui;
        
        commandManager.init();
        
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
}