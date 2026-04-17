package com.bieme.tesla.modules.utils.player.fakeplayer;

import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stewart extends Module {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Minecraft mc = Minecraft.getInstance();
    
    private StewartEntity stewart;

    public Stewart() {
        super("Stewart", "Fake player that follows you", Category.MISC);
    }

    @Override
    public void onEnable() {
        stewart = new StewartEntity();
        stewart.spawn();
        LOGGER.info("Stewart spawned!");
    }

    @Override
    public void onDisable() {
        if (stewart != null) {
            stewart.despawn();
        }
        stewart = null;
        LOGGER.info("Stewart despawned!");
    }

    @Override
    public void onUpdate() {
        if (stewart == null || mc.player == null) return;
        
        stewart.update();
        
        if (stewart.getHealth() < 10) {
            LOGGER.info("Stewart needs a totem!");
        }
    }

    public StewartEntity getStewart() {
        return stewart;
    }
}