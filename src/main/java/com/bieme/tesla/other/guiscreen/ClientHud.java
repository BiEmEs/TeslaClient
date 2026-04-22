package com.bieme.tesla.other.guiscreen;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableFrame;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ClientHud extends Screen {
    private final PinnableFrame frame;

    public boolean on_gui;
    public boolean back;

    public ClientHud() {
        super(Component.literal("Tesla HUD"));
        this.frame = new PinnableFrame("Tesla HUD", "TeslaHUD", 40, 40);
        this.back = false;
        this.on_gui = false;
    }

    public PinnableFrame get_frame_hud() {
        return this.frame;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void reloadColors() {
        var sm = Client.getSettingManager();
        // Frame colors — read from HUD module
        PinnableFrame.nc_r  = (int) sm.getSettingByTag("HUD", "HUDFrameNameR").getSliderValue();
        PinnableFrame.nc_g  = (int) sm.getSettingByTag("HUD", "HUDFrameNameG").getSliderValue();
        PinnableFrame.nc_b  = (int) sm.getSettingByTag("HUD", "HUDFrameNameB").getSliderValue();
        PinnableFrame.bg_r  = (int) sm.getSettingByTag("HUD", "HUDFrameBgR").getSliderValue();
        PinnableFrame.bg_g  = (int) sm.getSettingByTag("HUD", "HUDFrameBgG").getSliderValue();
        PinnableFrame.bg_b  = (int) sm.getSettingByTag("HUD", "HUDFrameBgB").getSliderValue();
        PinnableFrame.bg_a  = (int) sm.getSettingByTag("HUD", "HUDFrameBgA").getSliderValue();
        PinnableFrame.bd_r  = (int) sm.getSettingByTag("HUD", "HUDFrameBorderR").getSliderValue();
        PinnableFrame.bd_g  = (int) sm.getSettingByTag("HUD", "HUDFrameBorderG").getSliderValue();
        PinnableFrame.bd_b  = (int) sm.getSettingByTag("HUD", "HUDFrameBorderB").getSliderValue();
        PinnableFrame.bd_a  = 0;
        PinnableFrame.bdw_r = (int) sm.getSettingByTag("HUD", "HUDBtnBorderR").getSliderValue();
        PinnableFrame.bdw_g = (int) sm.getSettingByTag("HUD", "HUDBtnBorderG").getSliderValue();
        PinnableFrame.bdw_b = (int) sm.getSettingByTag("HUD", "HUDBtnBorderB").getSliderValue();
        PinnableFrame.bdw_a = 255;

        PinnableButton.nc_r = (int) sm.getSettingByTag("HUD", "HUDBtnNameR").getSliderValue();
        PinnableButton.nc_g = (int) sm.getSettingByTag("HUD", "HUDBtnNameG").getSliderValue();
        PinnableButton.nc_b = (int) sm.getSettingByTag("HUD", "HUDBtnNameB").getSliderValue();
        PinnableButton.bg_r = (int) sm.getSettingByTag("HUD", "HUDBtnBgR").getSliderValue();
        PinnableButton.bg_g = (int) sm.getSettingByTag("HUD", "HUDBtnBgG").getSliderValue();
        PinnableButton.bg_b = (int) sm.getSettingByTag("HUD", "HUDBtnBgB").getSliderValue();
        PinnableButton.bg_a = (int) sm.getSettingByTag("HUD", "HUDBtnBgA").getSliderValue();
        PinnableButton.bd_r = (int) sm.getSettingByTag("HUD", "HUDBtnBorderR").getSliderValue();
        PinnableButton.bd_g = (int) sm.getSettingByTag("HUD", "HUDBtnBorderG").getSliderValue();
        PinnableButton.bd_b = (int) sm.getSettingByTag("HUD", "HUDBtnBorderB").getSliderValue();
    }

    private boolean isBlurEnabled() {
        var s = Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundBlur");
        return s != null && s.getBoolValue();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isBlurEnabled()) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public void init() {
        this.on_gui = true;

        // Populate the frame's button list from ManagerHud so each HUD component has a toggle button
        frame.clear_buttons();
        if (Client.getHudManager() != null) {
            for (Pinnable pinnable : Client.getHudManager().get_array_huds()) {
                PinnableButton btn = new PinnableButton(frame, pinnable.get_title(), pinnable.get_tag());
                btn.setPinnable(pinnable);
                frame.add_button(btn);
            }
        }
    }

    @Override
    public void onClose() {
        com.bieme.tesla.modules.hacks.Module guiMod = Client.getHackManager().get_module_with_tag("GUI");
        com.bieme.tesla.modules.hacks.Module hudMod = Client.getHackManager().get_module_with_tag("HUD");

        boolean goBack = this.back;
        this.back = false;
        this.on_gui = false;

        // El editor se cierra: el módulo HUD no debería seguir "activo" en el sentido del toggle del GUI.
        if (hudMod != null) hudMod.setEnabled(false);

        if (Client.getConfigManager() != null) {
            Client.getConfigManager().saveSettings();
        }

        Minecraft mc = Minecraft.getInstance();
        if (goBack) {
            // Volvemos al ClickGUI, reactivando el módulo GUI.
            if (guiMod != null) guiMod.setEnabled(true);
            mc.setScreen(Client.clickGui != null ? Client.clickGui : new ClientGui());
        } else {
            // Cierre directo (sin pasar por el ClickGUI).
            if (guiMod != null) guiMod.setEnabled(false);
            mc.setScreen(null);
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        int mx = (int) event.x();
        int my = (int) event.y();
        int mouse = event.button();

        this.frame.click(mx, my, mouse);

        if (mouse == 0 && this.frame.motion(mx, my) && this.frame.can()) {
            this.frame.set_move(true);
            this.frame.set_move_x(mx - this.frame.get_x());
            this.frame.set_move_y(my - this.frame.get_y());
        }

        // Forward click to HUD components so they can be dragged on-screen
        if (Client.getHudManager() != null) {
            for (Pinnable pinnable : Client.getHudManager().get_array_huds()) {
                pinnable.click(mx, my, mouse);
            }
        }
        return true;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int mx = (int) event.x();
        int my = (int) event.y();
        int state = event.button();

        this.frame.release(mx, my, state);
        this.frame.set_move(false);

        // Forward release to HUD components to stop dragging
        if (Client.getHudManager() != null) {
            for (Pinnable pinnable : Client.getHudManager().get_array_huds()) {
                pinnable.release(mx, my, state);
            }
        }
        return true;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mx, int my, float tick) {
        reloadColors();
        // Render active HUD components so the user can see and reposition them
        if (Client.getHudManager() != null) {
            for (Pinnable pinnable : Client.getHudManager().get_array_huds()) {
                if (pinnable.is_active()) {
                    pinnable.render(guiGraphics, mx, my);
                }
            }
        }
        this.frame.render(guiGraphics, mx, my, 2);
    }
}