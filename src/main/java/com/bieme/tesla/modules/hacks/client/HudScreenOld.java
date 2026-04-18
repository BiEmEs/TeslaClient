package com.bieme.tesla.modules.hacks.client;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.ClientHud;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HudScreenOld extends Screen {

    private ClientHud hud;
    
    public HudScreenOld() {
        super(Component.literal("Tesla HUD"));
    }

    @Override
    protected void init() {
        this.hud = Client.clickHud;
        super.init();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
        super.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, tickDelta);
        
        if (hud != null && hud.get_frame_hud() != null) {
            hud.get_frame_hud().render(guiGraphics, mouseX, mouseY, 2);
        }
        
        guiGraphics.drawCenteredString(minecraft.font, "Press ESC to exit", this.width / 2, this.height - 20, 0xFFFFFF);
    }
}