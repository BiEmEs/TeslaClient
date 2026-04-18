package com.bieme.tesla.other.guiscreen;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableFrame;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

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

    @Override
    public void init() {
        this.on_gui = true;

        PinnableFrame.nc_r = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUINameFrameR").getSliderValue();
        PinnableFrame.nc_g = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUINameFrameG").getSliderValue();
        PinnableFrame.nc_b = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUINameFrameB").getSliderValue();

        PinnableFrame.bg_r = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundFrameR").getSliderValue();
        PinnableFrame.bg_g = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundFrameG").getSliderValue();
        PinnableFrame.bg_b = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundFrameB").getSliderValue();
        PinnableFrame.bg_a = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundFrameA").getSliderValue();

        PinnableFrame.bd_r = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderFrameR").getSliderValue();
        PinnableFrame.bd_g = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderFrameG").getSliderValue();
        PinnableFrame.bd_b = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderFrameB").getSliderValue();
        PinnableFrame.bd_a = 0;

        PinnableFrame.bdw_r = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderWidgetR").getSliderValue();
        PinnableFrame.bdw_g = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderWidgetG").getSliderValue();
        PinnableFrame.bdw_b = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderWidgetB").getSliderValue();
        PinnableFrame.bdw_a = 255;

        PinnableButton.nc_r = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUINameWidgetR").getSliderValue();
        PinnableButton.nc_g = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUINameWidgetG").getSliderValue();
        PinnableButton.nc_b = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUINameWidgetB").getSliderValue();

        PinnableButton.bg_r = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundWidgetR").getSliderValue();
        PinnableButton.bg_g = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundWidgetG").getSliderValue();
        PinnableButton.bg_b = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundWidgetB").getSliderValue();
        PinnableButton.bg_a = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBackgroundWidgetA").getSliderValue();

        PinnableButton.bd_r = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderWidgetR").getSliderValue();
        PinnableButton.bd_g = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderWidgetG").getSliderValue();
        PinnableButton.bd_b = (int) Client.getSettingManager().getSettingByTag("GUI", "ClickGUIBorderWidgetB").getSliderValue();
    }

    @Override
    public void onClose() {
        com.bieme.tesla.modules.hacks.Module guiMod = Client.getHackManager().get_module_with_tag("GUI");
        com.bieme.tesla.modules.hacks.Module hudMod = Client.getHackManager().get_module_with_tag("HUD");

        if (this.back) {
            if (guiMod != null) guiMod.setEnabled(true);
            if (hudMod != null) hudMod.setEnabled(false);
        } else {
            if (hudMod != null) hudMod.setEnabled(false);
            if (guiMod != null) guiMod.setEnabled(false);
        }

        this.on_gui = false;
        Client.getConfigManager().saveSettings();
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
        return true;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int mx = (int) event.x();
        int my = (int) event.y();
        int state = event.button();

        this.frame.release(mx, my, state);
        this.frame.set_move(false);
        return true;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mx, int my, float tick) {
        this.renderBackground(guiGraphics, mx, my, tick);
        this.frame.render(guiGraphics, mx, my, 2);
    }
}
